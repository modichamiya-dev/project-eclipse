package dev.modichamiya.eclipse.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.modichamiya.eclipse.api.profile.PlayerProfile;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

public final class PlayerProfileRepository {
    private final DatabaseProvider provider; private final Executor executor; private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    public PlayerProfileRepository(DatabaseProvider provider, Executor executor) { this.provider = Objects.requireNonNull(provider); this.executor = Objects.requireNonNull(executor); }

    public CompletableFuture<PlayerProfile> loadOrCreate(UUID id) { return CompletableFuture.supplyAsync(() -> {
        try (Connection c = provider.connection(); PreparedStatement ps = c.prepareStatement("SELECT profile_json FROM players WHERE uuid=?")) {
            ps.setString(1, id.toString()); try (ResultSet rs = ps.executeQuery()) { if (rs.next()) { PlayerProfile profile = mapper.readValue(rs.getString(1), PlayerProfile.class); profile.markClean(); return profile; } }
            PlayerProfile profile = PlayerProfile.create(id, Instant.now()); saveBlocking(profile); profile.markClean(); return profile;
        } catch (Exception e) { throw new CompletionException(e); }
    }, executor); }

    public CompletableFuture<Void> save(PlayerProfile profile) { return CompletableFuture.runAsync(() -> { try { saveBlocking(profile); profile.markClean(); } catch (Exception e) { throw new CompletionException(e); } }, executor); }

    private void saveBlocking(PlayerProfile profile) throws Exception {
        String json = mapper.writeValueAsString(profile);
        try (Connection c = provider.connection(); PreparedStatement ps = c.prepareStatement("INSERT INTO players(uuid, profile_json, updated_at) VALUES(?,?,?) ON CONFLICT(uuid) DO UPDATE SET profile_json=excluded.profile_json, updated_at=excluded.updated_at")) {
            ps.setString(1, profile.getUniqueId().toString()); ps.setString(2, json); ps.setLong(3, System.currentTimeMillis()); ps.executeUpdate();
        }
    }
}
