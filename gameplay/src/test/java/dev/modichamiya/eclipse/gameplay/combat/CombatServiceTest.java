package dev.modichamiya.eclipse.gameplay.combat;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class CombatServiceTest {@Test void appliesDeterministicMitigatedDamage(){CombatService service=new CombatService();UUID target=UUID.randomUUID();service.register(target,100);DamageResult result=service.apply(new DamageRequest(UUID.randomUUID(),target,20,DamageType.PHYSICAL,10,1,2,100,0,1,Map.of()));assertTrue(result.critical());assertEquals(70,service.health(target),.001);}}
