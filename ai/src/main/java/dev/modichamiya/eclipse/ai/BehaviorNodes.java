package dev.modichamiya.eclipse.ai;

import java.util.*;
import java.util.function.*;

public final class BehaviorNodes { private BehaviorNodes(){}
    public static BehaviorNode action(Function<Blackboard,BehaviorNode.Status> action){return action::apply;}
    public static BehaviorNode condition(Predicate<Blackboard> condition){return board->condition.test(board)?BehaviorNode.Status.SUCCESS:BehaviorNode.Status.FAILURE;}
    public static BehaviorNode sequence(List<BehaviorNode> children){return composite(children,true);}
    public static BehaviorNode selector(List<BehaviorNode> children){return composite(children,false);}
    private static BehaviorNode composite(List<BehaviorNode> source,boolean sequence){List<BehaviorNode>children=List.copyOf(source);return new BehaviorNode(){int cursor;public Status tick(Blackboard board){while(cursor<children.size()){Status status=children.get(cursor).tick(board);if(status==Status.RUNNING)return status;if(sequence&&status==Status.FAILURE||!sequence&&status==Status.SUCCESS){cursor=0;return status;}cursor++;}cursor=0;return sequence?Status.SUCCESS:Status.FAILURE;}public void reset(Blackboard board){cursor=0;children.forEach(child->child.reset(board));}};}
}
