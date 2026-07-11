package dev.modichamiya.eclipse.gameplay.combat;

import java.util.*;

public record DamageRequest(UUID attacker,UUID target,double base,DamageType type,double scaling,double criticalChance,double criticalDamage,double targetDefense,double resistance,long seed,Map<String,Double>modifiers){public DamageRequest{Objects.requireNonNull(target);Objects.requireNonNull(type);if(base<0||!Double.isFinite(base))throw new IllegalArgumentException();modifiers=Map.copyOf(modifiers);}}
