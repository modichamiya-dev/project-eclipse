package dev.modichamiya.eclipse.gameplay.social;

import dev.modichamiya.eclipse.registry.ContentKey;
import org.junit.jupiter.api.Test;
import java.time.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class SocialSystemsTest {@Test void formatsRanksAndSortsTab(){Rank rank=new Rank(ContentKey.parse("eclipse:owner"),"[OWNER]","#FF5555","#FFFFFF",100,true,Set.of("*"));RankService service=new RankService();service.register(rank);UUID player=UUID.randomUUID();service.grant(player,rank.key(),null);assertEquals(rank,service.primary(player,Instant.EPOCH).orElseThrow());assertTrue(new ChatFormatter().format(rank,"Modiji","hello","Global").plain().contains("[OWNER] Modiji"));var view=new TabListService().render(List.of(new TabEntry(player,"Modiji",100,1,10)),"Eclipse",1,300);assertEquals(player,view.entries().getFirst().player());}}
