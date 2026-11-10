package org.polyfrost.forgecompat.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.polyfrost.forgecompat.RavenHooks;
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

    public String getDisplayNameString() {
        return ((PlayerEntity) (Object) this).getName();
    }

    public float getDefaultEyeHeight() {
        return DEFAULT_EYE_HEIGHT;
    }

    @ModifyConstant(method = "getEyeHeight", constant = @Constant(floatValue = DEFAULT_EYE_HEIGHT))
    private float forgecompat$eyeHeight(float height) {
        return eyeHeight;
    }

    @Inject(method = "attack(Lnet/minecraft/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    private void forgecompat$attack(Entity target, CallbackInfo ci) {
        if (!ForgeHooks.onPlayerAttackTarget((PlayerEntity) (Object) this, target)) ci.cancel();
    }

    @ModifyConstant(method = "attack(Lnet/minecraft/entity/Entity;)V", constant = @Constant(doubleValue = 0.6D))
    private double forgecompat$ravenKeepSprint(double slowdown) {
        return RavenHooks.active() ? 1.0D : slowdown;
    }

    @Inject(method = "attack(Lnet/minecraft/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;setSprinting(Z)V"))
    private void forgecompat$ravenKnockback(Entity target, CallbackInfo ci) {
        if (RavenHooks.active()) RavenHooks.knockback(target);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void forgecompat$tickStart(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new TickEvent.PlayerTickEvent(TickEvent.Phase.START, (PlayerEntity) (Object) this));
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void forgecompat$tickEnd(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new TickEvent.PlayerTickEvent(TickEvent.Phase.END, (PlayerEntity) (Object) this));
    }
}
