package dev.modichamiya.eclipse.world;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class DimensionService implements AutoCloseable {
    public enum State { PROVISIONING, ACTIVE, CLOSING, CLOSED }
    public interface Backend { CompletableFuture<Void> create(String id,ContentKey template); CompletableFuture<Void> destroy(String id); }
    public static final class Instance { private final String id;private final ContentKey template;private final Instant created;private volatile State state;private Instance(String id,ContentKey template){this.id=id;this.template=template;this.created=Instant.now();this.state=State.PROVISIONING;}public String id(){return id;}public ContentKey template(){return template;}public Instant created(){return created;}public State state(){return state;} }
    private final Backend backend;private final int limit;private final Map<String,Instance> active=new ConcurrentHashMap<>();private final AtomicInteger sequence=new AtomicInteger();
    public DimensionService(Backend backend,int limit){this.backend=Objects.requireNonNull(backend);if(limit<1)throw new IllegalArgumentException("limit");this.limit=limit;}
    public CompletableFuture<Instance> provision(ContentKey template){synchronized(active){if(active.size()>=limit)return CompletableFuture.failedFuture(new IllegalStateException("Dimension capacity reached"));String id="eclipse-"+sequence.incrementAndGet();Instance instance=new Instance(id,template);active.put(id,instance);return backend.create(id,template).thenApply(v->{instance.state=State.ACTIVE;return instance;}).whenComplete((v,e)->{if(e!=null)active.remove(id);});}}
    public CompletableFuture<Void> close(String id){Instance instance=active.get(id);if(instance==null)return CompletableFuture.completedFuture(null);instance.state=State.CLOSING;return backend.destroy(id).whenComplete((v,e)->{if(e==null){instance.state=State.CLOSED;active.remove(id);}});}
    public Collection<Instance> active(){return List.copyOf(active.values());}
    public void close(){CompletableFuture.allOf(active.keySet().stream().map(this::close).toArray(CompletableFuture[]::new)).join();}
}
