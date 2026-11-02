package net.minecraftforge.client.event;

import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

public class RenderPlayerEvent extends PlayerEvent {
    public final PlayerRenderer renderer;
    public final float partialRenderTick;
    public final double x;
    public final double y;
    public final double z;

    public RenderPlayerEvent(PlayerEntity player, PlayerRenderer renderer, float partialRenderTick, double x, double y, double z) {
        super(player);
        this.renderer = renderer;
        this.partialRenderTick = partialRenderTick;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Cancelable
    public static class Pre extends RenderPlayerEvent {
        public Pre(PlayerEntity player, PlayerRenderer renderer, float partialRenderTick, double x, double y, double z) {
            super(player, renderer, partialRenderTick, x, y, z);
        }
    }

    public static class Post extends RenderPlayerEvent {
        public Post(PlayerEntity player, PlayerRenderer renderer, float partialRenderTick, double x, double y, double z) {
            super(player, renderer, partialRenderTick, x, y, z);
        }
    }

    public abstract static class Specials extends RenderPlayerEvent {
        public Specials(PlayerEntity player, PlayerRenderer renderer, float partialTicks) {
            super(player, renderer, partialTicks, 0, 0, 0);
        }

        @Cancelable
        public static class Pre extends Specials {
            public Pre(PlayerEntity player, PlayerRenderer renderer, float partialTicks) {
                super(player, renderer, partialTicks);
            }
        }

        public static class Post extends Specials {
            public Post(PlayerEntity player, PlayerRenderer renderer, float partialTicks) {
                super(player, renderer, partialTicks);
            }
        }
    }
}
