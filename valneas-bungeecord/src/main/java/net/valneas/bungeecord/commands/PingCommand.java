package net.valneas.bungeecord.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PingCommand extends Command {

    public PingCommand(java.lang.String name) {
        super(name);
    }

    @java.lang.Override
    public void execute(CommandSender sender, java.lang.String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            player.sendMessage(new TextComponent(ChatColor.AQUA + "Votre ping est de §r" + player.getPing() + ChatColor.AQUA + " ms."));
        }
    }
}
