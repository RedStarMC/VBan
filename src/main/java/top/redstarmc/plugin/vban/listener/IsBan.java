package top.redstarmc.plugin.vban.listener;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import top.redstarmc.plugin.vban.SQL;
import top.redstarmc.plugin.vban.VBan;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IsBan {
    public Component addComponent(int why){
        Component because;
        if (why == 1){
          getClass().arrayType();
        }
        return null;
    }

    /**
     * 监听器，观察是否被封禁
     * @param event 加入事件
     */
    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerJoin(LoginEvent event){
        boolean ban_state = false;
        Player player = event.getPlayer();
        String player_name = player.getUsername();
        try {
            List<Map> mapList = SQL.banWhere(player_name);
            Map map = new HashMap<>();
            Map map1 = new HashMap<>();
            assert mapList != null;
            map = mapList.get(0);
            map1 = mapList.get(1);
            String why = (String) map.get(1);
            int id = (int) map1.get(1);
        } catch (SQLException e) {
            VBan.getVban().getLogger().error("无法获得数据库查询结果"+e.getMessage());
        }
        //查询
        try {
            int why = 0;
            if (!ban_state){
                event.setResult(ResultedEvent.ComponentResult.denied(addComponent(why)));
            }else {
                event.setResult(ResultedEvent.ComponentResult.allowed());
            }
        } catch (Exception e) {
            VBan.getVban().getLogger().error(e.getMessage());
        }
    }

}
