package dev.modichamiya.eclipse.gameplay.ability;

import dev.modichamiya.eclipse.registry.ContentKey;
import org.junit.jupiter.api.Test;
import java.time.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class AbilityServiceTest {@Test void enforcesResourceAndCooldown(){AbilityService service=new AbilityService();ContentKey key=ContentKey.parse("eclipse:meteor");service.register(new AbilityDefinition(key,null,"mana",20,Duration.ofSeconds(5),"aoe",Map.of()));UUID player=UUID.randomUUID();service.setResource(player,"mana",30);Instant now=Instant.EPOCH;assertTrue(service.cast(player,key,now).success());assertEquals("cooldown",service.cast(player,key,now.plusSeconds(1)).reason());assertEquals(Duration.ofSeconds(4),service.remaining(player,key,now.plusSeconds(1)));}}
