package top.redstarmc.plugin.vban.Component;

import net.kyori.adventure.text.Component;

import java.text.SimpleDateFormat;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public class TBanComponet {
    public static Component returnTBanComponent(int id, String why, long ban_time, long unban_time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(ban_time);
        return text()
                .append(text("Red",RED),text("Star",YELLOW),text("MC",GRAY),text("服务器封禁系统",AQUA),
                        text("你  已  被  临  时  封  禁   ！\n",RED),
                        text("原因：",WHITE),text(why,GRAY),text("\n"),
                        text("你的封禁ID为:",WHITE),text(id+"\n",BLUE),
                        text("你于",WHITE),text(time,GRAY),text("被封禁\n",WHITE),
                        text("距离解封还有"),
                        text("申诉邮箱：report@redstarmc.top",RED))
                .build();
    }
}
