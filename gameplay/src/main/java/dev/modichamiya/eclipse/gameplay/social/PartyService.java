package dev.modichamiya.eclipse.gameplay.social;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class PartyService {public record Party(UUID id,UUID leader,Set<UUID>members,String lootMode){}private final Map<UUID,Party>parties=new ConcurrentHashMap<>();public Party create(UUID leader){Party party=new Party(UUID.randomUUID(),leader,Set.of(leader),"personal");parties.put(party.id(),party);return party;}public synchronized Party join(UUID partyId,UUID player){Party old=Optional.ofNullable(parties.get(partyId)).orElseThrow();Set<UUID>members=new HashSet<>(old.members());members.add(player);Party updated=new Party(old.id(),old.leader(),Set.copyOf(members),old.lootMode());parties.put(partyId,updated);return updated;}}
