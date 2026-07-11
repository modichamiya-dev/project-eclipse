package dev.modichamiya.eclipse.animation;

import java.util.function.DoubleUnaryOperator;

public enum Easing {
    LINEAR(t -> t), EASE_IN(t -> t*t), EASE_OUT(t -> 1-(1-t)*(1-t)), EASE_IN_OUT(t -> t<.5 ? 2*t*t : 1-Math.pow(-2*t+2,2)/2), CUBIC(t -> t*t*t);
    private final DoubleUnaryOperator function; Easing(DoubleUnaryOperator function){this.function=function;}
    public double apply(double value){return function.applyAsDouble(Math.max(0,Math.min(1,value)));}
}
