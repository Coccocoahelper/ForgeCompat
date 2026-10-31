package net.minecraftforge.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;

public class ForgeHooksClient {
    public static void drawScreen(Screen screen, int mouseX, int mouseY, float partialTicks) {
        if (!MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.DrawScreenEvent.Pre(screen, mouseX, mouseY, partialTicks))) {
            screen.render(mouseX, mouseY, partialTicks);
        }
        MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.DrawScreenEvent.Post(screen, mouseX, mouseY, partialTicks));
    }
}
