package dev.modichamiya.eclipse.animation;

import java.util.*;

public record EventTrack(String id, NavigableMap<Long,String> keyframes) implements TimelineTrack {
    public EventTrack { Objects.requireNonNull(id); keyframes=Collections.unmodifiableNavigableMap(new TreeMap<>(keyframes)); }
    public void apply(long previous,long current,double partial,TimelineContext context,TimelineSink sink){
        long from=previous<0 ? 0 : previous+1; if(current<from)return; keyframes.subMap(from,true,current,true).values().forEach(name -> sink.event(name,context));
    }
}
