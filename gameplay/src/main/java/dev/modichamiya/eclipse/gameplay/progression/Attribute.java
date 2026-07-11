package dev.modichamiya.eclipse.gameplay.progression;

import java.util.Objects;

public record Attribute(String key,double base,double minimum,double maximum){public Attribute{Objects.requireNonNull(key);if(!Double.isFinite(base)||minimum>maximum)throw new IllegalArgumentException();}public double clamp(double value){return Math.max(minimum,Math.min(maximum,value));}}
