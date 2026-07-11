package dev.modichamiya.eclipse.gameplay.combat;

import java.time.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class CrowdControlService {public enum Type{STUN,ROOT,SLOW,SILENCE,FEAR,TAUNT}private record Key(UUID entity,Type type){}private final Map<Key,Instant>effects=new ConcurrentHashMap<>();public Duration apply(UUID entity,Type type,Duration requested,Instant now){long recent=effects.keySet().stream().filter(k->k.entity.equals(entity)).count();Duration adjusted=requested.dividedBy(Math.min(4,recent+1));effects.put(new Key(entity,type),now.plus(adjusted));return adjusted;}public boolean active(UUID entity,Type type,Instant now){Instant until=effects.get(new Key(entity,type));if(until==null)return false;if(!until.isAfter(now)){effects.remove(new Key(entity,type),until);return false;}return true;}}
