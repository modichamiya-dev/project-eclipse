package dev.modichamiya.eclipse.gameplay.dungeon;

import dev.modichamiya.eclipse.registry.ContentKey;
import org.junit.jupiter.api.Test;
import java.time.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class DungeonRunTest {@Test void validatesGraphAndScoresRun(){var rooms=List.of(new DungeonDefinition.Room("start","combat",Set.of("boss"),false),new DungeonDefinition.Room("boss","boss",Set.of(),true));DungeonDefinition definition=new DungeonDefinition(ContentKey.parse("eclipse:crypt"),ContentKey.parse("eclipse:dim"),rooms,ContentKey.parse("eclipse:boss"),Duration.ofMinutes(30),1,5);DungeonRun run=new DungeonRun(definition,Set.of(UUID.randomUUID()),Instant.EPOCH);run.activate();run.clearRoom("boss",true);assertEquals(10400,run.complete(Instant.EPOCH.plusSeconds(10)));}}
