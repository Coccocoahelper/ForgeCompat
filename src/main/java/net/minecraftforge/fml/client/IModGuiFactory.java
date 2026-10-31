package net.minecraftforge.fml.client;

import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.gui.screen.Screen;

public interface IModGuiFactory {
    void initialize(Minecraft minecraft);

    Class<? extends Screen> mainConfigGuiClass();

    Set<RuntimeOptionCategoryElement> runtimeGuiCategories();

    RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element);

    class RuntimeOptionCategoryElement {
        public final String parent;
        public final String child;

        public RuntimeOptionCategoryElement(String parent, String child) {
            this.parent = parent;
            this.child = child;
        }
    }

    interface RuntimeOptionGuiHandler {
        void addWidgets(List<GuiElement> widgets, int x, int y, int w, int h);

        void paint(int x, int y, int w, int h);

        void actionCallback(int actionId);

        void close();
    }
}
