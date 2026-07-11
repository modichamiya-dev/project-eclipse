package dev.modichamiya.eclipse.registry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

class ContentLoaderTest {
    @TempDir Path root;
    @Test void loadsAndResolvesDefinitions() throws Exception {
        Files.writeString(root.resolve("item.yml"), "key: eclipse:sword\ntype: items\ntags: [weapon]\n");
        Files.writeString(root.resolve("loot.yml"), "key: eclipse:starter_loot\ntype: loot\nreferences: [eclipse:sword]\n");
        var loaded = new ContentLoader().load(root); assertEquals(2, loaded.size()); assertEquals(1, loaded.get("items").size());
    }
    @Test void rejectsDanglingReferences() throws Exception {
        Files.writeString(root.resolve("broken.yml"), "key: eclipse:broken\ntype: loot\nreferences: [eclipse:missing]\n");
        ContentLoadException error = assertThrows(ContentLoadException.class, () -> new ContentLoader().load(root)); assertTrue(error.getMessage().contains("eclipse:missing"));
    }
}
