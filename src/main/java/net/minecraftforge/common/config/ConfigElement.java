package net.minecraftforge.common.config;

import java.util.Map;

import net.minecraftforge.fml.client.config.IConfigElement;

public class ConfigElement implements IConfigElement {
    private final ConfigCategory category;

    public ConfigElement(ConfigCategory category) {
        this.category = category;
    }

    @Override
    public String getName() {
        return category.name;
    }

    @Override
    public Map<String, Property> getProperties() {
        return category.properties;
    }

    @Override
    public void save() {
        category.config.save();
    }
}
