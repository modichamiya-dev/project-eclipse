package dev.modichamiya.eclipse.gameplay.profession;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.time.*;
import java.util.*;

public final class MinionState {private final ContentKey resource;private final long intervalSeconds,capacity;private Instant simulatedAt;private long stored;public MinionState(ContentKey resource,long intervalSeconds,long capacity,Instant placedAt){this.resource=Objects.requireNonNull(resource);if(intervalSeconds<1||capacity<1)throw new IllegalArgumentException();this.intervalSeconds=intervalSeconds;this.capacity=capacity;this.simulatedAt=Objects.requireNonNull(placedAt);}public synchronized long simulate(Instant now){long elapsed=Math.max(0,Duration.between(simulatedAt,now).getSeconds()),cycles=elapsed/intervalSeconds,accepted=Math.min(cycles,capacity-stored);stored+=accepted;simulatedAt=simulatedAt.plusSeconds(cycles*intervalSeconds);return accepted;}public synchronized long collect(){long amount=stored;stored=0;return amount;}public ContentKey resource(){return resource;}public synchronized long stored(){return stored;}}
