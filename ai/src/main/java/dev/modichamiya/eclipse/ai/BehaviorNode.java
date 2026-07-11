package dev.modichamiya.eclipse.ai;

public interface BehaviorNode { enum Status { SUCCESS, FAILURE, RUNNING } Status tick(Blackboard blackboard); default void reset(Blackboard blackboard){} }
