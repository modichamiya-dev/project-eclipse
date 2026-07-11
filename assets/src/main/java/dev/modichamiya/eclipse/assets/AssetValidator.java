package dev.modichamiya.eclipse.assets;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public final class AssetValidator {
    public record Report(int assets, long bytes, List<String> warnings) { public Report { warnings=List.copyOf(warnings); } }
    public Report validate(List<AssetDefinition> assets) throws IOException {
        List<String> errors=new ArrayList<>(), warnings=new ArrayList<>(); long bytes=0;
        for (AssetDefinition asset : assets) {
            if (!Files.isRegularFile(asset.source())) { errors.add(asset.key()+": missing file "+asset.source()); continue; }
            long size=Files.size(asset.source()); bytes+=size; if (size>8L*1024*1024) warnings.add(asset.key()+": file exceeds 8 MiB");
            String name=asset.source().getFileName().toString(); if (!name.equals(name.toLowerCase(Locale.ROOT))) errors.add(asset.key()+": Minecraft asset paths must be lowercase");
            if ((asset.type()==AssetType.SOUND || asset.type()==AssetType.MUSIC) && !name.endsWith(".ogg")) errors.add(asset.key()+": sounds must be OGG");
            if (asset.type()==AssetType.TEXTURE && !name.endsWith(".png")) errors.add(asset.key()+": textures must be PNG");
        }
        if (!errors.isEmpty()) throw new IOException(String.join(System.lineSeparator(),errors)); return new Report(assets.size(),bytes,warnings);
    }
}
