package dev.modichamiya.eclipse.ai;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class ThreatTable<T> { private final Map<T,Double> threat=new ConcurrentHashMap<>(); public void add(T target,double amount){if(!Double.isFinite(amount))throw new IllegalArgumentException();threat.merge(target,amount,Double::sum);}public Optional<T>highest(){return threat.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey);}public void multiply(T target,double factor){threat.computeIfPresent(target,(k,v)->v*factor);}public void clear(){threat.clear();}public Map<T,Double>snapshot(){return Map.copyOf(threat);} }
