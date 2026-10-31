package net.minecraftforge.common.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;

public class Configuration {
    private static final Pattern ENTRY = Pattern.compile("([BIDS]):\"?([^\"=]+)\"?=(.*)");
    private final File file;
    private final Map<String, Map<String, Property>> categories = new LinkedHashMap<>();
    private final Map<String, String> categoryComments = new LinkedHashMap<>();
    private boolean changed;

    public Configuration(File file) {
        this.file = file;
        load();
    }

    public void load() {
        categories.clear();
        changed = false;
        if (!file.exists()) return;
        try {
            read(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            LogManager.getLogger("ForgeCompat").error("Failed to read {}", file, e);
        }
    }

    private void read(List<String> lines) {
        Map<String, Property> category = null;
        for (String raw : lines) {
            String line = raw.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;
            if (line.endsWith("{")) {
                category = category(line.substring(0, line.length() - 1).trim().replace("\"", ""));
            } else if (line.equals("}")) {
                category = null;
            } else if (category != null) {
                Matcher matcher = ENTRY.matcher(line);
                if (matcher.matches()) category.put(matcher.group(2), new Property(matcher.group(1).charAt(0), matcher.group(3)));
            }
        }
    }

    public void save() {
        StringBuilder out = new StringBuilder("# Configuration file\n\n");
        categories.forEach((name, properties) -> {
            String categoryComment = categoryComments.get(name);
            if (categoryComment != null) appendComment(out, "", categoryComment);
            out.append(quote(name)).append(" {\n");
            properties.forEach((key, property) -> {
                if (property.comment() != null) appendComment(out, "    ", property.comment());
                out.append("    ").append(property.type()).append(':').append(quote(key)).append('=').append(property.getString()).append('\n');
                property.markSaved();
            });
            out.append("}\n\n");
        });
        try {
            if (file.getParentFile() != null) Files.createDirectories(file.getParentFile().toPath());
            Files.write(file.toPath(), out.toString().getBytes(StandardCharsets.UTF_8));
            changed = false;
        } catch (IOException e) {
            LogManager.getLogger("ForgeCompat").error("Failed to write {}", file, e);
        }
    }

    private static void appendComment(StringBuilder out, String indent, String comment) {
        for (String line : comment.split("\n")) out.append(indent).append("# ").append(line).append('\n');
    }

    private static String quote(String name) {
        return name.matches("[A-Za-z0-9_.-]+") ? name : '"' + name + '"';
    }

    public boolean hasChanged() {
        return changed || categories.values().stream().anyMatch(properties -> properties.values().stream().anyMatch(Property::changed));
    }

    public Configuration setCategoryComment(String category, String comment) {
        category(category);
        categoryComments.put(category, comment);
        return this;
    }

    public ConfigCategory getCategory(String name) {
        return new ConfigCategory(this, name, category(name));
    }

    private Map<String, Property> category(String name) {
        return categories.computeIfAbsent(name, key -> new LinkedHashMap<>());
    }

    private Property get(String category, String key, char type, String defaultValue) {
        return category(category).computeIfAbsent(key, k -> {
            changed = true;
            return new Property(type, defaultValue);
        });
    }

    public Property get(String category, String key, boolean defaultValue) {
        return get(category, key, 'B', Boolean.toString(defaultValue));
    }

    public Property get(String category, String key, int defaultValue) {
        return get(category, key, 'I', Integer.toString(defaultValue));
    }

    public Property get(String category, String key, double defaultValue) {
        return get(category, key, 'D', Double.toString(defaultValue));
    }

    public Property get(String category, String key, String defaultValue) {
        return get(category, key, 'S', defaultValue);
    }

    public boolean getBoolean(String name, String category, boolean defaultValue, String comment) {
        Property property = get(category, name, defaultValue);
        property.comment(comment + " [default: " + defaultValue + "]");
        return property.getBoolean(defaultValue);
    }

    public int getInt(String name, String category, int defaultValue, int min, int max, String comment) {
        Property property = get(category, name, defaultValue);
        property.comment(comment + " [range: " + min + " ~ " + max + ", default: " + defaultValue + "]");
        property.range(min, max);
        return Math.max(min, Math.min(max, property.getInt(defaultValue)));
    }

    public float getFloat(String name, String category, float defaultValue, float min, float max, String comment) {
        Property property = get(category, name, Float.toString(defaultValue));
        property.comment(comment + " [range: " + min + " ~ " + max + ", default: " + defaultValue + "]");
        property.range(min, max);
        return (float) Math.max(min, Math.min(max, property.getDouble(defaultValue)));
    }

    public String getString(String name, String category, String defaultValue, String comment) {
        Property property = get(category, name, defaultValue);
        property.comment(comment + " [default: " + defaultValue + "]");
        return property.getString();
    }
}
