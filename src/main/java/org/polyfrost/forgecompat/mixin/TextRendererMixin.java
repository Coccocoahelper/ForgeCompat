package org.polyfrost.forgecompat.mixin;

import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.client.render.TextRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TextRenderer.class)
public abstract class TextRendererMixin {
    public void enableAlpha() {
        GlStateManager.enableAlphaTest();
    }
}
