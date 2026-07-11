package dev.modichamiya.eclipse.core.module;

import java.util.Set;

public interface EclipseModule {
    String id();
    default Set<String> dependencies() { return Set.of(); }
    default void onLoad() throws Exception { }
    default void onEnable() throws Exception { }
    default void onDisable() throws Exception { }
}
