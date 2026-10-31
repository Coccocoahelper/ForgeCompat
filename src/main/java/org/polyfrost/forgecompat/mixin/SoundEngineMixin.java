package org.polyfrost.forgecompat.mixin;

import net.minecraft.client.sound.instance.SoundInstance;
import net.minecraft.client.sound.system.SoundEngine;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundEngine.class)
public class SoundEngineMixin {
    @Inject(method = "play", at = @At("HEAD"), cancellable = true)
    private void forgecompat$playSound(SoundInstance sound, CallbackInfo ci) {
        PlaySoundEvent event = new PlaySoundEvent((SoundEngine) (Object) this, sound);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.result == null) ci.cancel();
    }
}
