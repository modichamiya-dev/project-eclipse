package dev.modichamiya.eclipse.gameplay.progression;

import java.util.Objects;

public record StatModifier(String source,String attribute,double additive,double multiplier){public StatModifier{Objects.requireNonNull(source);Objects.requireNonNull(attribute);if(!Double.isFinite(additive)||!Double.isFinite(multiplier))throw new IllegalArgumentException();}}
