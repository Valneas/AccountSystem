package net.valneas.account.events;

import net.valneas.account.api.events.permission.PermissionAssignedEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PermissionAssignedListener implements Listener {

    @EventHandler
    public void onPermissionAssigned(PermissionAssignedEvent e){
        e.getPlayer().sendMessage(ChatColor.GRAY + "La permission " + ChatColor.YELLOW
                + e.getPermission() + ChatColor.GRAY + " vous a été ajoutée");
    }
}
