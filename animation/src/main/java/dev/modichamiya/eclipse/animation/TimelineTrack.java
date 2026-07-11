package dev.modichamiya.eclipse.animation;

public interface TimelineTrack {
    String id();
    void apply(long previousTick,long currentTick,double partialTick,TimelineContext context,TimelineSink sink);
    default void cleanup(TimelineContext context,TimelineSink sink) { }
}
