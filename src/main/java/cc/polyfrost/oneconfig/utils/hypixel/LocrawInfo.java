package cc.polyfrost.oneconfig.utils.hypixel;

public class LocrawInfo {
    private final String serverId;
    private final String gameMode;
    private final String mapName;

    public LocrawInfo(String serverId, String gameMode, String mapName) {
        this.serverId = serverId;
        this.gameMode = gameMode;
        this.mapName = mapName;
    }

    public String getServerId() {
        return serverId;
    }

    public String getGameMode() {
        return gameMode;
    }

    public String getMapName() {
        return mapName;
    }
}
