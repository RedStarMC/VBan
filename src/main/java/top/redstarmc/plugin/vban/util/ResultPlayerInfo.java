package top.redstarmc.plugin.vban.util;

public class ResultPlayerInfo {
    private String player_name;
    private String why;
    private int id;
    private boolean aBoolean;

    public ResultPlayerInfo(String player_name, String why, int id, boolean aBoolean) {
        this.player_name = player_name;
        this.why = why;
        this.id = id;
        this.aBoolean = aBoolean;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public String getWhy() {
        return why;
    }

    public int getId() {
        return id;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }
}
