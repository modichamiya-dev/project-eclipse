package dev.modichamiya.eclipse.gameplay.loot;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;

public record LootTable(ContentKey key,List<LootEntry>entries){public LootTable{Objects.requireNonNull(key);entries=List.copyOf(entries);if(entries.isEmpty())throw new IllegalArgumentException("Loot table is empty");}}
