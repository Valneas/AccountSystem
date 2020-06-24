package net.valneas.account;

import net.valneas.account.api.commands.PermissionCommand;
import net.valneas.account.api.commands.RankCommand;
import net.valneas.account.events.PermissionAssignedListener;
import net.valneas.account.events.PermissionUnAssignedListener;
import net.valneas.account.events.PlayerJoinListener;
import net.valneas.account.events.PlayerQuitListener;
import net.valneas.account.mongo.Mongo;
import net.valneas.account.rank.permission.PermissionManager;
import net.valneas.account.util.MongoUtil;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class AccountSystem extends JavaPlugin {

    private final Mongo mongo = new Mongo(this);
    private final MongoUtil mongoUtil = new MongoUtil(this);
    private final PermissionManager permissionManager = new PermissionManager(mongo.getMongoClient());

    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerEvents();
        getCommand("permission").setExecutor(new PermissionCommand());
        getCommand("rank").setExecutor(new RankCommand(this));
        getLogger().info("Enabled!");
    }

    private void registerEvents() {
        registerListeners(
                new PlayerJoinListener(this),
                new PlayerQuitListener(this),
                new PermissionAssignedListener(),
                new PermissionUnAssignedListener()
        );
    }

    private void registerListeners(Listener...listeners){
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public Mongo getMongo() {
        return mongo;
    }

    public MongoUtil getMongoUtil() {
        return mongoUtil;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }
}
