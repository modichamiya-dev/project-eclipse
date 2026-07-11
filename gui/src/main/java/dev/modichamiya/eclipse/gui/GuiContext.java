package dev.modichamiya.eclipse.gui;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class GuiContext {
    private final Map<String,Object> state=new ConcurrentHashMap<>(); private volatile boolean dirty=true;
    public <T> Optional<T> get(String key,Class<T> type){return Optional.ofNullable(state.get(key)).filter(type::isInstance).map(type::cast);}
    public void set(String key,Object value){state.put(key,Objects.requireNonNull(value));dirty=true;}
    public boolean consumeDirty(){boolean value=dirty;dirty=false;return value;}
}
