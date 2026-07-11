package dev.modichamiya.eclipse.registry;

import dev.modichamiya.eclipse.core.event.EventBus;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public final class ContentRegistryService {
    public record ContentReloadedEvent(int registries, int entries) { }
    private final Path root; private final ContentLoader loader; private final RegistryManager manager; private final EventBus events;
    public ContentRegistryService(Path root, ContentLoader loader, RegistryManager manager, EventBus events) { this.root=Objects.requireNonNull(root); this.loader=Objects.requireNonNull(loader); this.manager=Objects.requireNonNull(manager); this.events=Objects.requireNonNull(events); }
    public synchronized void reload() throws IOException, ContentLoadException {
        var candidate = loader.load(root); manager.atomicSwap(candidate);
        int entries = candidate.values().stream().mapToInt(RegistrySnapshot::size).sum(); events.publish(new ContentReloadedEvent(candidate.size(), entries));
    }
}
