package dev.modichamiya.eclipse.animation;

import java.util.*;

public record NumericTrack(String id,NavigableMap<Long,Keyframe> keyframes) implements TimelineTrack {
    public record Keyframe(double value,Easing easing){public Keyframe{Objects.requireNonNull(easing);}}
    public NumericTrack { Objects.requireNonNull(id); keyframes=Collections.unmodifiableNavigableMap(new TreeMap<>(keyframes)); if(keyframes.isEmpty())throw new IllegalArgumentException("Numeric track requires keyframes"); }
    public void apply(long previous,long current,double partial,TimelineContext context,TimelineSink sink){
        double time=current+partial; var floor=keyframes.floorEntry((long)Math.floor(time)); var ceil=keyframes.ceilingEntry((long)Math.ceil(time));
        if(floor==null)floor=keyframes.firstEntry(); if(ceil==null)ceil=keyframes.lastEntry(); double value;
        if(floor.getKey().equals(ceil.getKey()))value=floor.getValue().value(); else {double t=(time-floor.getKey())/(ceil.getKey()-floor.getKey()); t=ceil.getValue().easing().apply(t); value=floor.getValue().value()+(ceil.getValue().value()-floor.getValue().value())*t;}
        sink.effect(id,value,context);
    }
}
