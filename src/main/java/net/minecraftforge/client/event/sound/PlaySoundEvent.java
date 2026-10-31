package net.minecraftforge.client.event.sound;

import net.minecraft.client.sound.instance.SoundInstance;
import net.minecraft.client.sound.system.SoundEngine;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PlaySoundEvent extends Event {
    public final SoundEngine manager;
    public final String name;
    public final SoundInstance sound;
    public SoundInstance result;

    public PlaySoundEvent(SoundEngine manager, SoundInstance sound) {
        this.manager = manager;
        this.sound = sound;
        this.name = sound.getLocation().getPath();
        this.result = sound;
    }
}
