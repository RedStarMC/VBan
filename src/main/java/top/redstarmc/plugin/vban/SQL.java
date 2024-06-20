package top.redstarmc.plugin.vban;

import org.checkerframework.checker.units.qual.C;
import top.redstarmc.plugin.vban.util.ResultPlayerInfo;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class SQL {
    private static Connection c = null;
    private static Statement stmt = null;
    private static final String head = VBan.getVban().getHead();
    /**
     * 初始化数据库
     */
    public static void startSQL() {
        File banlist = new File("./plugins/Vban/banlist.db");
        VBan.getVban().getServer().getConfiguration();
        if (banlist.exists()) {
            VBan.getVban().getLogger().info(head+"[SQL初始化]文件已经存在");
        } else {
            try {
                var dir = new File("./plugins/Vban");
                if (!dir.exists()) dir.mkdirs();
                banlist.createNewFile();
                System.out.println(head+"[SQL初始化]文件创建成功");
            } catch (IOException e) {
                VBan.getVban().getLogger().warn(head+"IO异常");
                throw new RuntimeException(e);
            }
        }
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:./plugins/Vban/banlist.db");
            try {
                stmt = c.createStatement();
                String sql1 = "CREATE TABLE BANLIST " +
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        " PLAYER_NAME    TEXT    NOT NULL, " +
                        " WHY            TEXT    NOT NULL, " +
                        " FTIME          LONG     NOT NULL)";
                stmt.execute(sql1);
                String sql2 = "CREATE TABLE TBANLIST " +
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        " PLAYER_NAME    TEXT    NOT NULL, " +
                        " WHY            TEXT    NOT NULL, " +
                        " TIME           LONG     NOT NULL," +
                        " FTIME          INT     NOT NULL)";
                stmt.execute(sql2);
                stmt.close();
                VBan.getVban().getLogger().info(head+"[SQL初始化]数据表创建成功！");
            } catch (SQLException e) {
                VBan.getVban().getLogger().info(head+"[SQL初始化]数据表创建失败或已经存在！");
            }
        } catch (Exception e) {
            VBan.getVban().getLogger().error(head+"[SQL初始化]数据库创建失败");
            VBan.getVban().getLogger().warn("现在抛出异常");
            VBan.getVban().getLogger().error(e.getMessage());
        }
    }
    /**
    关闭数据库
     */
    public static void CLOSESQL() throws SQLException {
        c.close();
    }

    /**
     * 查询封禁数据库
     * @param plyer_name 玩家名称
     * @return 是否被封禁
     * @throws SQLException 6
     */
    public static List<ResultPlayerInfo> banWhere(String plyer_name) throws SQLException {
        PreparedStatement updateSales = c.prepareStatement("SELECT * FROM BANLIST WHERE PLAYER_NAME = ?");
        updateSales.setString(1,plyer_name);
        ResultSet resultSet = updateSales.executeQuery();
        List<ResultPlayerInfo> resultPlayerInfos = new ArrayList<>();
        if (resultSet.next()){
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String why = resultSet.getString("WHY");
                boolean aBoolean = true; //被封禁为真
                ResultPlayerInfo resultPlayerInfo = new ResultPlayerInfo(plyer_name,why,id,aBoolean);
                resultPlayerInfos.add(0,resultPlayerInfo);
            }
        }else {
            boolean aBoolean = false;
            int id = 0 ;
            ResultPlayerInfo resultPlayerInfo = new ResultPlayerInfo(plyer_name,null,id,aBoolean);
            resultPlayerInfos.add(0,resultPlayerInfo);
        }
        resultSet.close();
        updateSales.close();
        return resultPlayerInfos;
    }

    /**
     * 插入封禁数据
     * @param player_name 玩家名称
     * @param why 原因
     * @param f_time 被封禁的时间
     * @throws SQLException 6
     */
    public void banINSERT(String player_name, String why,long f_time) throws SQLException {
        String CHA ="SELECT count(*) FROM BANLIST";
        ResultSet s = stmt.executeQuery(CHA);
        int a = s.getInt("count(*)");
        int b = a+1;
        PreparedStatement updateSales = c.prepareStatement("INSERT INTO BANLIST (ID,PLAYER_NAME,WHY,FTIME) VALUES(?,?,?,?)");
        updateSales.setInt(1, b);
        updateSales.setString(2, player_name);
        updateSales.setString(3,why);
        updateSales.setLong(4,f_time);
        updateSales.executeUpdate();
        updateSales.close();
    }
    public void tbanINSERT(String player_name, String why,long time,long f_time) throws SQLException {
        String CHA ="SELECT count(*) FROM TBANLIST";
        ResultSet s = stmt.executeQuery(CHA);
        int a = s.getInt("count(*)");
        int b = a+1;
        PreparedStatement updateSales = c.prepareStatement("INSERT INTO TBANLIST (ID,PLAYER_NAME,WHY,TIME,FTIME) VALUES(?,?,?,?,?)");
        updateSales.setInt(1, b);
        updateSales.setString(2, player_name);
        updateSales.setString(3,why);
        updateSales.setLong(6,time);
        updateSales.setLong(5,f_time);
        updateSales.executeUpdate();
    }

}
