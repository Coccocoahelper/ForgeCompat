package org.polyfrost.forgecompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GameGui;
import net.minecraft.client.gui.spectator.SpectatorGui;
import net.minecraft.client.render.Window;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameGui.class)
public class GameGuiMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void forgecompat$overlayPre(float tickDelta, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.Pre(tickDelta, new Window(minecraft), ElementType.ALL))) ci.cancel();
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void forgecompat$overlayPost(float tickDelta, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.Post(tickDelta, new Window(minecraft), ElementType.ALL));
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GameGui;renderHotbar(Lnet/minecraft/client/render/Window;F)V"))
    private void forgecompat$hotbar(GameGui gui, Window window, float tickDelta, Operation<Void> original) {
        hotbar(window, tickDelta, () -> original.call(gui, window, tickDelta));
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/spectator/SpectatorGui;renderHotbar(Lnet/minecraft/client/render/Window;F)V"))
    private void forgecompat$spectatorHotbar(SpectatorGui gui, Window window, float tickDelta, Operation<Void> original) {
        hotbar(window, tickDelta, () -> original.call(gui, window, tickDelta));
    }

    @Unique
    private static void hotbar(Window window, float tickDelta, Runnable render) {
        if (MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.Pre(tickDelta, window, ElementType.HOTBAR))) return;
        render.run();
        MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.Post(tickDelta, window, ElementType.HOTBAR));
    }
}
