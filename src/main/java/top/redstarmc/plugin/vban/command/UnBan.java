package top.redstarmc.plugin.vban.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UnBan implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (args.length == 0){
            source.sendMessage(Component.text("""
                    使用/unban [玩家名] 参数是必须的
                    用于解除错误的封禁
                    """, NamedTextColor.AQUA));
        }else if (args.length == 1){
            String Cmd = args[0];
            source.sendMessage(Component.text("""
                    使用/vwarn [玩家名]  参数是必须的
                    用于解除错误的封禁
                    """,NamedTextColor.AQUA));
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
