package dev.modichamiya.eclipse.api.event;

import dev.modichamiya.eclipse.api.profile.PlayerProfile;
import java.util.Objects;

public record ProfileLoadedEvent(PlayerProfile profile) { public ProfileLoadedEvent { Objects.requireNonNull(profile); } }
