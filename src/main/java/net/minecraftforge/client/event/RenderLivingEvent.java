package net.minecraftforge.client.event;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.living.LivingEntity;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderLivingEvent<T extends LivingEntity> extends Event {
    public final LivingEntity entity;
    public final LivingEntityRenderer<T> renderer;
    public final double x;
    public final double y;
    public final double z;

    public RenderLivingEvent(LivingEntity entity, LivingEntityRenderer<T> renderer, double x, double y, double z) {
        this.entity = entity;
        this.renderer = renderer;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public abstract static class Specials<T extends LivingEntity> extends RenderLivingEvent<T> {
        public Specials(LivingEntity entity, LivingEntityRenderer<T> renderer, double x, double y, double z) {
            super(entity, renderer, x, y, z);
        }

        @Cancelable
        public static class Pre<T extends LivingEntity> extends Specials<T> {
            public Pre(LivingEntity entity, LivingEntityRenderer<T> renderer, double x, double y, double z) {
                super(entity, renderer, x, y, z);
            }
        }

        public static class Post<T extends LivingEntity> extends Specials<T> {
            public Post(LivingEntity entity, LivingEntityRenderer<T> renderer, double x, double y, double z) {
                super(entity, renderer, x, y, z);
            }
        }
    }
}
