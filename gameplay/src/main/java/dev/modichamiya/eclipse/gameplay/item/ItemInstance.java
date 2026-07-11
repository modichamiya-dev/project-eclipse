package dev.modichamiya.eclipse.gameplay.item;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;

public record ItemInstance(UUID id,ContentKey definition,Rarity rarity,double quality,Map<String,Double>affixes,List<ContentKey>sockets,ContentKey reforge,int upgradeLevel,ContentKey skin){public ItemInstance{Objects.requireNonNull(id);Objects.requireNonNull(definition);Objects.requireNonNull(rarity);if(quality<0||quality>100||upgradeLevel<0)throw new IllegalArgumentException();affixes=Map.copyOf(affixes);sockets=List.copyOf(sockets);}public static ItemInstance basic(ContentKey key){return new ItemInstance(UUID.randomUUID(),key,Rarity.COMMON,0,Map.of(),List.of(),null,0,null);}}
