package net.valneas.account.util;

import com.mongodb.MongoClient;
import net.valneas.account.AccountManager;
import net.valneas.account.AccountSystem;
import org.bukkit.entity.Player;

public class MongoUtil {

    private final AccountSystem main;
    private final MongoClient mongo;

    public MongoUtil(AccountSystem main) {
        this.main = main;
        this.mongo = main.getMongo().getMongoClient();
    }

    public AccountManager getAccountManager(Player player){
        return new AccountManager(main, player);
    }
}
