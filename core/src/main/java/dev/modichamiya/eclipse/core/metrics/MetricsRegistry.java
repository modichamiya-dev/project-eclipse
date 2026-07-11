package dev.modichamiya.eclipse.core.metrics;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public final class MetricsRegistry {private final Map<String,LongAdder>counters=new ConcurrentHashMap<>();public void increment(String name){counters.computeIfAbsent(name,key->new LongAdder()).increment();}public void add(String name,long amount){counters.computeIfAbsent(name,key->new LongAdder()).add(amount);}public long count(String name){LongAdder value=counters.get(name);return value==null?0:value.sum();}public Map<String,Long>snapshot(){Map<String,Long>result=new TreeMap<>();counters.forEach((key,value)->result.put(key,value.sum()));return Map.copyOf(result);}}
