package dev.modichamiya.eclipse.assets;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.nio.file.Path;
import java.util.*;

public record AssetDefinition(ContentKey key, AssetType type, Path source, Map<String,Object> metadata) {
    public AssetDefinition { Objects.requireNonNull(key); Objects.requireNonNull(type); Objects.requireNonNull(source); metadata = Map.copyOf(metadata); }
}
