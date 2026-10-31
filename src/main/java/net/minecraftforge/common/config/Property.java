package net.minecraftforge.common.config;

public class Property {
    private final char type;
    private String value;
    private String comment;
    private Double min;
    private Double max;
    private boolean changed;

    Property(char type, String value) {
        this.type = type;
        this.value = value;
    }

    public boolean getBoolean(boolean fallback) {
        return value.equals("true") || !value.equals("false") && fallback;
    }

    public int getInt(int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    public double getDouble(double fallback) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    public String getString() {
        return value;
    }

    public Property setValue(boolean value) {
        return set(Boolean.toString(value));
    }

    public Property setValue(int value) {
        return set(Integer.toString(value));
    }

    public Property setValue(double value) {
        return set(Double.toString(value));
    }

    public Property setValue(String value) {
        return set(value);
    }

    private Property set(String value) {
        changed |= !value.equals(this.value);
        this.value = value;
        return this;
    }

    public char type() {
        return type;
    }

    public String comment() {
        return comment;
    }

    public Double min() {
        return min;
    }

    public Double max() {
        return max;
    }

    void range(double min, double max) {
        this.min = min;
        this.max = max;
    }

    void comment(String comment) {
        this.comment = comment;
    }

    boolean changed() {
        return changed;
    }

    void markSaved() {
        changed = false;
    }
}
