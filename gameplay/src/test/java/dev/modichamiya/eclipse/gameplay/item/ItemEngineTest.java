package dev.modichamiya.eclipse.gameplay.item;

import dev.modichamiya.eclipse.registry.ContentKey;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemEngineTest {@Test void mintsUniqueValidatedInstances(){ItemService service=new ItemService();ContentKey key=ContentKey.parse("eclipse:ring");service.register(new ItemDefinition(key,EquipmentSlot.RING_1,Map.of("strength",5d),Set.of(),null,null));ItemInstance one=service.mint(key,Rarity.RARE,80,Map.of("crit",2d)),two=service.mint(key,Rarity.RARE,70,Map.of());assertNotEquals(one.id(),two.id());EquipmentLoadout loadout=new EquipmentLoadout();loadout.equip(EquipmentSlot.RING_2,one,service);assertEquals(one,loadout.snapshot().get(EquipmentSlot.RING_2));}}
