package dev.modichamiya.eclipse.ai;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class Blackboard { private final Map<String,Object> values=new ConcurrentHashMap<>(); public <T>Optional<T>get(String key,Class<T>type){return Optional.ofNullable(values.get(key)).filter(type::isInstance).map(type::cast);}public void put(String key,Object value){values.put(key,Objects.requireNonNull(value));}public void remove(String key){values.remove(key);} }
