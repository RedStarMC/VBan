package top.redstarmc.plugin.vban.Component;

import net.kyori.adventure.text.Component;

import java.text.SimpleDateFormat;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public class BanComponent {

    public static Component returnBanComponentint(int id, String why,long ban_time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(ban_time);
        return text()
                .append(text("Red",RED),text("Star",YELLOW),text("MC",GRAY),text("服务器封禁系统\n",AQUA),
                        text("你  已  被  永  久  封  禁   ！\n",RED),
                        text("原因：",WHITE),text(why,GRAY),text("\n"),
                        text("你的封禁ID为:",WHITE),text(id+"\n",GREEN),
                        text("你于",WHITE),text(time,GRAY),text("被封禁\n",WHITE),
                        text("申诉邮箱：report@redstarmc.top",RED))
                .build();
    }

}
