package dev.modichamiya.eclipse.core.service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class ServiceRegistry {
    private final Map<Class<?>, Object> services = new ConcurrentHashMap<>();

    public <T> void register(Class<T> type, T service) {
        Objects.requireNonNull(type); Objects.requireNonNull(service);
        if (services.putIfAbsent(type, service) != null) throw new IllegalStateException("Service already registered: " + type.getName());
    }
    public <T> T require(Class<T> type) { return find(type).orElseThrow(() -> new IllegalStateException("Missing service: " + type.getName())); }
    public <T> Optional<T> find(Class<T> type) { return Optional.ofNullable(type.cast(services.get(type))); }
    public <T> void unregister(Class<T> type) { services.remove(type); }
    public void clear() { services.clear(); }
}
