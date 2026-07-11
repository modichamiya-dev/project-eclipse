package dev.modichamiya.eclipse.core.module;

import java.util.*;
import java.util.logging.Logger;

public final class ModuleManager {
    private final Logger logger;
    private final Map<String, EclipseModule> modules = new LinkedHashMap<>();
    private List<EclipseModule> order = List.of();

    public ModuleManager(Logger logger) { this.logger = Objects.requireNonNull(logger); }

    public void register(EclipseModule module) {
        Objects.requireNonNull(module);
        if (modules.putIfAbsent(module.id(), module) != null) throw new IllegalArgumentException("Duplicate module: " + module.id());
    }

    public void loadAndEnable() throws Exception {
        order = resolve();
        for (EclipseModule module : order) { logger.info("[module:" + module.id() + "] loading"); module.onLoad(); }
        int enabled = 0;
        try {
            for (EclipseModule module : order) { logger.info("[module:" + module.id() + "] enabling"); module.onEnable(); enabled++; }
        } catch (Exception failure) {
            disablePrefix(enabled);
            throw failure;
        }
    }

    public void disableAll() {
        disablePrefix(order.size());
    }

    public List<String> enabledModuleIds() { return order.stream().map(EclipseModule::id).toList(); }

    private void disablePrefix(int count) {
        for (int i = Math.min(count, order.size()) - 1; i >= 0; i--) {
            EclipseModule module = order.get(i);
            try { logger.info("[module:" + module.id() + "] disabling"); module.onDisable(); }
            catch (Exception ex) { logger.severe("Failed to disable " + module.id() + ": " + ex.getMessage()); }
        }
    }

    private List<EclipseModule> resolve() {
        for (EclipseModule module : modules.values()) {
            for (String dep : module.dependencies()) if (!modules.containsKey(dep)) throw new IllegalStateException("Module " + module.id() + " requires missing module " + dep);
        }
        Map<String, Integer> state = new HashMap<>();
        List<EclipseModule> result = new ArrayList<>();
        for (String id : modules.keySet()) visit(id, state, result, new ArrayDeque<>());
        return List.copyOf(result);
    }

    private void visit(String id, Map<String, Integer> state, List<EclipseModule> result, Deque<String> path) {
        int current = state.getOrDefault(id, 0);
        if (current == 2) return;
        if (current == 1) throw new IllegalStateException("Module dependency cycle: " + path + " -> " + id);
        state.put(id, 1); path.addLast(id);
        for (String dependency : modules.get(id).dependencies()) visit(dependency, state, result, path);
        path.removeLast(); state.put(id, 2); result.add(modules.get(id));
    }
}
