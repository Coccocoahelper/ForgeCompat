package net.minecraftforge.fml.client;

import net.minecraft.client.Minecraft;
import org.polyfrost.forgecompat.ForgeCompat;

public class FMLClientHandler {
    private static final FMLClientHandler INSTANCE = new FMLClientHandler();

    public static FMLClientHandler instance() {
        return INSTANCE;
    }

    public Minecraft getClient() {
        return Minecraft.getInstance();
    }

    public void haltGame(String message, Throwable cause) {
        throw new RuntimeException(message, cause);
    }

    public void onInitializationComplete() {
        ForgeCompat.initMods();
    }
}
