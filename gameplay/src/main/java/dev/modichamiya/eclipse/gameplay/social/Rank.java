package dev.modichamiya.eclipse.gameplay.social;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;

public record Rank(ContentKey key,String prefix,String nameColor,String chatColor,int weight,boolean staff,Set<String>permissions){public Rank{Objects.requireNonNull(key);prefix=Objects.requireNonNullElse(prefix,"");nameColor=validColor(nameColor);chatColor=validColor(chatColor);permissions=Set.copyOf(permissions);}private static String validColor(String color){if(color==null||!color.matches("#[0-9A-Fa-f]{6}"))throw new IllegalArgumentException("Color must be #RRGGBB");return color.toUpperCase(Locale.ROOT);}}
