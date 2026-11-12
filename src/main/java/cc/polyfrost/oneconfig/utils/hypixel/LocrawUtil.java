package cc.polyfrost.oneconfig.utils.hypixel;

import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils;

public class LocrawUtil {
    public static final LocrawUtil INSTANCE = new LocrawUtil();

    public LocrawInfo getLocrawInfo() {
        HypixelUtils.Location location = HypixelUtils.getLocation();
        return new LocrawInfo(
                location.getServerName().orElse(""),
                location.getMode().orElse(""),
                location.getMapName().orElse("")
        );
    }

    public boolean isInGame() {
        return HypixelUtils.getLocation().inGame();
    }
}
