package net.minecraftforge.fml.common;

public class ModMetadata {
    public String modId;
    public String name;
    public String version;

    public ModMetadata(String modId, String name, String version) {
        this.modId = modId;
        this.name = name;
        this.version = version;
    }
}
