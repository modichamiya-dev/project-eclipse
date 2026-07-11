package dev.modichamiya.eclipse.gameplay.item;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;

public record ItemDefinition(ContentKey key,EquipmentSlot slot,Map<String,Double>baseStats,Set<String>tags,ContentKey model,ContentKey set){public ItemDefinition{Objects.requireNonNull(key);Objects.requireNonNull(slot);baseStats=Map.copyOf(baseStats);tags=Set.copyOf(tags);}}
