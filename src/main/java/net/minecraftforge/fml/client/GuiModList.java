package net.minecraftforge.fml.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraftforge.fml.common.ModContainer;
import org.polyfrost.forgecompat.ForgeCompat;

public class GuiModList extends Screen {
    private final Screen parent;

    public GuiModList(Screen parent) {
        this.parent = parent;
    }

    @Override
    public void init() {
        buttons.clear();
        buttons.add(new ButtonWidget(0, width / 2 - 100, height - 26, "Done"));
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        renderBackground();
        drawCenteredString(textRenderer, "Forge Mods", width / 2, 12, 0xFFFFFF);
        int y = 32;
        for (ModContainer mod : ForgeCompat.MODS.values()) {
            drawCenteredString(textRenderer, mod.getName() + " " + mod.getVersion(), width / 2, y, 0xAAAAAA);
            y += 12;
        }
        super.render(mouseX, mouseY, tickDelta);
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        minecraft.openScreen(parent);
    }
}
