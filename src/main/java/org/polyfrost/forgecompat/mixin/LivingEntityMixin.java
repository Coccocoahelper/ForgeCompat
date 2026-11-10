package org.polyfrost.forgecompat.mixin;

import net.minecraft.entity.living.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void forgecompat$livingUpdate(CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new LivingEvent.LivingUpdateEvent((LivingEntity) (Object) this))) ci.cancel();
    }
}
