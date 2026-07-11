package dev.modichamiya.eclipse.gui;

import java.util.*;

public final class GuiSession {
    private final Deque<Screen> stack=new ArrayDeque<>(); private final GuiContext context=new GuiContext(); private GuiFrame last;
    public GuiContext context(){return context;} public void push(Screen screen){stack.push(Objects.requireNonNull(screen));context.set("navigation.changed",System.nanoTime());}
    public Optional<Screen> pop(){Screen value=stack.poll();context.set("navigation.changed",System.nanoTime());return Optional.ofNullable(value);} public Optional<Screen> current(){return Optional.ofNullable(stack.peek());}
    public Optional<GuiFrame> renderIfDirty(){if(!context.consumeDirty()&&last!=null)return Optional.empty();Screen screen=stack.peek();if(screen==null)return Optional.empty();last=screen.render(context);return Optional.of(last);}
    public Map<Integer,GuiFrame.Cell> renderDiff(){Screen screen=stack.peek();if(screen==null)return Map.of();GuiFrame next=screen.render(context);Map<Integer,GuiFrame.Cell> diff=last==null?next.cells():next.diff(last);last=next;context.consumeDirty();return diff;}
    public boolean click(int slot){Screen screen=stack.peek();return screen!=null&&screen.click(slot,context);}
}
