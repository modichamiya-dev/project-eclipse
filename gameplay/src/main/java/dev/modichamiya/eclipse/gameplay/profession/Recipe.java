package dev.modichamiya.eclipse.gameplay.profession;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;

public record Recipe(ContentKey key,String profession,int requiredLevel,ContentKey station,Map<ContentKey,Integer>ingredients,ContentKey output,int outputAmount){public Recipe{Objects.requireNonNull(key);Objects.requireNonNull(profession);Objects.requireNonNull(station);Objects.requireNonNull(output);if(requiredLevel<0||outputAmount<1||ingredients.values().stream().anyMatch(v->v<1))throw new IllegalArgumentException();ingredients=Map.copyOf(ingredients);}}
