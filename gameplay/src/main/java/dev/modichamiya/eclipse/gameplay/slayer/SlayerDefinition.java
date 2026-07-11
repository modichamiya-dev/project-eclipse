package dev.modichamiya.eclipse.gameplay.slayer;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;

public record SlayerDefinition(ContentKey key,String mobFamily,Map<Integer,Tier>tiers,List<Long>xpCurve){public record Tier(int tier,long requiredKillXp,ContentKey boss,ContentKey loot,long rewardXp){public Tier{if(tier<1||requiredKillXp<1||rewardXp<0)throw new IllegalArgumentException();Objects.requireNonNull(boss);Objects.requireNonNull(loot);}}public SlayerDefinition{Objects.requireNonNull(key);Objects.requireNonNull(mobFamily);tiers=Map.copyOf(tiers);xpCurve=List.copyOf(xpCurve);}}
