package dev.modichamiya.eclipse.gameplay.mob;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class MobService {private final Map<ContentKey,MobDefinition>definitions=new ConcurrentHashMap<>();private final Map<UUID,MobInstance>active=new ConcurrentHashMap<>();public void register(MobDefinition definition){if(definitions.putIfAbsent(definition.key(),definition)!=null)throw new IllegalArgumentException("Duplicate mob");}public MobInstance spawn(ContentKey key,MobInstance.Variant variant){MobDefinition definition=Optional.ofNullable(definitions.get(key)).orElseThrow();double multiplier=switch(variant){case NORMAL->1;case ELITE->1.5;case CHAMPION->2;case MINIBOSS->5;case WORLD_BOSS->25;case RAID_BOSS->50;};MobInstance mob=new MobInstance(UUID.randomUUID(),key,variant,definition.health()*multiplier);active.put(mob.id(),mob);return mob;}public Optional<MobInstance>find(UUID id){return Optional.ofNullable(active.get(id));}public void despawn(UUID id){active.remove(id);}public int activeCount(){return active.size();}}
