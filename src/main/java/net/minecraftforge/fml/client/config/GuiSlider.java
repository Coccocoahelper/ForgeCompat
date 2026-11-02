package net.minecraftforge.fml.client.config;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiSlider extends GuiButtonExt {
    public double sliderValue = 1.0;
    public String dispString = "";
    public boolean dragging = false;
    public boolean showDecimal = true;
    public double minValue = 0.0;
    public double maxValue = 5.0;
    public int precision = 1;
    public ISlider parent = null;
    public String suffix = "";
    public boolean drawString = true;

    public GuiSlider(int id, int x, int y, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr) {
        this(id, x, y, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr, null);
    }

    public GuiSlider(int id, int x, int y, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr, ISlider par) {
        super(id, x, y, width, height, prefix);
        minValue = minVal;
        maxValue = maxVal;
        sliderValue = (currentVal - minValue) / (maxValue - minValue);
        dispString = prefix;
        parent = par;
        suffix = suf;
        showDecimal = showDec;
        String val;
        if (showDecimal) {
            val = Double.toString(getValue());
            precision = Math.min(val.substring(val.indexOf('.') + 1).length(), 4);
        } else {
            val = Integer.toString(getValueInt());
            precision = 0;
        }
        message = dispString + val + suffix;
        drawString = drawStr;
        if (!drawString) message = "";
    }

    public GuiSlider(int id, int x, int y, String displayStr, double minVal, double maxVal, double currentVal, ISlider par) {
        this(id, x, y, 150, 20, displayStr, "", minVal, maxVal, currentVal, true, true, par);
    }

    @Override
    protected int getYImage(boolean hovered) {
        return 0;
    }

    @Override
    protected void renderBackground(Minecraft mc, int mouseX, int mouseY) {
        if (!visible) return;
        if (dragging) {
            sliderValue = (mouseX - (x + 4)) / (float) (width - 8);
            updateSlider();
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(x + (int) (sliderValue * (width - 8)), y, 0, 66, 4, 20);
        drawTexture(x + (int) (sliderValue * (width - 8)) + 4, y, 196, 66, 4, 20);
    }

    @Override
    public boolean mouseClicked(Minecraft mc, int mouseX, int mouseY) {
        if (!super.mouseClicked(mc, mouseX, mouseY)) return false;
        sliderValue = (mouseX - (x + 4)) / (float) (width - 8);
        updateSlider();
        dragging = true;
        return true;
    }

    public void updateSlider() {
        if (sliderValue < 0.0) sliderValue = 0.0;
        if (sliderValue > 1.0) sliderValue = 1.0;
        String val;
        if (showDecimal) {
            val = Double.toString(getValue());
            if (val.substring(val.indexOf('.') + 1).length() > precision) {
                val = val.substring(0, val.indexOf('.') + precision + 1);
                if (val.endsWith(".")) val = val.substring(0, val.indexOf('.') + precision);
            } else {
                while (val.substring(val.indexOf('.') + 1).length() < precision) val = val + "0";
            }
        } else {
            val = Integer.toString(getValueInt());
        }
        if (drawString) message = dispString + val + suffix;
        if (parent != null) parent.onChangeSliderValue(this);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        dragging = false;
    }

    public int getValueInt() {
        return (int) Math.round(getValue());
    }

    public double getValue() {
        return sliderValue * (maxValue - minValue) + minValue;
    }

    public void setValue(double value) {
        sliderValue = (value - minValue) / (maxValue - minValue);
    }

    public interface ISlider {
        void onChangeSliderValue(GuiSlider slider);
    }
}
