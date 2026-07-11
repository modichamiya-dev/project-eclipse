package dev.modichamiya.eclipse.core.event;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public final class EventBus {
    private final Map<Class<?>, CopyOnWriteArrayList<Consumer<?>>> listeners = new ConcurrentHashMap<>();

    public <T> AutoCloseable subscribe(Class<T> type, Consumer<T> listener) {
        Objects.requireNonNull(type); Objects.requireNonNull(listener);
        listeners.computeIfAbsent(type, ignored -> new CopyOnWriteArrayList<>()).add(listener);
        return () -> listeners.getOrDefault(type, new CopyOnWriteArrayList<>()).remove(listener);
    }

    @SuppressWarnings("unchecked")
    public <T> void publish(T event) {
        Objects.requireNonNull(event);
        for (Map.Entry<Class<?>, CopyOnWriteArrayList<Consumer<?>>> entry : listeners.entrySet()) {
            if (entry.getKey().isAssignableFrom(event.getClass())) for (Consumer<?> consumer : List.copyOf(entry.getValue())) ((Consumer<T>) consumer).accept(event);
        }
    }

    public void clear() { listeners.clear(); }
}
