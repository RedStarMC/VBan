package top.redstarmc.plugin.vban;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Com implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        //获取命令别名后的参数
        String[] args = invocation.arguments();
        source.sendMessage(Component.text("Hello World!", NamedTextColor.AQUA));
    }
    /*此方法允许您控制谁可以执行该命令。
    如果执行者不具有所需的许可，
    命令的执行及其自动完成的控制
    将直接发送到发件人所在的服务器*/
    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("vban.admin");
    }
   /* 在这里，您可以以与前面的方法相同的方式提供论点建议，
    但是异步地。建议使用此方法而不是以前的方法
    尤其是在你提出更广泛的逻辑来提供建议的情况下*/
    @Override
    public CompletableFuture<List<String>> suggestAsync(final Invocation invocation) {
        Collection<Player> player_colloection = VBan.getVban().getServer().getAllPlayers();
//        for (String player_name : player_colloection.){
//
//        }
        return CompletableFuture.completedFuture(List.of("a"));
    }
    private final List<String> Help_menu = new ArrayList<>();

}
