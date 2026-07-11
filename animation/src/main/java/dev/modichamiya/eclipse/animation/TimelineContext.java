package dev.modichamiya.eclipse.animation;

import java.util.*;

public final class TimelineContext {
    private final Map<String,Object> values;
    public TimelineContext(Map<String,Object> values){this.values=Map.copyOf(values);}
    public static TimelineContext empty(){return new TimelineContext(Map.of());}
    public <T> Optional<T> find(String key,Class<T> type){return Optional.ofNullable(values.get(key)).filter(type::isInstance).map(type::cast);}
}
