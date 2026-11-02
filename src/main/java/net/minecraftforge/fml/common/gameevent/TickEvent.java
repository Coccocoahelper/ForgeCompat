package net.minecraftforge.fml.common.gameevent;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;

public class TickEvent extends Event {
    public final Phase phase;

    public TickEvent(Phase phase) {
        this.phase = phase;
    }

    public enum Phase {
        START, END
    }

    public static class ClientTickEvent extends TickEvent {
        public ClientTickEvent(Phase phase) {
            super(phase);
        }
    }

    public static class RenderTickEvent extends TickEvent {
        public final float renderTickTime;

        public RenderTickEvent(Phase phase, float renderTickTime) {
            super(phase);
            this.renderTickTime = renderTickTime;
        }
    }

    public static class PlayerTickEvent extends TickEvent {
        public final Side side;
        public final PlayerEntity player;

        public PlayerTickEvent(Phase phase, PlayerEntity player) {
            super(phase);
            this.side = player.world.isClient ? Side.CLIENT : Side.SERVER;
            this.player = player;
        }
    }
}
