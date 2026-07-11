package dev.modichamiya.eclipse.gui;

import dev.modichamiya.eclipse.registry.ContentKey;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class GuiFrameworkTest {
    @Test void rendersReactiveDiffAndRoutesClicks(){
        Screen screen=new Screen(ContentKey.parse("eclipse:test"),ctx->"Test",List.of(new Button("plus",0,ctx->"+",ctx->ctx.set("value",ctx.get("value",Integer.class).orElse(0)+1)),new ProgressBar("bar",1,4,ctx->ctx.get("value",Integer.class).orElse(0)/4d)));
        GuiService service=new GuiService();service.register(screen);GuiSession session=service.open(screen.key());assertEquals(5,session.renderDiff().size());assertTrue(session.click(0));Map<Integer,GuiFrame.Cell> diff=session.renderDiff();assertEquals("█",diff.get(1).text());
    }
}
