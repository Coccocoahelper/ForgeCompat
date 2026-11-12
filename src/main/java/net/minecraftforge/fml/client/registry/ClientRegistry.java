package net.minecraftforge.fml.client.registry;

import java.util.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;

public class ClientRegistry {
    public static void registerKeyBinding(KeyBinding key) {
        GameOptions options = Minecraft.getInstance().options;
        options.keyBindings = Arrays.copyOf(options.keyBindings, options.keyBindings.length + 1);
        options.keyBindings[options.keyBindings.length - 1] = key;
    }
}
