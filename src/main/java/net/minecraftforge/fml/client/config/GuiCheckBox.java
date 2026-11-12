package net.minecraftforge.fml.client.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;

public class GuiCheckBox extends ButtonWidget {
    private static final int BOX = 11;
    private boolean isChecked;

    public GuiCheckBox(int id, int xPos, int yPos, String displayString, boolean isChecked) {
        super(id, xPos, yPos, displayString);
        this.isChecked = isChecked;
        this.height = BOX;
        this.width = BOX + 2 + Minecraft.getInstance().textRenderer.getWidth(displayString);
    }

    @Override
    public void render(Minecraft mc, int mouseX, int mouseY) {
        if (!visible) return;
        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
        fill(x, y, x + BOX, y + BOX, 0xFFA0A0A0);
        fill(x + 1, y + 1, x + BOX - 1, y + BOX - 1, 0xFF000000);
        int color = !active ? 0xA0A0A0 : hovered ? 0xFFFFA0 : 0xE0E0E0;
        if (isChecked) drawCenteredString(mc.textRenderer, "x", x + BOX / 2 + 1, y + 1, color);
        drawString(mc.textRenderer, message, x + BOX + 2, y + 2, color);
    }

    @Override
    public boolean mouseClicked(Minecraft mc, int mouseX, int mouseY) {
        if (!active || !visible || mouseX < x || mouseY < y || mouseX >= x + width || mouseY >= y + height) return false;
        isChecked = !isChecked;
        return true;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
