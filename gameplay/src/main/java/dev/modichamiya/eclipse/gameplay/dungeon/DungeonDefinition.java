package dev.modichamiya.eclipse.gameplay.dungeon;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.time.Duration;
import java.util.*;

public record DungeonDefinition(ContentKey key,ContentKey dimension,List<Room>rooms,ContentKey boss,Duration timeout,int partyMin,int partyMax){public record Room(String id,String type,Set<String>next,boolean secret){public Room{Objects.requireNonNull(id);Objects.requireNonNull(type);next=Set.copyOf(next);}}public DungeonDefinition{Objects.requireNonNull(key);Objects.requireNonNull(dimension);rooms=List.copyOf(rooms);Objects.requireNonNull(timeout);if(rooms.isEmpty()||timeout.isNegative()||partyMin<1||partyMax<partyMin)throw new IllegalArgumentException();Set<String>ids=new HashSet<>();for(Room room:rooms)if(!ids.add(room.id()))throw new IllegalArgumentException("Duplicate room");for(Room room:rooms)for(String next:room.next())if(!ids.contains(next))throw new IllegalArgumentException("Missing room "+next);}}
