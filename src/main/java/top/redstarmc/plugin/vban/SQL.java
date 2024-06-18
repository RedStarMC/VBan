package top.redstarmc.plugin.vban;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQL {
    private static Connection c = null;
    private static Statement stmt = null;

    public static void startSQL() {
        File banlist = new File("./plugins/Vban/banlist.db");
        VBan.getVban().getServer().getConfiguration();
        if (banlist.exists()) {
            VBan.getVban().getLogger().info("[SQL初始化]文件已经存在");
        } else {
            try {
                var dir = new File("./plugins/Vban");
                if (!dir.exists()) dir.mkdirs();
                banlist.createNewFile();
                System.out.println("[SQL初始化]文件创建成功");
            } catch (IOException e) {
                VBan.getVban().getLogger().warn("IO异常");
                throw new RuntimeException(e);
            }
        }
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:./plugins/Vban/banlist.db");
            if (tableExists("banlist")){
                stmt = c.createStatement();
                String sql = "CREATE TABLE COMPANY " +
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        " PLAYER_NAME    TEXT    NOT NULL, " +
                        " UUID           INT     NOT NULL, " +
                        " WHY            INT    NOT NULL, " +
                        " TIME           INT     ," +
                        " FTIME          INT     NOT NULL)";
                stmt.execute(sql);
                stmt.close();
                VBan.getVban().getLogger().info("[SQL初始化]数据库创建成功！");
            }else {
                VBan.getVban().getLogger().info("[SQL初始化]数据库已经存在！");
            }
        } catch (Exception e) {
            VBan.getVban().getLogger().error("[SQL初始化]数据库创建失败");
            VBan.getVban().getLogger().warn("现在抛出异常");
            VBan.getVban().getLogger().error(e.getMessage());
        }
    }

    public static void CLOSESQL() throws SQLException {
        c.close();
    }

    public List SELECT(String plyer_name) {
        String SQL = "";

        return null;
    }

    public void UPLOAD(String player_name, UUID uuid, int why, int f_time) throws SQLException {
        String SQL = "INSERT INTO COMPANY (ID,PLAYER_NAME,UUID,WHY,FTIME)" +
                "VALUES (1," + player_name + "," + uuid + "," + why + "," + f_time + ");";
        stmt.executeUpdate(SQL);
    }

    public static boolean tableExists(String tableName){
        try{
            DatabaseMetaData md = c.getMetaData();
            ResultSet rs = md.getTables(null, null, tableName, null);
            rs.last();
            return rs.getRow() > 0;
        }catch(SQLException ex){
            VBan.getVban().getLogger().info("");
        }
        return false;
    }
}
