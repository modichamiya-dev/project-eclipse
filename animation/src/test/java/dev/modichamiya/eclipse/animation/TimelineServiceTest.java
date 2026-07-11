package dev.modichamiya.eclipse.animation;

import dev.modichamiya.eclipse.registry.ContentKey;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class TimelineServiceTest {
    @Test void firesExactEventsAndCompletes(){
        List<String> events=new ArrayList<>(); TimelineService service=new TimelineService(); ContentKey key=ContentKey.parse("eclipse:meteor"); service.register(new Timeline(key,5,false,List.of(new EventTrack("events",new TreeMap<>(Map.of(2L,"impact",5L,"done"))))));
        TimelineInstance instance=service.play(key,TimelineContext.empty(),new TimelineSink(){public void event(String name,TimelineContext context){events.add(name);}}); for(int i=0;i<6;i++)service.tick();
        assertEquals(List.of("impact","done"),events); assertEquals(TimelineInstance.State.COMPLETED,instance.state()); assertEquals(0,service.activeCount());
    }
}
