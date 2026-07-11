package dev.modichamiya.eclipse.animation;

public interface TimelineSink {
    void event(String name,TimelineContext context);
    default void effect(String trackId,Object value,TimelineContext context) { }
}
