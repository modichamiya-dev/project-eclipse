package dev.modichamiya.eclipse.core.module;

import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;

class ModuleManagerTest {
    @Test void resolvesDependenciesAndReversesShutdown() throws Exception {
        List<String> calls = new ArrayList<>(); ModuleManager manager = new ModuleManager(Logger.getAnonymousLogger());
        manager.register(module("child", Set.of("root"), calls)); manager.register(module("root", Set.of(), calls)); manager.loadAndEnable(); manager.disableAll();
        assertEquals(List.of("load:root","load:child","enable:root","enable:child","disable:child","disable:root"), calls);
    }
    @Test void rejectsCycles() { ModuleManager manager = new ModuleManager(Logger.getAnonymousLogger()); manager.register(module("a", Set.of("b"), new ArrayList<>())); manager.register(module("b", Set.of("a"), new ArrayList<>())); assertThrows(IllegalStateException.class, manager::loadAndEnable); }
    private EclipseModule module(String id, Set<String> deps, List<String> calls) { return new EclipseModule() { public String id(){return id;} public Set<String> dependencies(){return deps;} public void onLoad(){calls.add("load:"+id);} public void onEnable(){calls.add("enable:"+id);} public void onDisable(){calls.add("disable:"+id);} }; }
}
