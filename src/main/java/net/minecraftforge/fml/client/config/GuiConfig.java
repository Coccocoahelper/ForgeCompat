package net.minecraftforge.fml.client.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraftforge.common.config.Property;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiConfig extends Screen {
    private static final int ROW_HEIGHT = 24;
    private static final int TOP = 32;
    private static final int DONE = -1;

    public final Screen parentScreen;
    public final String title;
    private final List<IConfigElement> elements;
    private final List<Row> rows = new ArrayList<>();
    private int scroll;

    public GuiConfig(Screen parentScreen, List<IConfigElement> elements, String modID, boolean allRequireWorldRestart, boolean allRequireMcRestart, String title) {
        this.parentScreen = parentScreen;
        this.elements = elements;
        this.title = title;
        for (IConfigElement element : elements) {
            rows.add(new Row(element.getName(), null));
            element.getProperties().forEach((key, property) -> rows.add(new Row(key, property)));
        }
    }

    private int bottom() {
        return height - 32;
    }

    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);
        buttons.clear();
        buttons.add(new ButtonWidget(DONE, width / 2 - 100, height - 26, "Done"));
        for (int i = 0; i < rows.size(); i++) {
            Row row = rows.get(i);
            if (row.property == null) continue;
            if (row.property.type() == 'B') {
                row.toggle = new ButtonWidget(i, width / 2 + 20, 0, 130, 20, row.value);
                buttons.add(row.toggle);
            } else {
                row.field = new TextFieldWidget(i, textRenderer, width / 2 + 21, 0, 128, 18);
                row.field.setMaxLength(256);
                row.field.setText(row.value);
            }
        }
        layout();
    }

    private void layout() {
        scroll = Math.max(0, Math.min(scroll, rows.size() * ROW_HEIGHT - (bottom() - TOP)));
        for (int i = 0; i < rows.size(); i++) {
            Row row = rows.get(i);
            row.y = TOP + i * ROW_HEIGHT - scroll;
            boolean visible = row.y >= TOP && row.y + ROW_HEIGHT <= bottom();
            if (row.toggle != null) {
                row.toggle.y = row.y;
                row.toggle.visible = visible;
            } else if (row.field != null) {
                row.field.y = row.y + 1;
                row.field.setVisible(visible);
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        renderBackground();
        drawCenteredString(textRenderer, title, width / 2, 12, 0xFFFFFF);
        for (Row row : rows) {
            if (row.y < TOP || row.y + ROW_HEIGHT > bottom()) continue;
            if (row.property == null) {
                drawCenteredString(textRenderer, "§l" + row.name, width / 2, row.y + 8, 0xFFFF55);
            } else {
                drawString(textRenderer, row.name, width / 2 - 150, row.y + 6, 0xFFFFFF);
                if (row.field != null) row.field.render();
            }
        }
        super.render(mouseX, mouseY, tickDelta);
        for (Row row : rows) {
            boolean hovered = mouseX >= width / 2 - 150 && mouseX < width / 2 + 20 && mouseY >= row.y && mouseY < row.y + ROW_HEIGHT;
            if (hovered && mouseY >= TOP && mouseY < bottom() && row.property != null && row.property.comment() != null) {
                renderTooltip(textRenderer.split(row.property.comment(), 220), mouseX, mouseY);
            }
        }
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        if (button.id == DONE) {
            save();
            minecraft.openScreen(parentScreen);
        } else {
            Row row = rows.get(button.id);
            row.value = Boolean.toString(!Boolean.parseBoolean(row.value));
            button.message = row.value;
        }
    }

    private void save() {
        for (Row row : rows) {
            if (row.property != null) row.apply();
        }
        for (IConfigElement element : elements) element.save();
    }

    @Override
    protected void keyPressed(char chr, int key) {
        if (key == Keyboard.KEY_ESCAPE) {
            minecraft.openScreen(parentScreen);
            return;
        }
        for (Row row : rows) {
            if (row.field != null && row.field.keyPressed(chr, key)) row.value = row.field.getText();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        for (Row row : rows) {
            if (row.field != null && row.field.isVisible()) row.field.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void handleMouse() {
        super.handleMouse();
        int wheel = Mouse.getEventDWheel();
        if (wheel != 0) {
            scroll -= Integer.signum(wheel) * ROW_HEIGHT;
            layout();
        }
    }

    @Override
    public void tick() {
        for (Row row : rows) {
            if (row.field != null) row.field.tick();
        }
    }

    @Override
    public void removed() {
        super.removed();
        Keyboard.enableRepeatEvents(false);
    }

    private static final class Row {
        final String name;
        final Property property;
        String value;
        ButtonWidget toggle;
        TextFieldWidget field;
        int y;

        Row(String name, Property property) {
            this.name = name;
            this.property = property;
            this.value = property == null ? null : property.getString();
        }

        void apply() {
            if (property.min() == null) {
                property.setValue(value);
                return;
            }
            try {
                double clamped = Math.max(property.min(), Math.min(property.max(), Double.parseDouble(value)));
                if (property.type() == 'I') property.setValue((int) clamped);
                else property.setValue(clamped);
            } catch (NumberFormatException ignored) {
                // keeps the stored value
            }
        }
    }
}
