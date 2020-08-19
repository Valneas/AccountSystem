package net.valneas.account.listener;

import net.valneas.account.api.events.permission.PermissionUnAssignedEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

public class PermissionUnAssignedListener implements Listener {

    public void onPermissionUnAssigned(PermissionUnAssignedEvent e){
        e.getPlayer().sendMessage(ChatColor.GRAY + "La permission " + ChatColor.YELLOW
                + e.getPermission() + ChatColor.GRAY + " vous a été retirée");
    }
}
