package net.minecraftforge.fml.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.relauncher.Side;

public class FMLCommonHandler {
    private static final FMLCommonHandler INSTANCE = new FMLCommonHandler();

    public static FMLCommonHandler instance() {
        return INSTANCE;
    }

    public EventBus bus() {
        return MinecraftForge.EVENT_BUS;
    }

    public Side getSide() {
        return Side.CLIENT;
    }

    public Side getEffectiveSide() {
        return Side.CLIENT;
    }

    public void exitJava(int exitCode, boolean hardExit) {
        if (hardExit) Runtime.getRuntime().halt(exitCode);
        else System.exit(exitCode);
    }
}
