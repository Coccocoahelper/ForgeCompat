package org.polyfrost.forgecompat.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void forgecompat$worldLoad(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new WorldEvent.Load((ClientWorld) (Object) this));
    }
}
