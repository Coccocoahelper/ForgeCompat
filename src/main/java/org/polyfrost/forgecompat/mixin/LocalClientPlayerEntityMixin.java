package org.polyfrost.forgecompat.mixin;

import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import org.objectweb.asm.Opcodes;
import org.polyfrost.forgecompat.RavenHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalClientPlayerEntity.class)
public class LocalClientPlayerEntityMixin {
    @ModifyConstant(method = "mobTick", constant = @Constant(floatValue = 0.2F))
    private float forgecompat$ravenNoSlow(float slowdown) {
        return RavenHooks.active() ? 1.0F : slowdown;
    }

    @Inject(method = "mobTick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/living/player/Input;movementForward:F", opcode = Opcodes.PUTFIELD, ordinal = 0, shift = At.Shift.AFTER))
    private void forgecompat$ravenSlowdown(CallbackInfo ci) {
        if (RavenHooks.active()) RavenHooks.slowdown();
    }
}
