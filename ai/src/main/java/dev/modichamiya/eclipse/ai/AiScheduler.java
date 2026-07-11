package dev.modichamiya.eclipse.ai;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class AiScheduler { private final Queue<BehaviorController>controllers=new ConcurrentLinkedQueue<>();private final int budget;public AiScheduler(int budgetPerTick){if(budgetPerTick<1)throw new IllegalArgumentException();budget=budgetPerTick;}public void add(BehaviorController controller){controllers.add(Objects.requireNonNull(controller));}public int tick(){int processed=0;for(;processed<budget;processed++){BehaviorController controller=controllers.poll();if(controller==null)break;controller.tick();controllers.offer(controller);}return processed;}public int size(){return controllers.size();} }
