package cc.polyfrost.oneconfig.utils.hypixel;

public class HypixelUtils {
    public static final HypixelUtils INSTANCE = new HypixelUtils();

    public boolean isHypixel() {
        return org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils.isHypixel();
    }
}
