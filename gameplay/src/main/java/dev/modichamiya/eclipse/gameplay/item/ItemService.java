package dev.modichamiya.eclipse.gameplay.item;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class ItemService {private final Map<ContentKey,ItemDefinition>definitions=new ConcurrentHashMap<>();private final Map<UUID,ItemInstance>instances=new ConcurrentHashMap<>();public void register(ItemDefinition definition){if(definitions.putIfAbsent(definition.key(),definition)!=null)throw new IllegalArgumentException("Duplicate item");}public ItemInstance mint(ContentKey key,Rarity rarity,double quality,Map<String,Double>affixes){ItemDefinition definition=requireDefinition(key);ItemInstance item=new ItemInstance(UUID.randomUUID(),key,rarity,quality,affixes,List.of(),null,0,null);instances.put(item.id(),item);return item;}public ItemDefinition requireDefinition(ContentKey key){return Optional.ofNullable(definitions.get(key)).orElseThrow(()->new NoSuchElementException("Unknown item "+key));}public Optional<ItemInstance>find(UUID id){return Optional.ofNullable(instances.get(id));}public void replace(ItemInstance item){if(!instances.containsKey(item.id()))throw new NoSuchElementException();requireDefinition(item.definition());instances.put(item.id(),item);}}
