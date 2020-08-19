package net.valneas.account.listener;

import net.valneas.account.AccountManager;
import net.valneas.account.AccountSystem;
import net.valneas.account.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final AccountSystem main;

    public PlayerQuitListener(AccountSystem main) {
        this.main = main;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        AccountManager accountManager = new AccountManager(main, player);

        if(accountManager.hasAnAccount()){
            accountManager.setLastDisconnection(System.currentTimeMillis());
            accountManager.setLastIp(PlayerUtil.getIp(player));
        }
    }
}
