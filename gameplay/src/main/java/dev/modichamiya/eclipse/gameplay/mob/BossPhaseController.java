package dev.modichamiya.eclipse.gameplay.mob;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;

public final class BossPhaseController {public record Phase(double healthAtOrBelow,ContentKey behavior,ContentKey transition){public Phase{if(healthAtOrBelow<0||healthAtOrBelow>1)throw new IllegalArgumentException();Objects.requireNonNull(behavior);}}private final List<Phase>phases;private int index;public BossPhaseController(List<Phase>phases){this.phases=phases.stream().sorted(Comparator.comparingDouble(Phase::healthAtOrBelow).reversed()).toList();if(phases.isEmpty())throw new IllegalArgumentException();}public Optional<Phase>update(double healthRatio){int previous=index;while(index+1<phases.size()&&healthRatio<=phases.get(index+1).healthAtOrBelow())index++;return index!=previous?Optional.of(phases.get(index)):Optional.empty();}public Phase current(){return phases.get(index);}}
