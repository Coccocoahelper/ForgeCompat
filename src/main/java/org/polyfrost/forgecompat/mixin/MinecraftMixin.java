package org.polyfrost.forgecompat.mixin;

import net.minecraft.client.ClientPlayerInteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.TickTimer;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    private TickTimer timer;

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void forgecompat$tickStart(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new TickEvent.ClientTickEvent(TickEvent.Phase.START));
    }

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void forgecompat$tickEnd(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new TickEvent.ClientTickEvent(TickEvent.Phase.END));
    }

    @Inject(method = "runGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;render(FJ)V"))
    private void forgecompat$renderTickStart(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new TickEvent.RenderTickEvent(TickEvent.Phase.START, timer.partialTick));
    }

    @Inject(method = "runGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;render(FJ)V", shift = At.Shift.AFTER))
    private void forgecompat$renderTickEnd(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new TickEvent.RenderTickEvent(TickEvent.Phase.END, timer.partialTick));
    }

    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;next()Z", remap = false))
    private boolean forgecompat$mouseEvent() {
        while (Mouse.next()) {
            if (!MinecraftForge.EVENT_BUS.post(new MouseEvent())) return true;
        }
        return false;
    }

    @Redirect(method = "doUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/ClientPlayerInteractionManager;useBlock(Lnet/minecraft/client/entity/living/player/LocalClientPlayerEntity;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Lnet/minecraft/util/math/Vec3d;)Z"))
    private boolean forgecompat$rightClickBlock(ClientPlayerInteractionManager manager, LocalClientPlayerEntity player, ClientWorld world, ItemStack stack, BlockPos pos, Direction face, Vec3d hit) {
        return !MinecraftForge.EVENT_BUS.post(new PlayerInteractEvent(player, PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK, pos, face, world)) && manager.useBlock(player, world, stack, pos, face, hit);
    }
}
