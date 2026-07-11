package dev.modichamiya.eclipse.assets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

class ResourcePackBuilderTest {
    @TempDir Path temp;
    @Test void outputIsDeterministic() throws Exception {
        Path root=temp.resolve("src"); Files.createDirectories(root.resolve("assets/eclipse/textures/item")); Files.write(root.resolve("assets/eclipse/textures/item/blade.png"),new byte[]{1,2,3});
        Path manifest=root.resolve("assets.yml"); Files.writeString(manifest,"assets:\n  - key: eclipse:blade\n    type: texture\n    file: assets/eclipse/textures/item/blade.png\n");
        var builder=new ResourcePackBuilder(); var first=builder.build(root,manifest,temp.resolve("one.zip"),64); var second=builder.build(root,manifest,temp.resolve("two.zip"),64);
        assertEquals(first.sha256(),second.sha256()); assertEquals(1,first.report().assets());
    }
}
