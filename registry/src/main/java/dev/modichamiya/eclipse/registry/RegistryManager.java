package dev.modichamiya.eclipse.registry;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public final class RegistryManager {
    private final AtomicReference<Map<String, RegistrySnapshot<? extends RegistryEntry>>> live = new AtomicReference<>(Map.of());
    public Map<String, RegistrySnapshot<? extends RegistryEntry>> snapshot() { return live.get(); }
    @SuppressWarnings("unchecked") public <T extends RegistryEntry> RegistrySnapshot<T> registry(String type) {
        RegistrySnapshot<?> registry = live.get().get(type); if (registry == null) throw new NoSuchElementException("Unknown registry type: " + type); return (RegistrySnapshot<T>) registry;
    }
    public void atomicSwap(Map<String, RegistrySnapshot<? extends RegistryEntry>> validated) { live.set(Map.copyOf(validated)); }
}
