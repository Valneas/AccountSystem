package net.valneas.bungeecord;

import net.md_5.bungee.api.plugin.Plugin;
import net.valneas.bungeecord.commands.LobbyCommand;
import net.valneas.bungeecord.commands.PingCommand;
import net.valneas.bungeecord.listener.PingListener;

public class Core extends Plugin {

    @java.lang.Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new PingListener());
        getProxy().getPluginManager().registerCommand(this, new LobbyCommand("lobby", "", "hub"));
        getProxy().getPluginManager().registerCommand(this, new PingCommand("ping"));
    }
}
