package dev.modichamiya.eclipse.gui;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public record Button(String id,int slot,Function<GuiContext,String> label,Consumer<GuiContext> action) implements GuiComponent {
    public Button{Objects.requireNonNull(id);Objects.requireNonNull(label);Objects.requireNonNull(action);if(slot<0)throw new IllegalArgumentException("slot");}
    public void render(Map<Integer,GuiFrame.Cell> cells,GuiContext context){cells.put(slot,new GuiFrame.Cell(id,label.apply(context),true));}
    public boolean click(int clicked,GuiContext context){if(clicked!=slot)return false;action.accept(context);return true;}
}
