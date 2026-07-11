package dev.modichamiya.eclipse.gui;

import java.util.*;

public record GuiFrame(Map<Integer,Cell> cells,String title) {
    public record Cell(String componentId,String text,boolean interactive){public Cell{Objects.requireNonNull(componentId);Objects.requireNonNull(text);}}
    public GuiFrame { cells=Map.copyOf(cells); Objects.requireNonNull(title); }
    public Map<Integer,Cell> diff(GuiFrame previous){Map<Integer,Cell> changed=new LinkedHashMap<>(); Set<Integer> slots=new HashSet<>(cells.keySet());slots.addAll(previous.cells.keySet());for(int slot:slots)if(!Objects.equals(cells.get(slot),previous.cells.get(slot)))changed.put(slot,cells.get(slot));return changed;}
}
