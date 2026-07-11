package dev.modichamiya.eclipse.config;

import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public final class EclipseConfig {
    private final Path path;
    private volatile Map<String, Object> root = Map.of();
    public EclipseConfig(Path path) { this.path = Objects.requireNonNull(path); }

    public synchronized void load() throws IOException {
        Files.createDirectories(path.getParent());
        if (Files.notExists(path)) try (InputStream in = EclipseConfig.class.getResourceAsStream("/default-config.yml")) {
            if (in == null) throw new IOException("Bundled default-config.yml is missing"); Files.copy(in, path);
        }
        try (Reader reader = Files.newBufferedReader(path)) {
            Object parsed = new Yaml().load(reader);
            if (!(parsed instanceof Map<?,?> map)) throw new IOException("Config root must be a map");
            Map<String,Object> copy = new LinkedHashMap<>(); map.forEach((k,v) -> copy.put(String.valueOf(k), v)); root = Collections.unmodifiableMap(copy);
        }
        if (integer("config-version", 0) < 1) throw new IOException("Unsupported config-version");
    }

    public String string(String key, String fallback) { Object value = value(key); return value == null ? fallback : String.valueOf(value); }
    public int integer(String key, int fallback) { Object value = value(key); return value instanceof Number n ? n.intValue() : fallback; }
    public boolean bool(String key, boolean fallback) { Object value = value(key); return value instanceof Boolean b ? b : fallback; }
    @SuppressWarnings("unchecked") private Object value(String dotted) { Object cursor = root; for (String part : dotted.split("\\.")) { if (!(cursor instanceof Map<?,?> map)) return null; cursor = map.get(part); } return cursor; }
}
