package dev.modichamiya.eclipse.admin;

import dev.modichamiya.eclipse.registry.ContentKey;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class EditorServiceTest {@Test void validatesLocksAndAudits(){EditorService service=new EditorService();service.register(new DefinitionSchema("item",List.of(new FieldSchema("name",String.class,true,null,v->!((String)v).isBlank(),"blank"))));var draft=service.begin(ContentKey.parse("eclipse:blade"),"item","admin");assertThrows(IllegalArgumentException.class,()->service.publish(draft,Map.of("name","")));var published=service.publish(draft,Map.of("name","Blade"));assertEquals(1,published.version());assertEquals(1,service.audit().size());}}
