package dev.modichamiya.eclipse.registry;

import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public final class ContentLoader {
    public Map<String, RegistrySnapshot<? extends RegistryEntry>> load(Path root) throws IOException, ContentLoadException {
        Files.createDirectories(root); List<String> errors = new ArrayList<>(); Map<String,MutableRegistry<GenericDefinition>> builders = new LinkedHashMap<>(); Map<ContentKey,GenericDefinition> all = new LinkedHashMap<>();
        try (Stream<Path> paths = Files.walk(root)) {
            for (Path path : paths.filter(Files::isRegularFile).filter(this::supported).sorted().toList()) {
                try { GenericDefinition definition = parse(path); if (all.putIfAbsent(definition.key(), definition) != null) errors.add(path + ": duplicate key " + definition.key()); else builders.computeIfAbsent(definition.type(), ignored -> new MutableRegistry<>()).register(definition); }
                catch (Exception ex) { errors.add(path + ": " + ex.getMessage()); }
            }
        }
        if (errors.isEmpty()) for (GenericDefinition definition : all.values()) for (ContentKey reference : definition.references()) if (!all.containsKey(reference)) errors.add(definition.source() + ": " + definition.key() + " references missing key " + reference);
        if (!errors.isEmpty()) throw new ContentLoadException(errors);
        Map<String,RegistrySnapshot<? extends RegistryEntry>> result = new LinkedHashMap<>(); builders.forEach((type,builder) -> result.put(type,builder.freeze())); return Map.copyOf(result);
    }

    private boolean supported(Path path) { String name = path.getFileName().toString().toLowerCase(Locale.ROOT); return name.endsWith(".yml") || name.endsWith(".yaml"); }
    @SuppressWarnings("unchecked") private GenericDefinition parse(Path path) throws IOException {
        Object loaded; try (Reader reader = Files.newBufferedReader(path)) { loaded = new Yaml().load(reader); }
        if (!(loaded instanceof Map<?,?> raw)) throw new IllegalArgumentException("definition root must be a map");
        Map<String,Object> map = new LinkedHashMap<>(); raw.forEach((k,v) -> map.put(String.valueOf(k),v));
        String keyText = requiredString(map,"key"); String type = requiredString(map,"type");
        Set<String> tags = strings(map.get("tags"), "tags"); Set<ContentKey> refs = new LinkedHashSet<>(); for (String ref : strings(map.get("references"), "references")) refs.add(ContentKey.parse(ref));
        map.remove("key"); map.remove("type"); map.remove("tags"); map.remove("references");
        return new GenericDefinition(ContentKey.parse(keyText), type, tags, map, refs, path);
    }
    private String requiredString(Map<String,Object> map, String field) { Object value = map.get(field); if (!(value instanceof String text) || text.isBlank()) throw new IllegalArgumentException("required field '" + field + "' is missing"); return text; }
    private Set<String> strings(Object value, String field) { if (value == null) return Set.of(); if (!(value instanceof Collection<?> values)) throw new IllegalArgumentException("field '" + field + "' must be a list"); Set<String> result = new LinkedHashSet<>(); for (Object item : values) { if (!(item instanceof String text) || text.isBlank()) throw new IllegalArgumentException("field '" + field + "' contains a non-string"); result.add(text); } return result; }
}
