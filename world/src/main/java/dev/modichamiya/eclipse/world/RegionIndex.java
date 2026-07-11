package dev.modichamiya.eclipse.world;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public final class RegionIndex {
    private final AtomicReference<List<Region>> regions=new AtomicReference<>(List.of());
    public void replace(Collection<Region> replacement){regions.set(List.copyOf(replacement));}
    public Optional<Region> find(WorldPoint point){return regions.get().stream().filter(region->region.contains(point)).findFirst();}
}
