package net.minecraftforge.fml.common.event;

import java.io.File;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ModMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.polyfrost.forgecompat.ForgeCompat;

public class FMLPreInitializationEvent {
    private final File configDir = FabricLoader.getInstance().getConfigDir().toFile();
    private final String modid;

    public FMLPreInitializationEvent(String modid) {
        this.modid = modid;
    }

    public File getModConfigurationDirectory() {
        return configDir;
    }

    public Logger getModLog() {
        return LogManager.getLogger(modid);
    }

    public ModMetadata getModMetadata() {
        ModContainer mod = ForgeCompat.MODS.get(modid);
        return new ModMetadata(modid, mod == null ? modid : mod.getName(), mod == null ? "" : mod.getVersion());
    }

    public File getSuggestedConfigurationFile() {
        return new File(configDir, modid + ".cfg");
    }
}
