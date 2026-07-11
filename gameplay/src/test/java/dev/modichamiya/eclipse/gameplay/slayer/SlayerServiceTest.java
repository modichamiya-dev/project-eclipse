package dev.modichamiya.eclipse.gameplay.slayer;

import dev.modichamiya.eclipse.registry.ContentKey;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class SlayerServiceTest {@Test void runsOwnedQuestLoop(){SlayerService service=new SlayerService();ContentKey key=ContentKey.parse("eclipse:undead");service.register(new SlayerDefinition(key,"undead",Map.of(1,new SlayerDefinition.Tier(1,10,ContentKey.parse("eclipse:boss"),ContentKey.parse("eclipse:loot"),5)),List.of(10L)));UUID player=UUID.randomUUID();service.start(player,key,1);assertEquals(SlayerService.State.BOSS_READY,service.creditKill(player,"undead",10));service.bossSpawned(player);assertEquals(5,service.complete(player));}}
