package top.redstarmc.plugin.vban.listener;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.network.ProtocolVersion;
import net.kyori.adventure.text.Component;
import top.redstarmc.plugin.vban.SQL;
import top.redstarmc.plugin.vban.VBan;
import top.redstarmc.plugin.vban.util.ResultPlayerInfo;
import top.redstarmc.plugin.vban.util.TResultPlayerInfo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import static net.kyori.adventure.text.Component.blockNBT;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public class IsBan {
    public static Component addBanComponent(int id, String why,long ban_time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(ban_time);
        return text()
                .append(text("Red",RED),text("Star",YELLOW),text("MC",GRAY),text("服务器封禁系统",AQUA),
                        text("你  已  被  永  久  封  禁   ！\n",RED),
                        text("原因：",WHITE),text(why,GRAY),
                        text("你的封禁ID为:",WHITE),text(id+"\n",BLUE),
                        text("你于",WHITE),text(time,GRAY),text("被封禁",WHITE),
                        text("申诉邮箱：report@redstarmc.top",RED))
                .build();
    }
    public static Component addTBanComponent(int id, String why,long ban_time,long unban_time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(ban_time);
        return text()
                .append(text("Red",RED),text("Star",YELLOW),text("MC",GRAY),text("服务器封禁系统",AQUA),
                        text("你  已  被  临  时  封  禁   ！\n",RED),
                        text("原因：",WHITE),text(why,GRAY),
                        text("你的封禁ID为:",WHITE),text(id+"\n",BLUE),
                        text("你于",WHITE),text(time,GRAY),text("被封禁",WHITE),
                        text("申诉邮箱：report@redstarmc.top",RED))
                .build();
    }
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
            result = PreLoginEvent.PreLoginComponentResult.denied(addBanComponent(id,why,ban_time));
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
                result = PreLoginEvent.PreLoginComponentResult.denied(addTBanComponent(id,why,ban_time,unban_time));
            }
        }
        event.setResult(result);
    }

}
