package net.valneas.account.listener;

import io.github.leonardosnt.bungeechannelapi.BungeeChannelApi;
import net.valneas.account.AccountManager;
import net.valneas.account.AccountSystem;
import net.valneas.account.api.events.rank.MajorRankChangedEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MajorRankChangedListener implements Listener {

    private final AccountSystem main;

    public MajorRankChangedListener(AccountSystem main) {
        this.main = main;
    }

    @EventHandler
    public void onMajorRankChanged(MajorRankChangedEvent e){
        final AccountManager account = e.getAccount();
        final BungeeChannelApi api = BungeeChannelApi.of(main);

        api.getPlayerList("ALL")
                .whenComplete((result, error) -> {
                    if(result.stream().anyMatch(s -> s.equals(account.getName()))){
                        api.sendMessage(account.getName(), ChatColor.YELLOW + "" + ChatColor.ITALIC + "Michel §r§l➔ " + ChatColor.YELLOW +
                                "Hey, on m'a dit de te dire que ton rang majeur a changé. Donc tu passes du rang §r" + ChatColor.BOLD + e.getPreviousMajorRank().getName() +
                                ChatColor.YELLOW + " à §r" + ChatColor.BOLD + e.getNewMajorRank().getName() + ChatColor.YELLOW +
                                ". Voilà, passe une bonne journée sur " + ChatColor.YELLOW + "Valnéas");
                    }
                });
    }
}
