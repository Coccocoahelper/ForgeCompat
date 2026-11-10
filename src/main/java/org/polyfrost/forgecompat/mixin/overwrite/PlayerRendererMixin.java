package org.polyfrost.forgecompat.mixin.overwrite;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.entity.living.player.ClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import org.polyfrost.forgecompat.OverwriteHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {
    @ModifyExpressionValue(method = "setModelStatus", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerInventory;getSelectedItem()Lnet/minecraft/item/ItemStack;"))
    private ItemStack forgecompat$silentHotbarItem(ItemStack original, @Local(argsOnly = true) ClientPlayerEntity entity) {
        return OverwriteHooks.heldItem(entity, original);
    }

    @ModifyExpressionValue(method = "setModelStatus", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/living/player/ClientPlayerEntity;getItemUseTimer()I"))
    private int forgecompat$forceBlockingPose(int original, @Local(argsOnly = true) ClientPlayerEntity entity, @Local ItemStack stack) {
        return original <= 0 && OverwriteHooks.forceBlocking(entity, stack) ? 1 : original;
    }

    @ModifyExpressionValue(method = "setModelStatus", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getUseAction()Lnet/minecraft/item/UseAction;"))
    private UseAction forgecompat$forceBlockingAction(UseAction original, @Local(argsOnly = true) ClientPlayerEntity entity, @Local ItemStack stack) {
        return OverwriteHooks.forceBlocking(entity, stack) ? UseAction.BLOCK : original;
    }
}
