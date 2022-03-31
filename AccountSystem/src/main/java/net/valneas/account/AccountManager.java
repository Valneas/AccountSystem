package net.valneas.account;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.valneas.account.rank.Rank;
import net.valneas.account.rank.RankUnit;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AccountManager {

    private final AccountSystem main;
    private final MongoClient mongo;
    private final String name, uuid;

    public AccountManager(AccountSystem main, Player player) {
        this.main = main;
        this.mongo = main.getMongo().getMongoClient();
        this.name = player.getName();
        this.uuid = player.getUniqueId().toString();
    }

    public AccountManager(AccountSystem main, String name, String uuid) {
        this.main = main;
        this.mongo = main.getMongo().getMongoClient();
        this.name = name;
        this.uuid = uuid;
    }

    public static boolean existsByUUID(String uuid){
        final AccountSystem main = (AccountSystem) Bukkit.getPluginManager().getPlugin("AccountSystem");
        final MongoClient mongo = main.getMongo().getMongoClient();
        final MongoDatabase database = mongo.getDatabase(main.getConfig().getString("mongodb.database"));
        final MongoCollection<Document> accounts = database.getCollection("accounts");

        for (Document document : accounts.find()) {
            if(document.getString("uuid").equals(uuid)){
                return true;
            }
        }
        return false;
    }

    public static boolean existsByName(String name){
        final AccountSystem main = (AccountSystem) Bukkit.getPluginManager().getPlugin("AccountSystem");
        final MongoClient mongo = main.getMongo().getMongoClient();
        final MongoDatabase database = mongo.getDatabase(main.getConfig().getString("mongodb.database"));
        final MongoCollection<Document> accounts = database.getCollection("accounts");

        for (Document document : accounts.find()) {
            if(document.getString("name").equals(name)){
                return true;
            }
        }
        return false;
    }

    public static String getUuidByName(String name){
        if(!existsByName(name)) return null;

        final AccountSystem main = (AccountSystem) Bukkit.getPluginManager().getPlugin("AccountSystem");
        final MongoClient mongo = main.getMongo().getMongoClient();
        final MongoDatabase database = mongo.getDatabase(main.getConfig().getString("mongodb.database"));
        final MongoCollection<Document> accounts = database.getCollection("accounts");

        for (Document document : accounts.find()) {
            if(document.getString("name").equals(name)){
                return document.getString("uuid");
            }
        }
        return null;
    }

    public static String getNameByUuid(String uuid){
        if(!existsByUUID(uuid)) return null;

        final AccountSystem main = (AccountSystem) Bukkit.getPluginManager().getPlugin("AccountSystem");
        final MongoClient mongo = main.getMongo().getMongoClient();
        final MongoDatabase database = mongo.getDatabase(main.getConfig().getString("mongodb.database"));
        final MongoCollection<Document> accounts = database.getCollection("accounts");

        for (Document document : accounts.find()) {
            if(document.getString("uuid").equals(uuid)){
                return document.getString("name");
            }
        }
        return null;
    }

    public void createAccount(){
        if(hasAnAccount()) return;
        final MongoCollection<Document> accounts = getAccountCollection();

        Document account = new Document("name", name)
                .append("uuid", uuid)
                .append("xp", 0.0d)
                .append("level", 0.0d)
                .append("money", 0.0d)
                .append("first-connection", null)
                .append("last-connection", null)
                .append("last-disconnection", null)
                .append("last-ip", null)
                .append("major-rank", RankUnit.JOUEUR.getId())
                .append("ranks", new ArrayList<Integer>())
                .append("moderation-mod", null)
        ;
        accounts.insertOne(account);
    }

    public boolean hasAnAccount(){
        final MongoCollection<Document> accounts = getAccountCollection();

        for (Document document : accounts.find()) {
            if(document.getString("uuid").equals(uuid)){
                return true;
            }

        }
        return false;
    }

    public void update(Document newDocument){
        if(!hasAnAccount()) return;
        Document account = getAccount();
        getAccountCollection().findOneAndReplace(account, newDocument);
    }

    public void updateOnLogin(){
        if(!hasAnAccount()) return;
        Document account = getAccount();

        if(!account.getString("name").equals(name)){
            account.replace("name", name);
        }

        update(account);
    }

    public Document getAccount(){
        final MongoCollection<Document> accounts = getAccountCollection();

        for (Document document : accounts.find()) {
            if(document.getString("uuid").equals(uuid)){
                return document;
            }

        }
        return null;
    }

    public double getXp(){
        if(!hasAnAccount()){
            return 0.0d;
        }

        return getAccount().getDouble("xp");
    }

    public void setXp(double xp){
        if(!hasAnAccount()){
            return;
        }

        final Document doc = getAccount();
        doc.replace("xp", xp);
        update(doc);
    }

    public double getLevel(){
        if(!hasAnAccount()){
            return 0.0d;
        }

        return getAccount().getDouble("level");
    }

    public void setLevel(double level){
        if(!hasAnAccount()){
            return;
        }

        final Document doc = getAccount();
        doc.replace("level", level);
        update(doc);
    }

    public long getFirstConnection(){
        return getAccount().getLong("first-connection");
    }

    public void setFirstConnection(long firstConnection){
        if(!hasAnAccount()) return;
        final Document doc = getAccount();
        doc.replace("first-connection", firstConnection);
        update(doc);
    }

    public long getLastConnection(){
        return getAccount().getLong("last-connection");
    }

    public void setLastConnection(long lastConnection){
        if(!hasAnAccount()) return;
        final Document doc = getAccount();
        doc.replace("last-connection", lastConnection);
        update(doc);
    }

    public long getLastDisconnection(){
        return getAccount().getLong("last-disconnection");
    }

    public void setLastDisconnection(long lastDisconnection){
        if(!hasAnAccount()) return;
        final Document doc = getAccount();
        doc.replace("last-disconnection", lastDisconnection);
        update(doc);
    }

    public String getLastIp(){
        return getAccount().getString("last-ip");
    }

    public void setLastIp(String lastIp){
        if(!hasAnAccount()) return;
        final Document doc = getAccount();
        doc.replace("last-ip", lastIp);
        update(doc);
    }

    public boolean getModerationMod(){
        return getAccount().getBoolean("moderation-mod");
    }

    public void setModerationMod(boolean moderationMod){
        if(!hasAnAccount()) return;
        final Document doc = getAccount();
        doc.replace("moderation-mod", moderationMod);
        update(doc);
    }

    private MongoCollection<Document> getAccountCollection(){
        return getDatabase().getCollection("accounts");
    }

    private MongoDatabase getDatabase(){
        return mongo.getDatabase(main.getConfig().getString("mongodb.database"));
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public Rank newRankManager(){
        return new Rank(this);
    }
}
