package dev.modichamiya.eclipse.gui;

import java.util.Map;

public interface GuiComponent {
    String id();
    void render(Map<Integer,GuiFrame.Cell> cells,GuiContext context);
    default boolean click(int slot,GuiContext context){return false;}
}
