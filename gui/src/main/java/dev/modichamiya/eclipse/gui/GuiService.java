package dev.modichamiya.eclipse.gui;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class GuiService {
    private final Map<ContentKey,Screen> screens=new ConcurrentHashMap<>();
    public void register(Screen screen){if(screens.putIfAbsent(screen.key(),screen)!=null)throw new IllegalArgumentException("Duplicate screen: "+screen.key());}
    public GuiSession open(ContentKey key){Screen screen=Optional.ofNullable(screens.get(key)).orElseThrow(() -> new NoSuchElementException("Unknown screen: "+key));GuiSession session=new GuiSession();session.push(screen);return session;}
}
