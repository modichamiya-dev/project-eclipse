package dev.modichamiya.eclipse.gui;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;
import java.util.function.Function;

public final class Screen {
    private final ContentKey key; private final Function<GuiContext,String> title; private final List<GuiComponent> components;
    public Screen(ContentKey key,Function<GuiContext,String> title,List<GuiComponent> components){this.key=Objects.requireNonNull(key);this.title=Objects.requireNonNull(title);this.components=List.copyOf(components);Set<String>ids=new HashSet<>();for(var c:components)if(!ids.add(c.id()))throw new IllegalArgumentException("Duplicate component: "+c.id());}
    public ContentKey key(){return key;}
    public GuiFrame render(GuiContext context){Map<Integer,GuiFrame.Cell> cells=new LinkedHashMap<>();for(var component:components)component.render(cells,context);return new GuiFrame(cells,title.apply(context));}
    public boolean click(int slot,GuiContext context){for(var component:components)if(component.click(slot,context))return true;return false;}
}
