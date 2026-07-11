package dev.modichamiya.eclipse.world;

import dev.modichamiya.eclipse.registry.ContentKey;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

class WorldEngineTest {
    @Test void indexesPolygonAndCapsInstances(){Region region=new Region(ContentKey.parse("eclipse:hub"),"world",List.of(new Region.Point(0,0),new Region.Point(10,0),new Region.Point(10,10),new Region.Point(0,10)),0,255,Map.of());RegionIndex index=new RegionIndex();index.replace(List.of(region));assertEquals(region,index.find(new WorldPoint("world",5,64,5)).orElseThrow());
        DimensionService.Backend backend=new DimensionService.Backend(){public CompletableFuture<Void>create(String id,ContentKey t){return CompletableFuture.completedFuture(null);}public CompletableFuture<Void>destroy(String id){return CompletableFuture.completedFuture(null);}};DimensionService service=new DimensionService(backend,1);service.provision(ContentKey.parse("eclipse:dungeon")).join();assertThrows(CompletionException.class,()->service.provision(ContentKey.parse("eclipse:dungeon")).join());service.close();}
}
