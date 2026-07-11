package dev.modichamiya.eclipse.gameplay.ability;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.time.Duration;
import java.util.*;

public record AbilityDefinition(ContentKey key,ContentKey timeline,String resource,double cost,Duration cooldown,String targeting,Map<String,Double>effects){public AbilityDefinition{Objects.requireNonNull(key);Objects.requireNonNull(resource);Objects.requireNonNull(cooldown);Objects.requireNonNull(targeting);if(cost<0||cooldown.isNegative())throw new IllegalArgumentException();effects=Map.copyOf(effects);}}
