package dev.modichamiya.eclipse.registry;

import java.nio.file.Path;
import java.util.*;

public record GenericDefinition(ContentKey key, String type, Set<String> tags, Map<String,Object> data, Set<ContentKey> references, Path source) implements RegistryEntry {
    public GenericDefinition {
        Objects.requireNonNull(key); Objects.requireNonNull(type); tags = Set.copyOf(tags); data = Map.copyOf(data); references = Set.copyOf(references); Objects.requireNonNull(source);
    }
}
