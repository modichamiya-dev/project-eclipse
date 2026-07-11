package dev.modichamiya.eclipse.admin;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class EditorService { public record Published(ContentKey key,String type,Map<String,Object>values,long version,String author,Instant at){} public record Draft(ContentKey key,String type,Map<String,Object>values,long baseVersion,String author){}
    private final Map<String,DefinitionSchema>schemas=new ConcurrentHashMap<>();private final Map<ContentKey,Published>live=new ConcurrentHashMap<>();private final Map<ContentKey,String>locks=new ConcurrentHashMap<>();private final List<Published>audit=Collections.synchronizedList(new ArrayList<>());
    public void register(DefinitionSchema schema){if(schemas.putIfAbsent(schema.type(),schema)!=null)throw new IllegalArgumentException("Duplicate schema");}
    public Draft begin(ContentKey key,String type,String author){String owner=locks.putIfAbsent(key,author);if(owner!=null&&!owner.equals(author))throw new IllegalStateException("Locked by "+owner);Published current=live.get(key);Map<String,Object>values=current==null?requireSchema(type).defaults():new LinkedHashMap<>(current.values());return new Draft(key,type,values,current==null?0:current.version(),author);}
    public Published publish(Draft draft,Map<String,Object>values){if(!Objects.equals(locks.get(draft.key()),draft.author()))throw new IllegalStateException("Draft lock lost");DefinitionSchema schema=requireSchema(draft.type());Map<String,String>errors=schema.validate(values);if(!errors.isEmpty())throw new IllegalArgumentException(errors.toString());Published current=live.get(draft.key());long version=current==null?1:current.version()+1;if(current!=null&&current.version()!=draft.baseVersion())throw new ConcurrentModificationException();Published published=new Published(draft.key(),draft.type(),Map.copyOf(values),version,draft.author(),Instant.now());live.put(draft.key(),published);audit.add(published);locks.remove(draft.key(),draft.author());return published;}
    public void discard(Draft draft){locks.remove(draft.key(),draft.author());}public List<Published>audit(){synchronized(audit){return List.copyOf(audit);}}private DefinitionSchema requireSchema(String type){return Optional.ofNullable(schemas.get(type)).orElseThrow(()->new NoSuchElementException("Unknown schema: "+type));}
}
