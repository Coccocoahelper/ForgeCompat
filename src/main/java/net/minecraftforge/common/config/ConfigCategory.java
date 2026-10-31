package net.minecraftforge.common.config;

import java.util.Map;

public class ConfigCategory {
    final Configuration config;
    final String name;
    final Map<String, Property> properties;

    ConfigCategory(Configuration config, String name, Map<String, Property> properties) {
        this.config = config;
        this.name = name;
        this.properties = properties;
    }
}
