package net.minecraftforge.event.entity.player;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PlayerEvent extends Event {
    public final PlayerEntity entityPlayer;

    public PlayerEvent(PlayerEntity player) {
        this.entityPlayer = player;
    }
}
