package org.polyfrost.forgecompat.mixin.overwrite;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.resource.pack.ResourcePacks;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ResourcePacks.class)
public class ResourcePacksMixin {
    @WrapMethod(method = "clearOldDownloads")
    private void forgecompat$ignoreErrors(Operation<Void> original) {
        try {
            original.call();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
