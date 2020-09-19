package net.valneas.account.api.commands;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.valneas.account.AccountManager;
import net.valneas.account.AccountSystem;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

/*
 * This class is used by the 'account' command in game.
 * This one allows us to manage every accounts in the database.
 * The sender will be able to see every accounts with pages system to avoid
 * big messages. Also he will be able to manage them, I mean edit every things,
 * even several accounts at the same time.
 */

/*
    /account:

    First subcommand: [help]
    Second subcommand: [show] [name/uuid/ObjectID(db)/*(all)] [if * -> how much to show ?/max to show everything]
    Third subcommand: [set] [name/uuid/ObjectID(db)/*(all)] [something in the object] [value]

 */
public class AccountCommand implements CommandExecutor {

    private final AccountSystem main;

    public AccountCommand(AccountSystem main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        /*
         * The console and the player are separated, why ?
         * Because there are too much differences in the code
         * between them. I thought it would be better to
         * separate them instead of make condition everytime.
         */

        /*
         * This the player part of the code.
         */
        if(sender instanceof Player){
            final Player player = (Player) sender;
            final AccountManager accountManager = new AccountManager(main, player);
//            final Rank rank = accountManager.newRankManager();

            /*
               Checking if the player is worthy (*laugh*) of
               manage accounts
             */
            if(!accountManager.getAccount().containsKey("super-user") || !accountManager.getAccount().getBoolean("super-user")){
                player.sendMessage(ChatColor.RED + "Erreur : Vous n'avez pas la permission.");
                return true;
            }

            /*
                No argument.
             */
            if(args.length == 0){
                player.sendMessage(getHelpMessage());
                return true;
            }

            if(args[0].equalsIgnoreCase("help")){
                /*
                    The help argument.
                */
                player.sendMessage(getHelpMessage());
            }else if(args[0].equalsIgnoreCase("show")){
                /*
                    The show argument.
                 */

                if(args.length > 1){
                    if(args[1].equals("*") ? args.length > 3 : args.length > 2){
                        player.sendMessage(getHelpMessage());
                        return true;
                    }

                    /*
                        Final variables used.
                     */
                    final MongoClient mongo = main.getMongo().getMongoClient();/*The mongo client to get info in the db*/
                    final MongoDatabase database = mongo.getDatabase(main.getConfig().getString("mongodb.database")); /*The db*/
                    final MongoCollection<Document> accounts = database.getCollection("accounts");/*The collection of every accounts*/

                    final String secondArgType = getSecondArgType(args[1]);/*See the method getSecondArgType below*/
                    if (secondArgType != null) {
                        switch (secondArgType){

                            /*
                                If the args[1] given is a name.
                             */
                            case "name":
                                final Document byName = main.getMongoUtil().getByName(args[1]);/*The document of the asked account*/
                                player.spigot().sendMessage(getShowMessage(byName));
                                break;

                            /*
                                If the args[1] given is an uuid.
                             */
                            case "uuid":
                                final Document byUUID = main.getMongoUtil().getByUUID(args[1]);
                                player.spigot().sendMessage(getShowMessage(byUUID));
                                break;

                            /*
                                If the args[1] given is an objectID.
                             */
                            case "ObjectID":
                                final Document byObjectID = main.getMongoUtil().getByObjectID(args[1]);
                                player.spigot().sendMessage(getShowMessage(byObjectID));
                                break;

                            /*
                                If the args[1] given is *.
                             */
                            case "*":
                                long i = 0;
                                long limit = 0;

                                try{
                                    limit = args[2].equalsIgnoreCase("max") ? accounts.countDocuments() : Long.parseLong(args[2]);
                                } catch (Exception e){
                                    player.sendMessage(ChatColor.RED + "Erreur : La limite demandée n'est pas un nombre.");
                                    return true;
                                }

                                TextComponent message = new TextComponent("Comptes :\n\n");
                                message.setColor(net.md_5.bungee.api.ChatColor.BLUE);

                                for (Document document : accounts.find()) {
                                    i++;
                                    if(i < limit){
                                       TextComponent msg = new TextComponent(ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "" + ChatColor.BOLD + document.getString("name") + "\n");
                                       msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.AQUA + "Cliquez pour en voir plus.").create()));
                                       msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/account show " + document.getObjectId("_id")));
                                       message.addExtra(msg);
                                    }
                                }

                                message.addExtra("\n" + ChatColor.GRAY + "" + i + " compte(s) affiché(s).");
                                player.spigot().sendMessage(message);
                                break;

                            default: break;
                        }
                    }else {
                        player.sendMessage(ChatColor.RED + "Une erreur s'est produite.");
                    }
                }
            }else if(args[0].equalsIgnoreCase("set")){
                /*
                    The set argument.
                 */

                player.sendMessage(ChatColor.RED + "Erreur : Commande désactivée, désolé Billy...");
                return true;

//                if(args.length > 3) {
//                    if (args.length > 4) {
//                        player.sendMessage(getHelpMessage());
//                        return true;
//                    }
//
//                    /*
//                        Final variables used.
//                     */
//                    final MongoClient mongo = main.getMongo().getMongoClient();/*The mongo client to get info in the db*/
//                    final MongoDatabase database = mongo.getDatabase(main.getConfig().getString("mongodb.database")); /*The db*/
//                    final MongoCollection<Document> accounts = database.getCollection("accounts");/*The collection of every accounts*/
//
//                    final String secondArgType = getSecondArgType(args[1]);/*See the method getSecondArgType below*/
//
//                    final String key = args[2];
//
//                    if(key.equalsIgnoreCase("uuid") || key.equalsIgnoreCase("_id")){
//                        player.sendMessage(ChatColor.RED + "Erreur : Vous ne pouvez pas modifier cette valeur");
//                        return true;
//                    }
//
//                    final String value = args[3];
//                    if (secondArgType != null) {
//                        switch (secondArgType) {
//
//                            /*
//                                If the args[1] given is a name.
//                             */
//                            case "name":
//                                final Document oldByName = main.getMongoUtil().getByName(args[1]);/*The document of the asked account*/
//                                final Document byName = main.getMongoUtil().getByName(args[1]);/*The document of the asked account*/
//
//                                try{
//                                    byName.replace(key, value);
//                                    accounts.findOneAndReplace(oldByName, byName);
//                                } catch (Exception e){
//                                    player.sendMessage(ChatColor.RED + "Une erreur s'est produite, vérifiez votre commande.");
//                                }
//
//                                player.sendMessage(ChatColor.AQUA + "Modifié avec succès !");
//                                break;
//
//                           /*
//                                If the args[1] given is an uuid.
//                             */
//                            case "uuid":
//                                final Document oldByUUID = main.getMongoUtil().getByUUID(args[1]);/*The document of the asked account*/
//                                final Document byUUID = main.getMongoUtil().getByUUID(args[1]);/*The document of the asked account*/
//
//                                try{
//                                    byUUID.replace(key, value);
//                                    accounts.findOneAndReplace(oldByUUID, byUUID);
//                                } catch (Exception e){
//                                    player.sendMessage(ChatColor.RED + "Une erreur s'est produite, vérifiez votre commande.");
//                                }
//
//                                player.sendMessage(ChatColor.AQUA + "Modifié avec succès !");
//                                break;
//
//                            /*
//                                If the args[1] given is an objectID.
//                             */
//                            case "ObjectID":
//                                final Document oldByObjectID = main.getMongoUtil().getByObjectID(args[1]);/*The document of the asked account*/
//                                final Document byObjectID = main.getMongoUtil().getByObjectID(args[1]);/*The document of the asked account*/
//
//                                try{
//                                    byObjectID.replace(key, value);
//                                    accounts.findOneAndReplace(oldByObjectID, byObjectID);
//                                } catch (Exception e){
//                                    player.sendMessage(ChatColor.RED + "Une erreur s'est produite, vérifiez votre commande.");
//                                }
//
//                                player.sendMessage(ChatColor.AQUA + "Modifié avec succès !");
//                                break;
//
//                            /*
//                                If the args[1] given is *.
//                             */
//                            case "*":
//
//                                for (Document document : accounts.find()) {
//                                    try{
//                                        Document oldDocument = document;
//                                        document.replace(key, value);
//                                        accounts.findOneAndReplace(oldDocument, document);
//                                    } catch (Exception e){
//                                        player.sendMessage(ChatColor.RED + "Une erreur s'est produite, vérifiez votre commande.");
//                                    }
//                                }
//
//                                player.sendMessage(ChatColor.AQUA + "Tous les documents ont été modifiés avec succès !");
//                                break;
//
//                            default: break;
//                        }
//                    }
//                }
            }
        }else if(sender instanceof ConsoleCommandSender){
            /*The console part of the code.*/
            sender.sendMessage(ChatColor.RED + "Erreur : Vous n'avez pas la permission (T'as acces a la db...)");
        }else{
            /*And there is the command block part of the code, to avoid every problem.*/
            sender.sendMessage(ChatColor.RED + "Erreur : Vous n'avez pas la permission.");
        }
        return false;
    }

    /**
     * Method to get the help message of the command.
     * @return the command's help message
     */
    private String getHelpMessage(){
        return ChatColor.BLUE + "Account " + ChatColor.DARK_GRAY + "§l➮ " + ChatColor.AQUA + "Aide :\n" +
                ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/" + ChatColor.AQUA + "account [help] : " + ChatColor.GRAY + "Renvois ce message.\n" +
                ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/" + ChatColor.AQUA + "account [show] [name (pseudo)/uuid/ObjectID(db)/* (tous les comptes)] [si * -> en montrer combien ?/max pour tout montrer] : "
                + ChatColor.GRAY + "Voir le compte de quelqu'un.\n" +
                ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + "/" + ChatColor.AQUA + "account [set] [name (pseudo)/uuid/ObjectID(db)/* (tous les comptes)] [Element du/des compte(s)] [valeur] : " +
                ChatColor.GRAY + "Modifier une valeur d'un compte.\n";
    }

    /**
     * Check the type of the arg given in parameter.
     * @param arg : The arg to check
     * @return The type of the arg given.
     */
    private String getSecondArgType(String arg){
        if(arg.equals("*")){
            return "*";
        }

        final MongoClient mongo = main.getMongo().getMongoClient();
        final MongoDatabase database = mongo.getDatabase(main.getConfig().getString("mongodb.database"));
        final MongoCollection<Document> accounts = database.getCollection("accounts");

        if (AccountManager.existsByName(arg)) {
            return "name";
        }else if(AccountManager.existsByUUID(arg)){
            return "uuid";
        }else {
            for (Document account : accounts.find()) {
                if(account.getObjectId("_id").toString().equals(arg)){
                    return "ObjectID";
                }
            }
        }
        return null;
    }

    /**
     * Get the message for the 'show' arg.
     * @param account : The account who contains things to get.
     * @return The message to send to the player.
     */
    private TextComponent getShowMessage(Document account){
        TextComponent message = new TextComponent(ChatColor.BLUE + "Compte de " + ChatColor.BOLD +
                account.getString("name") + "§r §9:\n\n");/*The beginning of the message*/

        for (Map.Entry<String, Object> entry : account.entrySet()) {/*Get every fields in the document*/
            final String key = entry.getKey();
            final Object value = entry.getValue();

            TextComponent msg;
            if(key.contains("ip") && !key.equals("zip")){
                msg = new TextComponent(ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + key +
                        ChatColor.DARK_GRAY + " > ");
                TextComponent ip = new TextComponent(ChatColor.GRAY + "[CACHE(E)]");
                ip.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder(ChatColor.GRAY + String.valueOf(value)).create()));
                msg.addExtra(ip);
            }else{
                msg = new TextComponent(ChatColor.DARK_GRAY + "- " + ChatColor.GRAY + key +
                        ChatColor.DARK_GRAY + " > " + ChatColor.AQUA + (value == null ? "null" : String.valueOf(value)));
            }


            TextComponent edit = new TextComponent(" §7[" + ChatColor.BLUE + "Modifier§7]️️\n");
            edit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(ChatColor.GRAY + "Clique pour modifier cette valeur").create()));
            edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
                    "/account set " + account.getObjectId("_id") + " " + key + " "));
            msg.addExtra(edit);
            if(!key.equals("super-user")){
                message.addExtra(msg);
            }
        }
        return message;
    }
}
