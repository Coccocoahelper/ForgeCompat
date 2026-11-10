package org.polyfrost.forgecompat.mixin.overwrite;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.layer.ItemInHandLayer;
import net.minecraft.client.render.model.entity.HumanoidModel;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.polyfrost.forgecompat.OverwriteHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemInHandLayer.class)
public class ItemInHandLayerMixin {
    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/LivingEntity;getDisplayItemInHand()Lnet/minecraft/item/ItemStack;"))
    private ItemStack forgecompat$silentHotbarItem(ItemStack original, @Local(argsOnly = true) LivingEntity entity) {
        return OverwriteHooks.heldItem(entity, original);
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/entity/HumanoidModel;translateRightArm(F)V"))
    private void forgecompat$blockingArm(HumanoidModel model, float scale, Operation<Void> original, @Local(argsOnly = true) LivingEntity entity, @Local ItemStack stack) {
        if (!(entity instanceof PlayerEntity player) || !player.isSwordBlocking() && !OverwriteHooks.forceBlocking(player, stack)) {
            original.call(model, scale);
            return;
        }
        original.call(model, 0.0325F);
        if (entity.isSneaking()) GlStateManager.translatef(-0.58F, 0.3F, -0.2F);
        else GlStateManager.translatef(-0.48F, 0.2F, -0.2F);
        GlStateManager.rotatef(-24390.0F, 137290.0F, -2009900.0F, -2054900.0F);
    }
}
