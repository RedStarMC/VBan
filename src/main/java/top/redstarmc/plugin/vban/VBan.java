package top.redstarmc.plugin.vban;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyReloadEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;
import top.redstarmc.plugin.vban.command.*;
import top.redstarmc.plugin.vban.listener.IsBan;

import java.sql.SQLException;

@Plugin(
        id = "vban",
        name = "VBan",
        authors = {"pingguomc"},
        version = "0.1.5"
)
public class VBan {
    private final String head = "[Vl封禁插件]";
    private static VBan Vban = null;
    private final Logger logger;
    private final ProxyServer server;
    @Inject
    public VBan(ProxyServer server, Logger logger){
        this.server = server;
        this.logger = logger;

        logger.info("插件构建成功！");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        Vban = this;
        logger.info("Velocity-ban    速度反向代理-封禁插件");
        logger.info("  ");
        logger.info("作者： pingguomc");
        logger.info("特别鸣谢： HopeAsd,H3xadecimal");
        logger.info(head+"正 在 初 始 化 插 件…………");
        logger.info(head+"正在注册监听器……");
        server.getEventManager().register(this,new IsBan());
        logger.info(head+"正在注册数据库……");
        SQL.startSQL();
        logger.info(head+"正在注册指令");
        registerCommand();
        logger.info(head+"插 件 加 载 成 功！");
    }
    @Subscribe
    public void onPorxyReloadEvent(ProxyReloadEvent event) throws SQLException {
        SQL.CLOSESQL();
        logger.info(head+"感谢使用！再见！");
    }
    @Subscribe
    public void onPorxyShutdownEvent(ProxyShutdownEvent event) throws SQLException {
        SQL.CLOSESQL();
        logger.info(head+"感谢使用！再见！");
    }
    public void registerCommand(){
        CommandManager commandManager = server.getCommandManager();
        CommandMeta vban_Meta = commandManager.metaBuilder("vban").plugin(this).build();
        CommandMeta vwarn_Meta = commandManager.metaBuilder("vwarn").plugin(this).build();
        CommandMeta vquery_warn_Meta = commandManager.metaBuilder("vquerywarn").aliases("vqw").plugin(this).build();
        CommandMeta vbanlist_Meta = commandManager.metaBuilder("vbanlist").aliases("vbl").plugin(this).build();
        CommandMeta vunban_Meta = commandManager.metaBuilder("vunban").plugin(this).build();
        CommandMeta vtempban_Meta = commandManager.metaBuilder("vtempban").aliases("vtban").plugin(this).build();
        CommandMeta vuntempban_Meta = commandManager.metaBuilder("vuntempban").aliases("vuntb").plugin(this).build();

        SimpleCommand vban_command = new Ban();
        SimpleCommand vwarn_command = new Warn();
        SimpleCommand vquery_warn_command = new QueryWarn();
        SimpleCommand vban_list_command = new BanList();
        SimpleCommand vunban_command = new UnBan();
        SimpleCommand vtemp_ban_command = new TempBan();
        SimpleCommand vuntemp_ban_command = new UnTempBan();

        commandManager.register(vban_Meta,vban_command);
        commandManager.register(vwarn_Meta,vwarn_command);
        commandManager.register(vquery_warn_Meta,vquery_warn_command);
        commandManager.register(vbanlist_Meta,vban_list_command);
        commandManager.register(vunban_Meta,vunban_command);
        commandManager.register(vtempban_Meta,vtemp_ban_command);
        commandManager.register(vuntempban_Meta,vuntemp_ban_command);
    }
    public static VBan getVban() {
        return Vban;
    }
    public Logger getLogger() {
        return logger;
    }
    public ProxyServer getServer() {
        return server;
    }
    public String getHead(){return head;}
}
