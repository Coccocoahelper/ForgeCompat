package net.minecraftforge.event.entity.living;

import net.minecraft.entity.living.LivingEntity;

public class LivingSetAttackTargetEvent extends LivingEvent {
    public final LivingEntity target;

    public LivingSetAttackTargetEvent(LivingEntity entity, LivingEntity target) {
        super(entity);
        this.target = target;
    }
}
