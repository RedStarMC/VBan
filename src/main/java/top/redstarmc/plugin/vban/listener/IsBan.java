package top.redstarmc.plugin.vban.listener;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.network.ProtocolVersion;
import net.kyori.adventure.text.Component;
import top.redstarmc.plugin.vban.SQL;
import top.redstarmc.plugin.vban.VBan;
import top.redstarmc.plugin.vban.util.ResultPlayerInfo;

import java.sql.SQLException;
import java.util.List;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public class IsBan {
    private static final String server_address = "mc.redstarmc.top";
    public static Component addComponent(int id, String why){
        return text()
                .append(text("你 已 被 永 久 封 禁 ！\n",RED),
                        text("Red",RED),text("Star",YELLOW),text("MC",GRAY),text("服务器封禁系统",BLUE),
                        text("原因："+why+"\n", DARK_GREEN),
                        text("你的封禁ID为:"+id+"\n",BLUE),
                        text(server_address,RED))
                .build();
    }

    /**
     * 监听器，观察是否被封禁
     * @param event 加入事件
     */
    @Subscribe(order = PostOrder.FIRST)
    public void onPreLoginEvent(PreLoginEvent event){
        String player_name = event.getUsername();
        ProtocolVersion protocolVersion = event.getConnection().getProtocolVersion();
        VBan.getVban().getLogger().info(protocolVersion.toString());
        int id = 0;
        String why = null;
        boolean aBoolean = false;  //意为未被封禁
        try {
            List<ResultPlayerInfo> resultPlayerInfoList = SQL.banWhere(player_name);
            ResultPlayerInfo resultPlayerInfo = resultPlayerInfoList.get(0);
            id = resultPlayerInfo.getId();
            why = resultPlayerInfo.getWhy();
            aBoolean = resultPlayerInfo.isaBoolean();
        } catch (SQLException e) {
            VBan.getVban().getLogger().error(e.getMessage());
        }
        //查询
        PreLoginEvent.PreLoginComponentResult result = PreLoginEvent.PreLoginComponentResult.allowed();
        if(aBoolean){
            result = PreLoginEvent.PreLoginComponentResult.denied(addComponent(id,why));
        }
        event.setResult(result);
    }

}
