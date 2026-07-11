package dev.modichamiya.eclipse.gameplay.mob;

import dev.modichamiya.eclipse.registry.ContentKey;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class MobServiceTest {@Test void scalesVariantsAndTransitionsBosses(){MobService service=new MobService();ContentKey key=ContentKey.parse("eclipse:boss");service.register(new MobDefinition(key,null,ContentKey.parse("eclipse:ai"),null,100,10,5,Set.of(),Set.of("hub")));assertEquals(5000,service.spawn(key,MobInstance.Variant.RAID_BOSS).maximumHealth());BossPhaseController phases=new BossPhaseController(List.of(new BossPhaseController.Phase(1,ContentKey.parse("eclipse:p1"),null),new BossPhaseController.Phase(.5,ContentKey.parse("eclipse:p2"),null)));assertTrue(phases.update(.4).isPresent());}}
