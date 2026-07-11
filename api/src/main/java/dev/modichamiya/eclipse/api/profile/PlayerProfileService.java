package dev.modichamiya.eclipse.api.profile;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PlayerProfileService {
    CompletableFuture<PlayerProfile> load(UUID playerId);
    Optional<PlayerProfile> cached(UUID playerId);
    CompletableFuture<Void> save(UUID playerId);
    CompletableFuture<Void> unload(UUID playerId);
    CompletableFuture<Void> saveAll();
}
