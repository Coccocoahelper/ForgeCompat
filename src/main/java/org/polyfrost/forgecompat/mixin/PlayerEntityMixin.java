package org.polyfrost.forgecompat.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    private static final float DEFAULT_EYE_HEIGHT = 1.62F;

    public float eyeHeight = getDefaultEyeHeight();

    public float getDefaultEyeHeight() {
        return DEFAULT_EYE_HEIGHT;
    }

    @ModifyConstant(method = "getEyeHeight", constant = @Constant(floatValue = DEFAULT_EYE_HEIGHT))
    private float forgecompat$eyeHeight(float height) {
        return eyeHeight;
    }

    @Inject(method = "attack(Lnet/minecraft/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    private void forgecompat$attack(Entity target, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new AttackEntityEvent((PlayerEntity) (Object) this, target))) ci.cancel();
    }
}
