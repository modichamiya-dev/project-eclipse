package dev.modichamiya.eclipse.registry;

import java.util.*;

public final class MutableRegistry<T extends RegistryEntry> {
    private final Map<ContentKey,T> entries = new LinkedHashMap<>(); private boolean frozen;
    public void register(T entry) {
        Objects.requireNonNull(entry); if (frozen) throw new IllegalStateException("Registry is frozen");
        if (entries.putIfAbsent(entry.key(), entry) != null) throw new IllegalArgumentException("Duplicate content key: " + entry.key());
    }
    public RegistrySnapshot<T> freeze() { frozen = true; return new RegistrySnapshot<>(entries); }
}
