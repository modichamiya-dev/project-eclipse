package dev.modichamiya.eclipse.gui;

import java.util.*;
import java.util.function.ToDoubleFunction;

public record ProgressBar(String id,int startSlot,int width,ToDoubleFunction<GuiContext> progress) implements GuiComponent {
    public ProgressBar{Objects.requireNonNull(id);Objects.requireNonNull(progress);if(startSlot<0||width<1)throw new IllegalArgumentException();}
    public void render(Map<Integer,GuiFrame.Cell> cells,GuiContext context){double value=Math.max(0,Math.min(1,progress.applyAsDouble(context)));int filled=(int)Math.round(value*width);for(int i=0;i<width;i++)cells.put(startSlot+i,new GuiFrame.Cell(id,i<filled?"█":"░",false));}
}
