package dev.modichamiya.eclipse;

import dev.modichamiya.eclipse.api.event.ProfileLoadedEvent;
import dev.modichamiya.eclipse.api.profile.*;
import dev.modichamiya.eclipse.config.EclipseConfig;
import dev.modichamiya.eclipse.core.event.EventBus;
import dev.modichamiya.eclipse.core.module.*;
import dev.modichamiya.eclipse.core.service.ServiceRegistry;
import dev.modichamiya.eclipse.database.*;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

public final class EclipsePlugin extends JavaPlugin implements Listener, CommandExecutor {
    private final ServiceRegistry services = new ServiceRegistry(); private final EventBus events = new EventBus();
    private ModuleManager modules; private ExecutorService databaseExecutor; private BukkitTask autosaveTask; private EclipseConfig config;

    @Override public void onEnable() {
        try {
            saveResource("config.yml", false); config = new EclipseConfig(getDataFolder().toPath().resolve("config.yml")); config.load();
            databaseExecutor = Executors.newFixedThreadPool(Math.max(2, config.integer("database.pool-size", 1) + 1), Thread.ofPlatform().name("eclipse-db-", 0).factory());
            modules = new ModuleManager(getLogger()); registerModules(); modules.loadAndEnable();
            Bukkit.getPluginManager().registerEvents(this, this); Objects.requireNonNull(getCommand("eclipse")).setExecutor(this);
            long interval = Math.max(30, config.integer("profiles.autosave-seconds", 300)) * 20L;
            autosaveTask = Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> services.require(PlayerProfileService.class).saveAll().exceptionally(ex -> { getLogger().severe("Autosave failed: " + ex.getMessage()); return null; }), interval, interval);
            for (Player player : Bukkit.getOnlinePlayers()) loadProfile(player);
        } catch (Exception failure) { getLogger().log(java.util.logging.Level.SEVERE, "Project Eclipse failed to start", failure); Bukkit.getPluginManager().disablePlugin(this); }
    }

    private void registerModules() {
        services.register(ServiceRegistry.class, services); services.register(EventBus.class, events);
        modules.register(new EclipseModule() { public String id() { return "core"; } });
        modules.register(new EclipseModule() {
            private SQLiteDatabaseProvider provider;
            public String id() { return "database"; } public Set<String> dependencies() { return Set.of("core"); }
            public void onEnable() throws Exception {
                Path file = getDataFolder().toPath().resolve(config.string("database.file", "eclipse.db"));
                provider = new SQLiteDatabaseProvider(file, config.integer("database.pool-size", 1), config.integer("database.connection-timeout-ms", 10000)); provider.start(); new MigrationRunner().migrate(provider);
                services.register(DatabaseProvider.class, provider); PlayerProfileRepository repository = new PlayerProfileRepository(provider, databaseExecutor);
                services.register(PlayerProfileService.class, new CachedProfileService(repository));
            }
            public void onDisable() { services.find(PlayerProfileService.class).ifPresent(s -> s.saveAll().join()); services.unregister(PlayerProfileService.class); services.unregister(DatabaseProvider.class); if (provider != null) provider.close(); }
        });
        for (String id : List.of("config","api","assets","registry","animation","gui","world","ai","admin","gameplay","content")) modules.register(new EclipseModule() { public String id() { return id; } public Set<String> dependencies() { return Set.of("core"); } });
    }

    @Override public void onDisable() {
        if (autosaveTask != null) autosaveTask.cancel(); if (modules != null) modules.disableAll();
        events.clear(); services.clear(); if (databaseExecutor != null) { databaseExecutor.shutdown(); try { if (!databaseExecutor.awaitTermination(10, TimeUnit.SECONDS)) databaseExecutor.shutdownNow(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); databaseExecutor.shutdownNow(); } }
    }

    @EventHandler public void onJoin(PlayerJoinEvent event) { loadProfile(event.getPlayer()); }
    @EventHandler public void onQuit(PlayerQuitEvent event) { services.require(PlayerProfileService.class).unload(event.getPlayer().getUniqueId()).exceptionally(ex -> { getLogger().severe("Profile unload failed: " + ex.getMessage()); return null; }); }
    private void loadProfile(Player player) { services.require(PlayerProfileService.class).load(player.getUniqueId()).thenAccept(profile -> Bukkit.getScheduler().runTask(this, () -> { if (player.isOnline()) events.publish(new ProfileLoadedEvent(profile)); })).exceptionally(ex -> { getLogger().severe("Profile load failed for " + player.getUniqueId() + ": " + ex.getMessage()); Bukkit.getScheduler().runTask(this, () -> player.kick(net.kyori.adventure.text.Component.text("Your Eclipse profile could not be loaded. Please reconnect."))); return null; }); }

    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("eclipse.admin")) { sender.sendMessage("You do not have permission."); return true; }
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) { try { config.load(); sender.sendMessage("Eclipse configuration reloaded."); } catch (Exception e) { sender.sendMessage("Reload rejected: " + e.getMessage()); } return true; }
        if (args.length >= 2 && args[0].equalsIgnoreCase("module") && args[1].equalsIgnoreCase("list")) { sender.sendMessage("Modules: " + String.join(", ", modules.enabledModuleIds())); return true; }
        if (args.length == 2 && args[0].equalsIgnoreCase("profile")) { Player target = Bukkit.getPlayerExact(args[1]); if (target == null) { sender.sendMessage("Player is not online."); return true; } services.require(PlayerProfileService.class).cached(target.getUniqueId()).ifPresentOrElse(p -> sender.sendMessage("Profile " + p.getUniqueId() + ": level=" + p.getLevel() + ", xp=" + p.getExperience()), () -> sender.sendMessage("Profile is still loading.")); return true; }
        sender.sendMessage("Usage: /eclipse reload | module list | profile <player>"); return true;
    }

    private static final class CachedProfileService implements PlayerProfileService {
        private final PlayerProfileRepository repository; private final ConcurrentMap<UUID, PlayerProfile> cache = new ConcurrentHashMap<>();
        CachedProfileService(PlayerProfileRepository repository) { this.repository = repository; }
        public CompletableFuture<PlayerProfile> load(UUID id) { PlayerProfile cached = cache.get(id); if (cached != null) return CompletableFuture.completedFuture(cached); return repository.loadOrCreate(id).thenApply(profile -> { PlayerProfile race = cache.putIfAbsent(id, profile); return race == null ? profile : race; }); }
        public Optional<PlayerProfile> cached(UUID id) { return Optional.ofNullable(cache.get(id)); }
        public CompletableFuture<Void> save(UUID id) { PlayerProfile profile = cache.get(id); return profile == null || !profile.isDirty() ? CompletableFuture.completedFuture(null) : repository.save(profile); }
        public CompletableFuture<Void> unload(UUID id) { PlayerProfile profile = cache.remove(id); if (profile == null) return CompletableFuture.completedFuture(null); profile.touch(Instant.now()); return repository.save(profile); }
        public CompletableFuture<Void> saveAll() { return CompletableFuture.allOf(cache.values().stream().filter(PlayerProfile::isDirty).map(repository::save).toArray(CompletableFuture[]::new)); }
    }
}
