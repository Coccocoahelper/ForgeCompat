package net.minecraftforge.fml.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.GuiIngameForge;
import org.polyfrost.forgecompat.ForgeCompat;
import org.polyfrost.forgecompat.Hooks;

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
        getClient().gui = new GuiIngameForge(getClient());
        Hooks.applyLookAndFeel();
        ForgeCompat.initMods();
    }
}
