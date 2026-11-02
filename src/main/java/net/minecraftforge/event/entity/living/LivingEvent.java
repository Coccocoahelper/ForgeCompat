package net.minecraftforge.event.entity.living;

import net.minecraft.entity.living.LivingEntity;
import net.minecraftforge.event.entity.EntityEvent;

public class LivingEvent extends EntityEvent {
    public final LivingEntity entityLiving;

    public LivingEvent(LivingEntity entity) {
        super(entity);
        this.entityLiving = entity;
    }
}
