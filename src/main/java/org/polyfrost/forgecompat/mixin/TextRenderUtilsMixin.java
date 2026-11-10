package org.polyfrost.forgecompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.TextRenderUtils;
import org.polyfrost.forgecompat.RavenHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TextRenderUtils.class)
public class TextRenderUtilsMixin {
    @ModifyExpressionValue(method = "wrapText", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Text;getContent()Ljava/lang/String;"))
    private static String forgecompat$ravenText(String text) {
        return RavenHooks.text(text);
    }
}
