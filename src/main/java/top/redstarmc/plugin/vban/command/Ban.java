package top.redstarmc.plugin.vban.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import top.redstarmc.plugin.vban.Component.BanComponent;
import top.redstarmc.plugin.vban.SQL;
import top.redstarmc.plugin.vban.VBan;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class Ban implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (args.length == 0){
            source.sendMessage(Component.text("""
                    使用/vban [玩家名] [违反的内容] 参数是必须的
                    效果就是：你违反了[违反的内容]
                    必须写明违反的规定名称和条数
                    封禁自动封禁同ip下所有玩家""", NamedTextColor.AQUA));
        }else if (args.length == 1){
            source.sendMessage(Component.text("""
                    使用/vban [玩家名] [违反的内容] 参数是必须的\s
                    效果就是：你违反了[违反的内容]
                    必须写明违反的规定名称和条数
                    封禁自动封禁同ip下所有玩家""",NamedTextColor.AQUA));
        }else if (args.length == 2){
            String Cmd_1 = args[0];
            String Cmd_2 = args[1];
            long time = System.currentTimeMillis();
            SQL sql = new SQL();
            try {
                sql.banINSERT(Cmd_1,Cmd_2,time);
                kickBannedPlayer(Cmd_1);
                source.sendMessage(Component.text("玩家"+Cmd_1+"已被封禁！", NamedTextColor.RED));
                source.sendMessage(Component.text("原因："+Cmd_2, NamedTextColor.GOLD));

            } catch (SQLException e) {
                VBan.getVban().getLogger().info("发生错误！"+e.getMessage());
            }
        }else {
            source.sendMessage(Component.text("语法错误！", NamedTextColor.RED));
        }
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return SimpleCommand.super.suggestAsync(invocation);

    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("vban.ban");
    }


    public void kickBannedPlayer(String player_name){
        Optional<Player> aaa = VBan.getVban().getServer().getPlayer(player_name);
        if (aaa.isEmpty()){
            return;
        }
        aaa.ifPresent(player -> player.disconnect(BanComponent.returnBanComponentint(0, "请重新进入以查看原因", System.currentTimeMillis())));
    }
}
