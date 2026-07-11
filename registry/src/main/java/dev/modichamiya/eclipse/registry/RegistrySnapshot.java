package dev.modichamiya.eclipse.registry;

import java.util.*;
import java.util.function.Predicate;

public final class RegistrySnapshot<T extends RegistryEntry> implements Iterable<T> {
    private final Map<ContentKey, T> entries;
    RegistrySnapshot(Map<ContentKey, T> entries) { this.entries = Map.copyOf(entries); }
    public Optional<T> find(ContentKey key) { return Optional.ofNullable(entries.get(key)); }
    public T require(ContentKey key) { return find(key).orElseThrow(() -> new NoSuchElementException("Unknown content key: " + key)); }
    public int size() { return entries.size(); }
    public List<T> query(Predicate<T> predicate) { return entries.values().stream().filter(predicate).toList(); }
    public Collection<T> values() { return entries.values(); }
    @Override public Iterator<T> iterator() { return values().iterator(); }
}
