package net.minecraftforge.fml.client.config;

import net.minecraft.client.gui.widget.ButtonWidget;

public class GuiButtonExt extends ButtonWidget {
    public GuiButtonExt(int id, int x, int y, String text) {
        super(id, x, y, text);
    }

    public GuiButtonExt(int id, int x, int y, int width, int height, String text) {
        super(id, x, y, width, height, text);
    }
}
