package top.redstarmc.plugin.vban.listener;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import net.kyori.adventure.text.Component;
import top.redstarmc.plugin.vban.Component.BanComponent;
import top.redstarmc.plugin.vban.Component.TBanComponet;
import top.redstarmc.plugin.vban.SQL;
import top.redstarmc.plugin.vban.VBan;
import top.redstarmc.plugin.vban.util.ResultPlayerInfo;
import top.redstarmc.plugin.vban.util.TResultPlayerInfo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public class IsBan {
    /**
     * 监听器，观察是否被封禁
     * @param event 加入事件
     */
    @Subscribe(order = PostOrder.FIRST)
    public void onPreLoginEvent(PreLoginEvent event){
        String player_name = event.getUsername();
        int id = 0;
        String why = null;
        long ban_time = 0L;
        long unban_time = 0L;
        boolean aBanBoolean = false;  //意为未被封禁
        boolean aTBanBoolean = false;
        /*
        查询是否被永久封禁
         */
        try {
            List<ResultPlayerInfo> resultPlayerInfoList = SQL.banWhere(player_name);
            ResultPlayerInfo resultPlayerInfo = resultPlayerInfoList.get(0);
            id = resultPlayerInfo.getId();
            why = resultPlayerInfo.getWhy();
            ban_time = resultPlayerInfo.getBan_time();
            aBanBoolean = resultPlayerInfo.isaBoolean();
        } catch (SQLException e) {
            VBan.getVban().getLogger().error(e.getMessage());
        }
        PreLoginEvent.PreLoginComponentResult result = PreLoginEvent.PreLoginComponentResult.allowed();
        /*
        被永久封禁直接拒绝，未被永久封禁查询临时封禁
         */
        if(aBanBoolean){
            result = PreLoginEvent.PreLoginComponentResult.denied(BanComponent.returnBanComponentint(id,why,ban_time));
        }else  {
            try{
                List<TResultPlayerInfo> tResultPlayerInfoList = SQL.tBanWhere(player_name);
                TResultPlayerInfo tResultPlayerInfo = tResultPlayerInfoList.get(0);
                id = tResultPlayerInfo.getId();
                why = tResultPlayerInfo.getWhy();
                aTBanBoolean = tResultPlayerInfo.isaBoolean();
                ban_time = tResultPlayerInfo.getBan_time();
                unban_time = tResultPlayerInfo.getUnban_time();
            } catch (SQLException e) {
                VBan.getVban().getLogger().info(e.getMessage());
            }
            if (aTBanBoolean){
                result = PreLoginEvent.PreLoginComponentResult.denied(TBanComponet.returnTBanComponent(id,why,ban_time,unban_time));
            }
        }
        event.setResult(result);
    }

}
