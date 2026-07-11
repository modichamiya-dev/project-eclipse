package dev.modichamiya.eclipse.gameplay.profession;

import dev.modichamiya.eclipse.registry.ContentKey;
import org.junit.jupiter.api.Test;
import java.time.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ProfessionTest {@Test void craftsAtomicallyAndSimulatesOffline(){ContentKey ore=ContentKey.parse("eclipse:ore"),bar=ContentKey.parse("eclipse:bar"),recipe=ContentKey.parse("eclipse:smelt");CraftingService service=new CraftingService();service.register(new Recipe(recipe,"smithing",1,ContentKey.parse("eclipse:forge"),Map.of(ore,2),bar,1));assertEquals(1,service.craft(recipe,1,Map.of(ore,2)).get(bar));MinionState minion=new MinionState(ore,10,5,Instant.EPOCH);assertEquals(5,minion.simulate(Instant.EPOCH.plusSeconds(100)));assertEquals(5,minion.collect());}}
