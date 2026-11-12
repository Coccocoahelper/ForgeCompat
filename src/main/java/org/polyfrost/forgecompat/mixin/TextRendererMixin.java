package org.polyfrost.forgecompat.mixin;

import net.minecraft.client.render.TextRenderer;
import org.polyfrost.forgecompat.RavenHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TextRenderer.class)
public abstract class TextRendererMixin {
    @ModifyVariable(method = {"drawLayer(Ljava/lang/String;Z)V", "getWidth(Ljava/lang/String;)I"}, at = @At("HEAD"), argsOnly = true)
    private String forgecompat$ravenText(String text) {
        return RavenHooks.text(text);
    }
}
