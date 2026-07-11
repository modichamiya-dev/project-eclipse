package dev.modichamiya.eclipse.gameplay.mob;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;

public record MobDefinition(ContentKey key,ContentKey model,ContentKey behavior,ContentKey loot,double health,double damage,double defense,Set<ContentKey>abilities,Set<String>regions){public MobDefinition{Objects.requireNonNull(key);Objects.requireNonNull(behavior);if(health<=0||damage<0||defense<0)throw new IllegalArgumentException();abilities=Set.copyOf(abilities);regions=Set.copyOf(regions);}}
