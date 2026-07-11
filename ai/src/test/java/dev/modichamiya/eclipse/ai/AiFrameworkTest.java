package dev.modichamiya.eclipse.ai;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class AiFrameworkTest { @Test void selectsThreatAndBudgetsTrees(){ThreatTable<String>table=new ThreatTable<>();table.add("tank",10);table.add("healer",20);assertEquals("healer",table.highest().orElseThrow());Blackboard board=new Blackboard();BehaviorController controller=new BehaviorController(BehaviorNodes.sequence(List.of(BehaviorNodes.condition(b->true),BehaviorNodes.action(b->{b.put("ran",true);return BehaviorNode.Status.SUCCESS;}))),board);AiScheduler scheduler=new AiScheduler(1);scheduler.add(controller);assertEquals(1,scheduler.tick());assertTrue(board.get("ran",Boolean.class).orElse(false));} }
