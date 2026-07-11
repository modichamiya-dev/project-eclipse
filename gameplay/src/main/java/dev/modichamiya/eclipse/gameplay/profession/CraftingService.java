package dev.modichamiya.eclipse.gameplay.profession;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class CraftingService {private final Map<ContentKey,Recipe>recipes=new ConcurrentHashMap<>();public void register(Recipe recipe){if(recipes.putIfAbsent(recipe.key(),recipe)!=null)throw new IllegalArgumentException();}public synchronized Map<ContentKey,Integer>craft(ContentKey recipeKey,int professionLevel,Map<ContentKey,Integer>inventory){Recipe recipe=Optional.ofNullable(recipes.get(recipeKey)).orElseThrow();if(professionLevel<recipe.requiredLevel())throw new IllegalStateException("Profession level too low");Map<ContentKey,Integer>result=new HashMap<>(inventory);for(var ingredient:recipe.ingredients().entrySet())if(result.getOrDefault(ingredient.getKey(),0)<ingredient.getValue())throw new IllegalStateException("Missing ingredient "+ingredient.getKey());recipe.ingredients().forEach((key,amount)->result.compute(key,(k,v)->v-amount));result.merge(recipe.output(),recipe.outputAmount(),Math::addExact);result.values().removeIf(value->value==0);return Map.copyOf(result);}}
