package dev.modichamiya.eclipse.gameplay.social;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.time.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class RankService {private record Assignment(ContentKey rank,Instant expires){}private final Map<ContentKey,Rank>ranks=new ConcurrentHashMap<>();private final Map<UUID,List<Assignment>>assignments=new ConcurrentHashMap<>();public void register(Rank rank){if(ranks.putIfAbsent(rank.key(),rank)!=null)throw new IllegalArgumentException("Duplicate rank");}public void grant(UUID player,ContentKey rank,Instant expires){if(!ranks.containsKey(rank))throw new NoSuchElementException();assignments.computeIfAbsent(player,id->Collections.synchronizedList(new ArrayList<>())).add(new Assignment(rank,expires));}public Optional<Rank>primary(UUID player,Instant now){return assignments.getOrDefault(player,List.of()).stream().filter(a->a.expires==null||a.expires.isAfter(now)).map(a->ranks.get(a.rank)).filter(Objects::nonNull).max(Comparator.comparingInt(Rank::weight));}}
