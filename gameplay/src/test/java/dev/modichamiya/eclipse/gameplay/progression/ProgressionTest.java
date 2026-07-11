package dev.modichamiya.eclipse.gameplay.progression;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ProgressionTest {@Test void resolvesAndCachesStatsAndProgression(){StatService stats=new StatService();stats.register(new Attribute("strength",10,0,1000));UUID id=UUID.randomUUID();stats.replace(id,List.of(new StatModifier("item","strength",5,2)));assertEquals(30,stats.resolve(id).get("strength"));assertSame(stats.resolve(id),stats.resolve(id));ProgressionState state=new ProgressionState();assertTrue(state.addXp(100,new XpCurve(List.of(100L,300L))));assertEquals(2,state.level());assertEquals(1,state.skillPoints());assertEquals(5,state.addCollection("stone",5));}}
