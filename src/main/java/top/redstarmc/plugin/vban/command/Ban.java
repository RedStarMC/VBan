package top.redstarmc.plugin.vban.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import top.redstarmc.plugin.vban.SQL;
import top.redstarmc.plugin.vban.VBan;

import javax.inject.Named;
import java.sql.SQLException;
import java.util.List;
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
                source.sendMessage(Component.text("玩家"+Cmd_1+"已被封禁！", NamedTextColor.DARK_RED));
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
}
