package net.minecraftforge.fml.client.config;

import java.util.Map;

import net.minecraftforge.common.config.Property;

public interface IConfigElement {
    String getName();

    Map<String, Property> getProperties();

    void save();
}
