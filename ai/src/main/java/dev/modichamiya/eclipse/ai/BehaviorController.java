package dev.modichamiya.eclipse.ai;

import java.util.Objects;

public final class BehaviorController { private final BehaviorNode root;private final Blackboard blackboard;private volatile boolean active=true;public BehaviorController(BehaviorNode root,Blackboard blackboard){this.root=Objects.requireNonNull(root);this.blackboard=Objects.requireNonNull(blackboard);}public BehaviorNode.Status tick(){return active?root.tick(blackboard):BehaviorNode.Status.FAILURE;}public Blackboard blackboard(){return blackboard;}public void stop(){active=false;root.reset(blackboard);} }
