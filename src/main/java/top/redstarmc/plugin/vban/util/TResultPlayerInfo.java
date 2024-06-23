package top.redstarmc.plugin.vban.util;

public class TResultPlayerInfo {
    private String player_name;
    private String why;
    private int id;
    private boolean aBoolean;
    private long ban_time;
    private long unban_time;

    public TResultPlayerInfo(String player_name, String why, int id, boolean aBoolean, long ban_time, long unban_time) {
        this.player_name = player_name;
        this.why = why;
        this.id = id;
        this.aBoolean = aBoolean;
        this.ban_time = ban_time;
        this.unban_time = unban_time;
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

    public long getBan_time() {
        return ban_time;
    }

    public long getUnban_time() {
        return unban_time;
    }
}
