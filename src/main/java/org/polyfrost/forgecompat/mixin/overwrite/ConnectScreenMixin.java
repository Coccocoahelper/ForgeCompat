package org.polyfrost.forgecompat.mixin.overwrite;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.gui.screen.menu.multiplayer.ConnectScreen;
import net.minecraft.client.render.TextRenderer;
import org.polyfrost.forgecompat.OverwriteHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ConnectScreen.class)
public class ConnectScreenMixin {
    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/menu/multiplayer/ConnectScreen;drawCenteredString(Lnet/minecraft/client/render/TextRenderer;Ljava/lang/String;III)V"))
    private boolean forgecompat$connectingStatus(ConnectScreen screen, TextRenderer textRenderer, String text, int x, int y, int color) {
        OverwriteHooks.drawConnecting(screen);
        return false;
    }
}
