package dev.modichamiya.eclipse.gameplay.loot;

import java.util.*;

public record LootContext(UUID player,double magicFind,double luck,long seed,Map<String,Object>attributes){public LootContext{Objects.requireNonNull(player);if(!Double.isFinite(magicFind)||!Double.isFinite(luck))throw new IllegalArgumentException();attributes=Map.copyOf(attributes);}}
