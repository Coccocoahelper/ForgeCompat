package net.minecraftforge.client.event;

import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class GuiOpenEvent extends Event {
    public Screen gui;

    public GuiOpenEvent(Screen gui) {
        this.gui = gui;
    }
}
