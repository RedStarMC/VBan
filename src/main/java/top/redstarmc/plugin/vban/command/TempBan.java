package top.redstarmc.plugin.vban.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TempBan implements SimpleCommand{
    @Override
    public void execute(SimpleCommand.Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (args.length == 0){
            source.sendMessage(Component.text("""
                    使用/vtempban 或/vtban [玩家名] [理由] 参数是必须的
                    效果就是：你违反了[违反的内容]
                    必须写明违反的规定名称和条数
                    封禁自动封禁同ip下所有玩家
                    """, NamedTextColor.AQUA));
        }else if (args.length == 1){
            source.sendMessage(Component.text("""
                    使用/vtempban 或/vtban [玩家名] [理由] 参数是必须的
                    效果就是：你违反了[违反的内容]
                    必须写明违反的规定名称和条数
                    封禁自动封禁同ip下所有玩家
                    """,NamedTextColor.AQUA));
        }else if(args.length == 2){
            String Cmd_1 = args[0];
            String Cmd_2 = args[1];
        }else {
            source.sendMessage(Component.text("语法错误！", NamedTextColor.RED));

        }
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(SimpleCommand.Invocation invocation) {
        return SimpleCommand.super.suggestAsync(invocation);
    }

    @Override
    public boolean hasPermission(SimpleCommand.Invocation invocation) {
        return invocation.source().hasPermission("vban.tempban");
    }
}
