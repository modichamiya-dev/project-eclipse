package dev.modichamiya.eclipse.gameplay.mob;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;

public final class MobInstance {public enum Variant{NORMAL,ELITE,CHAMPION,MINIBOSS,WORLD_BOSS,RAID_BOSS}private final UUID id;private final ContentKey definition;private final Variant variant;private final double maximumHealth;private double health;public MobInstance(UUID id,ContentKey definition,Variant variant,double maximumHealth){this.id=Objects.requireNonNull(id);this.definition=Objects.requireNonNull(definition);this.variant=Objects.requireNonNull(variant);this.maximumHealth=maximumHealth;this.health=maximumHealth;}public UUID id(){return id;}public ContentKey definition(){return definition;}public Variant variant(){return variant;}public synchronized double damage(double amount){health=Math.max(0,health-Math.max(0,amount));return health;}public synchronized double health(){return health;}public double maximumHealth(){return maximumHealth;}}
