package top.redstarmc.plugin.vban.ban;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.ResultedEvent;
import  com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import top.redstarmc.plugin.vban.VBan;

import java.util.UUID;

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
        UUID player_uuid = player.getUniqueId();
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

    public String why1(){
        String why = "";
        return null;
    }
    public String why2(){

        return null;
    }
    public String why3(){

        return null;
    }
    public String why4(){

        return null;
    }
    public String why5(){

        return null;
    }
    public String why6(){

        return null;
    }
    public String why7(){

        return null;
    }
}
