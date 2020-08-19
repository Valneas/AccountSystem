package net.valneas.account.rank.permission;

import com.mongodb.MongoClient;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PermissionManager {

    private final Map<UUID, PermissionAttachment> permissions;
    private final PermissionDB permissionDB;

    public PermissionManager(MongoClient mongoClient) {
        this.permissions = new HashMap<>();
        this.permissionDB = new PermissionDB(mongoClient);
    }

    private void put(UUID uuid, PermissionAttachment permissionAttachment){
        permissions.putIfAbsent(uuid, permissionAttachment);
    }

    private void checkExists(Player player, PermissionAttachment newPermissionAttachment){
        if(!permissions.containsKey(player.getUniqueId())){
            put(player.getUniqueId(), newPermissionAttachment);
        }
    }

    public void update(Player player, PermissionAttachment newPermissionAttachment){
        checkExists(player, newPermissionAttachment);
        permissions.replace(player.getUniqueId(), newPermissionAttachment);
    }

    public void add(Player player, String permission){
        set(player, permission, true);
    }

    public void set(Player player, String permission, boolean value){
        update(player, new PermissionUtil(get(player)).setPermission(permission, value).build());
    }

    public void remove(Player player, String permission){
        update(player, new PermissionUtil(get(player)).unsetPermission(permission).build());
    }

    public void removeAll(Player player){
        permissions.remove(player.getUniqueId());
    }

    public PermissionAttachment get(Player player){
        return permissions.get(player.getUniqueId());
    }

    public Map<UUID, PermissionAttachment> getPermissions() {
        return permissions;
    }

    private static class PermissionUtil {

        private final PermissionAttachment permission;

        public PermissionUtil(PermissionAttachment permission) {
            this.permission = permission;
        }

        public PermissionUtil setPermission(String permission, boolean value){
            this.permission.setPermission(permission, value);
            return this;
        }

        public PermissionUtil setPermission(Permission permission, boolean value){
            this.permission.setPermission(permission, value);
            return this;
        }

        public PermissionUtil unsetPermission(String permission){
            this.permission.unsetPermission(permission);
            return this;
        }
        public PermissionUtil unsetPermission(Permission permission){
            this.permission.unsetPermission(permission);
            return this;
        }

        public PermissionAttachment build(){
            return this.permission;
        }

    }
}
