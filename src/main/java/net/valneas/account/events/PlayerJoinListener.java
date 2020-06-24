package net.valneas.account.events;

import net.valneas.account.AccountManager;
import net.valneas.account.AccountSystem;
import net.valneas.account.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final AccountSystem main;

    public PlayerJoinListener(AccountSystem main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        AccountManager accountManager = new AccountManager(main, player);

        long current = System.currentTimeMillis();

        if(!accountManager.hasAnAccount()){
            accountManager.createAccount();
            accountManager.setFirstConnection(current);
        }else{
            accountManager.updateOnLogin();
        }

        accountManager.setLastConnection(current);
        accountManager.setLastIp(PlayerUtil.getIp(player));
    }
}
