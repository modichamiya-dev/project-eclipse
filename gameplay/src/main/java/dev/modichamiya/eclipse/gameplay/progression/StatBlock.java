package dev.modichamiya.eclipse.gameplay.progression;

import java.util.*;

public final class StatBlock {private final Map<String,Double>values;StatBlock(Map<String,Double>values){this.values=Map.copyOf(values);}public double get(String key){return values.getOrDefault(key,0d);}public Map<String,Double>values(){return values;}}
