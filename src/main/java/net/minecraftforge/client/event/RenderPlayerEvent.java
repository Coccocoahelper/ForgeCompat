package net.minecraftforge.client.event;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class RenderPlayerEvent extends PlayerEvent {
    public final double x;
    public final double y;
    public final double z;

    public RenderPlayerEvent(PlayerEntity player, double x, double y, double z) {
        super(player);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public abstract static class Specials extends RenderPlayerEvent {
        public Specials(PlayerEntity player, double x, double y, double z) {
            super(player, x, y, z);
        }

        public static class Post extends Specials {
            public Post(PlayerEntity player, double x, double y, double z) {
                super(player, x, y, z);
            }
        }
    }
}
