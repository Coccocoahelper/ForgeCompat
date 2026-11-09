package net.minecraftforge.fml.common;

import java.io.File;

import org.polyfrost.forgecompat.ForgeCompat;

public interface ModContainer {
    String getModId();

    String getName();

    String getVersion();

    File getSource();

    default Object getMod() {
        return ForgeCompat.INSTANCES.get(getModId());
    }
}
