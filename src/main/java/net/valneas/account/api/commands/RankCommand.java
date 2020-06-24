package net.valneas.account.api.commands;

import net.valneas.account.AccountManager;
import net.valneas.account.AccountSystem;
import net.valneas.account.rank.Rank;
import net.valneas.account.rank.RankUnit;
import net.valneas.account.util.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RankCommand implements CommandExecutor {

    private final AccountSystem main;

    public RankCommand(AccountSystem main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(getUsageMessage());
            return true;
        }

        if (args[0].equalsIgnoreCase("show")) {
            if (args.length > 1) {
                if (args.length >= 3) {
                    sender.sendMessage(getUsageMessage());
                    return true;
                }

                final AccountManager account;

                if (PlayerUtil.ArgIsAnUuid(args[1])) {
                    if (AccountManager.existsByUUID(args[1])) {
                        account = new AccountManager(main, AccountManager.getNameByUuid(args[1]), args[1]);
                    } else {
                        sender.sendMessage(ChatColor.RED + "Erreur : Ce compte n'existe pas.");
                        return true;
                    }
                } else {
                    if (AccountManager.existsByName(args[1])) {
                        account = new AccountManager(main, args[1], AccountManager.getUuidByName(args[1]));
                    } else {
                        sender.sendMessage(ChatColor.RED + "Erreur : Ce compte n'existe pas.");
                        return true;
                    }
                }

                if (account.hasAnAccount()) {
                    final Rank rank = account.newRankManager();

                    if (!rank.hasMajorRank() && !rank.hasRanks()) {
                        sender.sendMessage(ChatColor.YELLOW + "Michel §f➤" + ChatColor.YELLOW + " Cette personne n'a aucun rang.");
                        return true;
                    }

                    StringBuilder sb = new StringBuilder();
                    sb.append(ChatColor.YELLOW + "Michel §f➤" + ChatColor.YELLOW + "" + ChatColor.ITALIC + " Les rangs de §r" + ChatColor.WHITE + ""
                            + ChatColor.BOLD + ChatColor.UNDERLINE + account.getName() + "§r" +
                            ChatColor.YELLOW + "§o sont :\n");
                    if (rank.hasMajorRank()) {
                        sb.append("§f- " + ChatColor.GRAY + rank.getMajorRank().getName() + ChatColor.DARK_GRAY + " (" + ChatColor.GOLD + "Id §f: " + ChatColor.GOLD +
                                rank.getMajorRank().getId() + ChatColor.DARK_GRAY + ") "
                                + ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "Rang majeur" + ChatColor.DARK_GREEN + "]\n");
                    }

                    if (rank.hasRanks()) {
                        for (RankUnit r : rank.getRanks()) {
                            sb.append("§f- " + ChatColor.GRAY + r.getName() + ChatColor.DARK_GRAY + " (" + ChatColor.GOLD + "Id §f: " + ChatColor.GOLD +
                                    r.getId() + ChatColor.DARK_GRAY + ")\n");
                        }
                    }

                    sender.sendMessage(sb.toString());
                }
            } else {
                sender.sendMessage(getUsageMessage());
            }
        } else if (args[0].equalsIgnoreCase("add")) {
            if (args.length > 1) {
                if (args.length > 2) {
                    if (args.length > 3) {
                        sender.sendMessage(getUsageMessage());
                        return true;
                    }

                    final AccountManager account;

                    if (PlayerUtil.ArgIsAnUuid(args[1])) {
                        if (AccountManager.existsByUUID(args[1])) {
                            account = new AccountManager(main, AccountManager.getNameByUuid(args[1]), args[1]);
                        } else {
                            sender.sendMessage(ChatColor.RED + "Erreur : Ce compte n'existe pas.");
                            return true;
                        }
                    } else {
                        if (AccountManager.existsByName(args[1])) {
                            account = new AccountManager(main, args[1], AccountManager.getUuidByName(args[1]));
                        } else {
                            sender.sendMessage(ChatColor.RED + "Erreur : Ce compte n'existe pas.");
                            return true;
                        }
                    }

                    if (account.hasAnAccount()) {
                        final Rank rank = account.newRankManager();

                        final RankUnit rankUnit = RankUnit.getByCommandArg(args[2]);
                        if (rankUnit == null) {
                            sender.sendMessage("Le rang ne peut pas etre null !\nVoici la liste des rangs :\n" + RankUnit.getRankList());
                            return true;
                        }

                        if (rank.getMajorRank().equals(rankUnit) || rank.hasRank(rankUnit)) {
                            sender.sendMessage(ChatColor.RED + "Erreur : Cette personne a déjà le rang.");
                            return true;
                        }

                        rank.addRank(rankUnit);
                        sender.sendMessage(ChatColor.AQUA + "Rang ajouté !");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Erreur : Ce compte n'existe pas.");
                        return true;
                    }
                } else {
                    sender.sendMessage(getUsageMessage());
                }
            } else {
                sender.sendMessage(getUsageMessage());
            }
        } else if (args[0].equalsIgnoreCase("remove")) {
            if (args.length > 1) {
                if (args.length > 2) {
                    if (args.length > 3) {
                        sender.sendMessage(getUsageMessage());
                        return true;
                    }

                    final AccountManager account;

                    if (PlayerUtil.ArgIsAnUuid(args[1])) {
                        if (AccountManager.existsByUUID(args[1])) {
                            account = new AccountManager(main, AccountManager.getNameByUuid(args[1]), args[1]);
                        } else {
                            sender.sendMessage(ChatColor.RED + "Erreur : Ce compte n'existe pas.");
                            return true;
                        }
                    } else {
                        if (AccountManager.existsByName(args[1])) {
                            account = new AccountManager(main, args[1], AccountManager.getUuidByName(args[1]));
                        } else {
                            sender.sendMessage(ChatColor.RED + "Erreur : Ce compte n'existe pas.");
                            return true;
                        }
                    }

                    if (account.hasAnAccount()) {
                        final Rank rank = account.newRankManager();

                        final RankUnit rankUnit = RankUnit.getByCommandArg(args[2]);
                        if (rankUnit == null) {
                            sender.sendMessage("Le rang ne peut pas etre null !\nVoici la liste des rangs :\n" + RankUnit.getRankList());
                            return true;
                        }

                        if (!rank.hasMajorRank() || !rank.hasRanks() || !rank.hasRank(rankUnit)) {
                            sender.sendMessage(ChatColor.RED + "Erreur : Cette personne n'a pas le rang.");
                            return true;
                        }

                        rank.removeRank(rankUnit);
                        sender.sendMessage(ChatColor.RED + "Rang retiré !");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Erreur : Ce compte n'existe pas.");
                        return true;
                    }
                } else {
                    sender.sendMessage(getUsageMessage());
                }
            } else {
                sender.sendMessage(getUsageMessage());
            }
        } else if (args[0].equalsIgnoreCase("set")) {
            if (args.length > 1) {
                if (args.length > 2) {
                    if (args.length > 3) {
                        sender.sendMessage(getUsageMessage());
                        return true;
                    }

                    final AccountManager account;

                    if (PlayerUtil.ArgIsAnUuid(args[1])) {
                        if (AccountManager.existsByUUID(args[1])) {
                            account = new AccountManager(main, AccountManager.getNameByUuid(args[1]), args[1]);
                        } else {
                            sender.sendMessage(ChatColor.RED + "Erreur : Ce compte n'existe pas.");
                            return true;
                        }
                    } else {
                        if (AccountManager.existsByName(args[1])) {
                            account = new AccountManager(main, args[1], AccountManager.getUuidByName(args[1]));
                        } else {
                            sender.sendMessage(ChatColor.RED + "Erreur : Ce compte n'existe pas.");
                            return true;
                        }
                    }

                    if (account.hasAnAccount()) {
                        final Rank rank = account.newRankManager();

                        final RankUnit rankUnit = RankUnit.getByCommandArg(args[2]);
                        if (rankUnit == null) {
                            sender.sendMessage("Le rang ne peut pas etre null !\nVoici la liste des rangs :\n" + RankUnit.getRankList());
                            return true;
                        }

                        if (rank.getMajorRank().equals(rankUnit)) {
                            sender.sendMessage(ChatColor.RED + "Erreur : Cette personne a déjà le rang.");
                            return true;
                        }

                        rank.setMajorRank(rankUnit);
                        sender.sendMessage(ChatColor.AQUA + "Major rank changé !");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Erreur : Ce compte n'existe pas.");
                        return true;
                    }
                } else {
                    sender.sendMessage(getUsageMessage());
                }
            } else {
                sender.sendMessage(getUsageMessage());
            }
        }

        return false;
    }

    private String getUsageMessage() {
        return "/rank [add/remove/set/show] [joueur] [rang]";
    }
}
