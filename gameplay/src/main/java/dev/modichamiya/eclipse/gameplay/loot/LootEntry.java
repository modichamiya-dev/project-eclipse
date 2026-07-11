package dev.modichamiya.eclipse.gameplay.loot;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.Objects;
import java.util.function.Predicate;

public record LootEntry(ContentKey item,double weight,double chance,Predicate<LootContext>condition){public LootEntry{Objects.requireNonNull(item);if(weight<=0||chance<0||chance>1)throw new IllegalArgumentException();condition=condition==null?ctx->true:condition;}}
