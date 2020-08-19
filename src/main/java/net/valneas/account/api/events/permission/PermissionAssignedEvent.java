package net.valneas.account.api.events.permission;

import net.valneas.account.rank.Rank;
import net.valneas.account.rank.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PermissionAssignedEvent extends PlayerEvent {

    private static final HandlerList handlerList = new HandlerList();
    private final Rank rank;
    private final Permission permissionManager;
    private final String permission;

    public PermissionAssignedEvent(Player player, Rank rank, Permission permissionManager, String permission) {
        super(player);
        this.rank = rank;
        this.permissionManager = permissionManager;
        this.permission = permission;
    }

    public Rank getRank() {
        return rank;
    }

    public Permission getPermissionManager() {
        return permissionManager;
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
