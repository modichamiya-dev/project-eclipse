package dev.modichamiya.eclipse.gameplay.progression;

import java.util.*;

public final class XpCurve {private final List<Long>thresholds;public XpCurve(List<Long>thresholds){this.thresholds=List.copyOf(thresholds);long previous=-1;for(long value:thresholds){if(value<=previous)throw new IllegalArgumentException("XP thresholds must increase");previous=value;}}public int levelFor(long xp){int level=1;for(long threshold:thresholds){if(xp<threshold)break;level++;}return level;}public long thresholdFor(int level){if(level<=1)return 0;return thresholds.get(level-2);}}
