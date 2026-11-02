package net.minecraftforge.fml.common.network;

import net.minecraft.network.Connection;
import net.minecraftforge.fml.common.eventhandler.Event;

public class FMLNetworkEvent extends Event {
    public final Connection manager;

    public FMLNetworkEvent(Connection manager) {
        this.manager = manager;
    }

    public static class ClientDisconnectionFromServerEvent extends FMLNetworkEvent {
        public ClientDisconnectionFromServerEvent(Connection manager) {
            super(manager);
        }
    }
}
