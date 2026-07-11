package dev.modichamiya.eclipse.animation;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;

public record Timeline(ContentKey key,long durationTicks,boolean loop,List<TimelineTrack> tracks) {
    public Timeline { Objects.requireNonNull(key); if(durationTicks<1)throw new IllegalArgumentException("durationTicks must be positive"); tracks=List.copyOf(tracks); Set<String> ids=new HashSet<>(); for(var track:tracks)if(!ids.add(track.id()))throw new IllegalArgumentException("Duplicate track id: "+track.id()); }
}
