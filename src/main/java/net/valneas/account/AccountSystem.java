package net.valneas.account;

import net.valneas.account.api.commands.AccountCommand;
import net.valneas.account.api.commands.PermissionCommand;
import net.valneas.account.api.commands.RankCommand;
import net.valneas.account.listener.*;
import net.valneas.account.mongo.Mongo;
import net.valneas.account.rank.permission.PermissionManager;
import net.valneas.account.util.MongoUtil;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class AccountSystem extends JavaPlugin {

    private final Mongo mongo = new Mongo(this);
    private final MongoUtil mongoUtil = new MongoUtil(this);
    private final PermissionManager permissionManager = new PermissionManager(mongo.getMongoClient());

    /**
     * Plugin initialization
     */
    @Override
    public void onEnable() {
        saveDefaultConfig();

        registerEvents();
        getCommand("permission").setExecutor(new PermissionCommand());
        getCommand("rank").setExecutor(new RankCommand(this));
        getCommand("account").setExecutor(new AccountCommand(this));
        getLogger().info("Enabled!");
    }

    /**
     * Registering events
     */
    private void registerEvents() {
        registerListeners(
                new PlayerJoinListener(this),
                new PlayerQuitListener(this),
                new PermissionAssignedListener(),
                new PermissionUnAssignedListener(),
                new MajorRankChangedListener(this),
                new RankAddedListener(this),
                new RankRemovedListener(this)
        );
    }

    /**
     * Register a listener
     * @param listeners : Listener to register
     */
    private void registerListeners(Listener...listeners){
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    /**
     * Get a mongo class instance
     * @return mongo class instance
     */
    public Mongo getMongo() {
        return mongo;
    }

    /**
     * Get a mongo util class instance
     * @return mongo util class instance
     */
    public MongoUtil getMongoUtil() {
        return mongoUtil;
    }

    /**
     * Get a permission manager class instance
     * @return permission manager class instance
     */
    public PermissionManager getPermissionManager() {
        return permissionManager;
    }
}
