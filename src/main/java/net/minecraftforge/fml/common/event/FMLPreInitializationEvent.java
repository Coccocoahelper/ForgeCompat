package net.minecraftforge.fml.common.event;

import java.io.File;

import net.fabricmc.loader.api.FabricLoader;

public class FMLPreInitializationEvent {
    private final File configDir = FabricLoader.getInstance().getConfigDir().toFile();
    private final String modid;

    public FMLPreInitializationEvent(String modid) {
        this.modid = modid;
    }

    public File getModConfigurationDirectory() {
        return configDir;
    }

    public File getSuggestedConfigurationFile() {
        return new File(configDir, modid + ".cfg");
    }
}
