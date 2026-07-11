package dev.modichamiya.eclipse.api.profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.*;

public final class PlayerProfile {
    private final UUID uniqueId;
    private final Instant createdAt;
    private Instant lastSeenAt;
    private int level;
    private long experience;
    private int skillPoints;
    private int talentPoints;
    private final Set<String> titles;
    private final Set<String> achievements;
    private final Map<String, Integer> masteries;
    private final Map<String, Integer> prestige;
    private final Map<String, Integer> reputation;
    private final Set<String> discoveries;
    private transient volatile boolean dirty;

    @JsonCreator
    public PlayerProfile(@JsonProperty("uniqueId") UUID uniqueId, @JsonProperty("createdAt") Instant createdAt,
        @JsonProperty("lastSeenAt") Instant lastSeenAt, @JsonProperty("level") int level,
        @JsonProperty("experience") long experience, @JsonProperty("skillPoints") int skillPoints,
        @JsonProperty("talentPoints") int talentPoints, @JsonProperty("titles") Set<String> titles,
        @JsonProperty("achievements") Set<String> achievements, @JsonProperty("masteries") Map<String,Integer> masteries,
        @JsonProperty("prestige") Map<String,Integer> prestige, @JsonProperty("reputation") Map<String,Integer> reputation,
        @JsonProperty("discoveries") Set<String> discoveries) {
        this.uniqueId = Objects.requireNonNull(uniqueId); this.createdAt = Objects.requireNonNull(createdAt);
        this.lastSeenAt = Objects.requireNonNull(lastSeenAt); this.level = level; this.experience = experience;
        this.skillPoints = skillPoints; this.talentPoints = talentPoints;
        this.titles = new HashSet<>(titles == null ? Set.of() : titles); this.achievements = new HashSet<>(achievements == null ? Set.of() : achievements);
        this.masteries = new HashMap<>(masteries == null ? Map.of() : masteries); this.prestige = new HashMap<>(prestige == null ? Map.of() : prestige);
        this.reputation = new HashMap<>(reputation == null ? Map.of() : reputation); this.discoveries = new HashSet<>(discoveries == null ? Set.of() : discoveries);
    }

    public static PlayerProfile create(UUID id, Instant now) { return new PlayerProfile(id, now, now, 1, 0, 0, 0, Set.of(), Set.of(), Map.of(), Map.of(), Map.of(), Set.of()); }
    public UUID getUniqueId() { return uniqueId; } public Instant getCreatedAt() { return createdAt; } public Instant getLastSeenAt() { return lastSeenAt; }
    public int getLevel() { return level; } public long getExperience() { return experience; } public int getSkillPoints() { return skillPoints; } public int getTalentPoints() { return talentPoints; }
    public Set<String> getTitles() { return Collections.unmodifiableSet(titles); } public Set<String> getAchievements() { return Collections.unmodifiableSet(achievements); }
    public Map<String,Integer> getMasteries() { return Collections.unmodifiableMap(masteries); } public Map<String,Integer> getPrestige() { return Collections.unmodifiableMap(prestige); }
    public Map<String,Integer> getReputation() { return Collections.unmodifiableMap(reputation); } public Set<String> getDiscoveries() { return Collections.unmodifiableSet(discoveries); }
    public boolean isDirty() { return dirty; } public void markClean() { dirty = false; }
    public void touch(Instant now) { lastSeenAt = Objects.requireNonNull(now); dirty = true; }
}
