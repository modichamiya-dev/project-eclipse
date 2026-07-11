package dev.modichamiya.eclipse.gameplay.ability;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class MasteryService {private final Map<UUID,Map<String,Long>>xp=new ConcurrentHashMap<>();private final Map<String,List<Long>>curves=new ConcurrentHashMap<>();public void curve(String mastery,List<Long>thresholds){long previous=-1;for(long value:thresholds){if(value<=previous)throw new IllegalArgumentException();previous=value;}curves.put(mastery,List.copyOf(thresholds));}public long add(UUID player,String mastery,long amount){if(amount<0)throw new IllegalArgumentException();return xp.computeIfAbsent(player,id->new ConcurrentHashMap<>()).merge(mastery,amount,Math::addExact);}public int level(UUID player,String mastery){long value=xp.getOrDefault(player,Map.of()).getOrDefault(mastery,0L);int level=1;for(long threshold:curves.getOrDefault(mastery,List.of())){if(value<threshold)break;level++;}return level;}}
