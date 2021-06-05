package net.valneas.bungeecord.commands;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LobbyCommand extends Command {

    private String lobby = "lobby";

    public LobbyCommand(String name, String permission, String...aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;

            if(p.getServer().getInfo().getName().equals(lobby)) {
                p.sendMessage(new TextComponent("§cTu es déjà au lobby !"));
                return;
            }
            p.connect(ProxyServer.getInstance().getServerInfo(lobby));
            p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§eConnecté au lobby !"));
        }
    }
}
