package top.redstarmc.plugin.vban.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import top.redstarmc.plugin.vban.SQL;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UnBan implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (args.length == 0){
            source.sendMessage(Component.text("""
                    使用/vunban [封禁ID] 参数是必须的
                    用于解除错误的封禁
                    """, NamedTextColor.AQUA));
        }else if (args.length == 1) {
            String Cmd = args[0];
            int id = 0;
            try {
                id = Integer.parseInt(Cmd);
            } catch (Exception e) {
                source.sendMessage(Component.text("必须输入整数！", NamedTextColor.RED));
            }
            SQL sql = new SQL();
            try {
                sql.delBan(id);
                source.sendMessage(Component.text("成 功 解 封 封禁ID为"+id+"的玩家！",NamedTextColor.GREEN));
            } catch (SQLException e) {
                source.sendMessage(Component.text("错误！请查看控制台！",NamedTextColor.DARK_RED));
                throw new RuntimeException(e);
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
        return invocation.source().hasPermission("vban.unban");
    }
}
