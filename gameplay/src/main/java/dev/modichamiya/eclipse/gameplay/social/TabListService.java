package dev.modichamiya.eclipse.gameplay.social;

import java.util.*;

public final class TabListService {public record View(String header,String footer,List<TabEntry>entries){}public View render(Collection<TabEntry>entries,String serverName,int online,int capacity){List<TabEntry>sorted=entries.stream().sorted().toList();return new View("✦ "+serverName+" ✦","Players "+online+"/"+capacity+"  •  play.eclipse",sorted);}}
