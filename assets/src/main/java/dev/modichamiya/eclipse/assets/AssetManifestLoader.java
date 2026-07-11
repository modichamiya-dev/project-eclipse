package dev.modichamiya.eclipse.assets;

import dev.modichamiya.eclipse.registry.ContentKey;
import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public final class AssetManifestLoader {
    public List<AssetDefinition> load(Path sourceRoot, Path manifest) throws IOException {
        Object parsed; try (Reader reader = Files.newBufferedReader(manifest)) { parsed = new Yaml().load(reader); }
        if (!(parsed instanceof Map<?,?> root) || !(root.get("assets") instanceof Collection<?> rows)) throw new IOException("Manifest requires an assets list");
        List<AssetDefinition> result = new ArrayList<>(); Set<ContentKey> keys = new HashSet<>();
        for (Object row : rows) {
            if (!(row instanceof Map<?,?> raw)) throw new IOException("Asset row must be a map");
            String keyText = string(raw,"key"); String typeText = string(raw,"type"); String fileText = string(raw,"file");
            ContentKey key = ContentKey.parse(keyText); if (!keys.add(key)) throw new IOException("Duplicate asset key: " + key);
            Path source = sourceRoot.resolve(fileText).normalize(); if (!source.startsWith(sourceRoot.normalize())) throw new IOException("Asset path escapes source root: " + fileText);
            Map<String,Object> metadata = new LinkedHashMap<>(); raw.forEach((k,v) -> { String field=String.valueOf(k); if (!Set.of("key","type","file").contains(field)) metadata.put(field,v); });
            result.add(new AssetDefinition(key, AssetType.valueOf(typeText.toUpperCase(Locale.ROOT)), source, metadata));
        }
        return List.copyOf(result);
    }
    private String string(Map<?,?> map, String field) throws IOException { Object value=map.get(field); if (!(value instanceof String text) || text.isBlank()) throw new IOException("Missing asset field: " + field); return text; }
}
