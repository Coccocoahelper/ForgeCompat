package cc.polyfrost.oneconfig.config;

import cc.polyfrost.oneconfig.config.data.Mod;
import org.polyfrost.forgecompat.LegacyOneConfig;

public class Config {
    public final transient Mod mod;
    public final transient String configFile;
    public boolean enabled;
    private transient LegacyOneConfig.Bridge bridge;

    public Config(Mod modData, String configFile, boolean enabled) {
        this.mod = modData;
        this.configFile = configFile;
        this.enabled = enabled;
        modData.config = this;
    }

    public Config(Mod modData, String configFile) {
        this(modData, configFile, true);
    }

    public void initialize() {
        if (bridge == null) bridge = LegacyOneConfig.register(this);
    }

    public void openGui() {
        if (bridge != null) bridge.open();
    }

    public void save() {
        if (bridge != null) bridge.save();
    }

    public boolean isEnabled() {
        return enabled;
    }
}
