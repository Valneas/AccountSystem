package net.valneas.bungeecord.listener;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Arrays;
import java.util.List;

public class PingListener implements Listener {

    @EventHandler
    public void onPing(final ProxyPingEvent event) {
        final ServerPing ping = event.getResponse();
        final List<String> lines = Arrays.asList("   §6§lValnéas§r §e§oMMORPG   ",
                                                "   §dEn développement",
                                                "          §f1.16.5");
        ServerPing.Players players = event.getResponse().getPlayers();
        ServerPing.PlayerInfo[] sample =  new ServerPing.PlayerInfo[lines.size()];

        for (int i = 0; i < sample.length; i++) {
            sample[i] = new ServerPing.PlayerInfo(lines.get(i), "");
        }

        players.setSample(sample);
        ping.setDescriptionComponent(new TextComponent("             §6§lValnéas §d[En développement]\n              §f§oAucune date d'ouverture"));

        event.setResponse(ping);
    }

}
