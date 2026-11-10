package org.polyfrost.forgecompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.LanguageAdapter;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModOrigin;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.ModContainerImpl;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import org.apache.logging.log4j.LogManager;

public final class EarlyInit implements LanguageAdapter {
    static final List<String> SUPPORTED = Arrays.asList(
            "com.spiderfrog.gadgets.main.OAMTweaker",
            "de.hdskins.forge.v1855.HdSkinsPlugin",
            "mixins.hitdelayfix.json",
            "ragonohitdelay",
            "accountmanager",
            "godplace",
            "kegiultimatelatency",
            "keystrokes",
            "keystrokesmod",
            "MumbleLink",
            "miniskywars",
            "mixins.meowtils.json",
            "clickgui.mixins.json",
            "mixins.myau.json",
            "liquidbounce.forge.mixins.json",
            "lsfix.HwidShimPlugin",
            Essential.TWEAKER
    );
    private static final Pattern MODID = Pattern.compile("\"modid\"\\s*:\\s*\"([^\"]+)\"");

    public EarlyInit() throws IOException {
        Set<Path> hidden = new HashSet<>();
        for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
            if (mod.getOrigin().getKind() != ModOrigin.Kind.PATH) continue;
            for (Path path : mod.getOrigin().getPaths()) {
                if (!Files.isRegularFile(path)) continue;
                try (ZipFile zip = new ZipFile(path.toFile())) {
                    if (!supported(zip, manifest(zip))) continue;
                }
                for (Path source : ((ModContainerImpl) mod).getCodeSourcePaths()) hidden.add(source.toRealPath());
            }
        }
        if (!hidden.isEmpty()) hide(hidden);
        AccessTransformers.apply();
    }

    private static void hide(Set<Path> hidden) throws IOException {
        ClassLoader knot = FabricLauncherBase.getLauncher().getTargetClassLoader();
        try {
            Field field = knot.getClass().getDeclaredField("urlLoader");
            field.setAccessible(true);
            URLClassLoader old = (URLClassLoader) field.get(knot);
            List<URL> urls = new ArrayList<>();
            for (URL url : old.getURLs()) {
                Path path = Path.of(url.toURI());
                if (!Files.exists(path) || !hidden.contains(path.toRealPath())) urls.add(url);
            }
            Constructor<?> constructor = field.getType().getDeclaredConstructor(URL[].class);
            constructor.setAccessible(true);
            field.set(knot, constructor.newInstance((Object) urls.toArray(new URL[0])));
        } catch (ReflectiveOperationException | URISyntaxException e) {
            throw new RuntimeException("Failed to hide Fabric-loaded Forge mods " + hidden, e);
        }
        LogManager.getLogger("ForgeCompat").info("Removed Fabric-loaded Forge mod jars from the class path: {}", hidden);
    }

    static Attributes manifest(ZipFile zip) throws IOException {
        ZipEntry entry = zip.getEntry("META-INF/MANIFEST.MF");
        if (entry == null) return new Attributes();
        try (InputStream in = zip.getInputStream(entry)) {
            return new Manifest(in).getMainAttributes();
        }
    }

    static List<String> mixinConfigs(Attributes manifest) {
        String configs = manifest.getValue("MixinConfigs");
        return configs == null ? new ArrayList<>() : Arrays.asList(configs.trim().split("\\s*,\\s*"));
    }

    static boolean isForgeMod(ZipFile zip, Attributes manifest) {
        return manifest.getValue("TweakClass") != null || manifest.getValue("FMLCorePlugin") != null || manifest.getValue("MixinConfigs") != null || zip.getEntry("mcmod.info") != null;
    }

    static boolean supported(ZipFile zip, Attributes manifest) throws IOException {
        return isForgeMod(zip, manifest) && (SUPPORTED.contains(manifest.getValue("TweakClass")) || SUPPORTED.contains(manifest.getValue("FMLCorePlugin"))
                || mixinConfigs(manifest).stream().anyMatch(SUPPORTED::contains) || SUPPORTED.contains(modId(zip)));
    }

    static boolean hasMixinConfig(String name) {
        try (DirectoryStream<Path> jars = Files.newDirectoryStream(FabricLoaderImpl.INSTANCE.getModsDirectory().toPath(), "*.jar")) {
            for (Path jar : jars) {
                try (ZipFile zip = new ZipFile(jar.toFile())) {
                    if (mixinConfigs(manifest(zip)).contains(name)) return true;
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return false;
    }

    static String modId(ZipFile zip) throws IOException {
        ZipEntry entry = zip.getEntry("mcmod.info");
        if (entry == null) return null;
        try (InputStream in = zip.getInputStream(entry)) {
            Matcher matcher = MODID.matcher(new String(in.readAllBytes(), StandardCharsets.UTF_8));
            return matcher.find() ? matcher.group(1) : null;
        }
    }

    @Override
    public <T> T create(ModContainer mod, String value, Class<T> type) {
        throw new UnsupportedOperationException("forgecompat_early is not a real language adapter");
    }
}
