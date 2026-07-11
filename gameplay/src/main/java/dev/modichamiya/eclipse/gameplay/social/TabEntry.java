package dev.modichamiya.eclipse.gameplay.social;

import java.util.UUID;

public record TabEntry(UUID player,String displayName,int rankWeight,int level,int ping) implements Comparable<TabEntry>{public int compareTo(TabEntry other){int rank=Integer.compare(other.rankWeight,rankWeight);return rank!=0?rank:String.CASE_INSENSITIVE_ORDER.compare(displayName,other.displayName);}}
