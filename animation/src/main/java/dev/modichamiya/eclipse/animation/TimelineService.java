package dev.modichamiya.eclipse.animation;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;
import java.util.concurrent.*;

public final class TimelineService implements AutoCloseable {
    private final Map<ContentKey,Timeline> definitions=new ConcurrentHashMap<>(); private final Set<TimelineInstance> active=ConcurrentHashMap.newKeySet();
    public void register(Timeline timeline){if(definitions.putIfAbsent(timeline.key(),timeline)!=null)throw new IllegalArgumentException("Duplicate timeline: "+timeline.key());}
    public TimelineInstance play(ContentKey key,TimelineContext context,TimelineSink sink){Timeline timeline=Optional.ofNullable(definitions.get(key)).orElseThrow(() -> new NoSuchElementException("Unknown timeline: "+key)); TimelineInstance instance=new TimelineInstance(timeline,context,sink);active.add(instance);return instance;}
    public void tick(){active.removeIf(instance -> !instance.advance());}
    public int activeCount(){return active.size();}
    public void close(){active.forEach(TimelineInstance::cancel);active.clear();definitions.clear();}
}
