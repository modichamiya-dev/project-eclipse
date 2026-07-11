package dev.modichamiya.eclipse.gameplay.loot;

import dev.modichamiya.eclipse.gameplay.item.*;
import dev.modichamiya.eclipse.registry.ContentKey;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class LootServiceTest {@Test void seededRollsProduceValidItems(){ItemService items=new ItemService();ContentKey item=ContentKey.parse("eclipse:blade");items.register(new ItemDefinition(item,EquipmentSlot.WEAPON,Map.of(),Set.of(),null,null));LootService loot=new LootService(items);ContentKey table=ContentKey.parse("eclipse:test");loot.register(new LootTable(table,List.of(new LootEntry(item,1,1,null))));var result=loot.roll(table,new LootContext(UUID.randomUUID(),0,0,42,Map.of())).orElseThrow();assertEquals(item,result.definition());}}
