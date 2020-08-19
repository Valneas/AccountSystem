package net.valneas.account.rank.permission;

import net.valneas.account.AccountSystem;
import net.valneas.account.api.events.permission.PermissionAssignedEvent;
import net.valneas.account.api.events.permission.PermissionUnAssignedEvent;
import net.valneas.account.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class Permission {

    private final PermissionManager permissionManager;
    private final Player player;
    private final Rank rank;

    public Permission(AccountSystem main, Player player) {
        this.permissionManager = main.getPermissionManager();
        this.player = player;
        this.rank = main.getMongoUtil().getAccountManager(player).newRankManager();
    }

    public Permission(PermissionManager permissionManager, Player player, Rank rank) {
        this.permissionManager = permissionManager;
        this.player = player;
        this.rank = rank;
    }

    public void addPermission(String permission){
        permissionManager.add(player, permission);
        Bukkit.getPluginManager().callEvent(new PermissionAssignedEvent(player, rank, this, permission));
    }

    public void removePermission(String permission){
        permissionManager.remove(player, permission);
        Bukkit.getPluginManager().callEvent(new PermissionUnAssignedEvent(player, rank, this, permission));
    }

    public Map<String, Boolean> getPermissions(){
        return permissionManager.get(player).getPermissions();
    }
}
