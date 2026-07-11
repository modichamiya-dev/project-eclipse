package dev.modichamiya.eclipse.assets;

import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.*;
import java.util.zip.*;

public final class ResourcePackBuilder {
    public record BuildResult(Path zip, String sha256, AssetValidator.Report report) { }
    public BuildResult build(Path sourceRoot, Path manifest, Path output, int packFormat) throws IOException {
        List<AssetDefinition> assets=new AssetManifestLoader().load(sourceRoot,manifest); AssetValidator.Report report=new AssetValidator().validate(assets);
        Files.createDirectories(output.getParent()); Path temp=Files.createTempFile(output.getParent(),"eclipse-pack-",".zip");
        try (ZipOutputStream zip=new ZipOutputStream(Files.newOutputStream(temp))) {
            write(zip,"pack.mcmeta",("{\"pack\":{\"pack_format\":"+packFormat+",\"description\":\"Project Eclipse resources\"}}").getBytes(java.nio.charset.StandardCharsets.UTF_8));
            List<Path> files; try (var stream=Files.walk(sourceRoot)) { files=stream.filter(Files::isRegularFile).filter(path -> !path.equals(manifest)).sorted(Comparator.comparing(path -> sourceRoot.relativize(path).toString())).toList(); }
            for (Path file : files) write(zip,sourceRoot.relativize(file).toString().replace('\\','/'),Files.readAllBytes(file));
        }
        Files.move(temp,output,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE); return new BuildResult(output,hash(output),report);
    }
    private void write(ZipOutputStream zip,String name,byte[] bytes) throws IOException { ZipEntry entry=new ZipEntry(name); entry.setTime(0); zip.putNextEntry(entry); zip.write(bytes); zip.closeEntry(); }
    private String hash(Path path) throws IOException { try { MessageDigest digest=MessageDigest.getInstance("SHA-256"); try (InputStream in=Files.newInputStream(path)) { byte[] buffer=new byte[8192]; for (int read;(read=in.read(buffer))>=0;) digest.update(buffer,0,read); } return HexFormat.of().formatHex(digest.digest()); } catch (NoSuchAlgorithmException impossible) { throw new IllegalStateException(impossible); } }
}
