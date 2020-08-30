package net.valneas.account.rank;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum RankUnit {

    ADMIN(1, 1, "Admin", "§4@Admin ", "", ChatColor.DARK_RED),
    HEAD(2, 2, "Head", "@Head-", "", ChatColor.BOLD),
    DEVELOPPEUR(3, 3, "Développeur", "§c§o@Développeur ", "", ChatColor.RED),
    DEVELOPPEUSE(4, 4, "Développeuse", "§c§o@Développeuse ", "", ChatColor.RED),
    BUILDER(5, 5, "Builder", "§b@Builder ", "", ChatColor.AQUA),
    BUILDEUSE(6, 6, "Buildeuse", "§b@Buildeuse ", "", ChatColor.AQUA),
    MODERATEUR(7, 7, "Modérateur", "§2@Modérateur ", "", ChatColor.DARK_GREEN),
    MODERATRICE(8, 8, "Modératrice", "§2@Modératrice ", "", ChatColor.DARK_GREEN),
    ASSISTANT(9, 9, "Assistant", "§a@Assistant ", "", ChatColor.GREEN),
    ASSISTANTE(10, 10, "Assistante", "§a@Assistante ", "", ChatColor.GREEN),
    STAFF(11, 15, "Staff", "§d§oStaff ", "", ChatColor.LIGHT_PURPLE),
    PARTENAIRE(12, 11, "Partenaire", "§6@Partenaire ", "", ChatColor.GOLD),
    YOUTUBE(13, 12, "YouTube", "§c§n@YouTube ", "", ChatColor.RED),
    TWITCH(14, 13, "Twitch", "§5§n@Twitch ", "", ChatColor.DARK_PURPLE),
    JOUEUR(15, 14, "Joueur", "§7", "", ChatColor.GRAY);

    private final String name, prefix, suffix;
    private final int power, id;
    private final ChatColor color;

    RankUnit(int power, int id, String name, String prefix, String suffix, ChatColor color){
        this.power = power;
        this.id = id;
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public int getPower() {
        return power;
    }

    public int getId() {
        return id;
    }

    public ChatColor getColor() {
        return color;
    }

    public static RankUnit getByPower(int power){
        return Arrays.stream(RankUnit.values()).filter(r -> r.getPower() == power).findFirst().orElse(null);
    }

    public static RankUnit getById(int id){
        return Arrays.stream(RankUnit.values()).filter(r -> r.getId() == id).findFirst().orElse(null);
    }

    public static RankUnit getByName(String name){
        return Arrays.stream(RankUnit.values()).filter(r -> r.getName().equals(name)).findFirst().orElse(null);
    }

    public static RankUnit getByCommandArg(String arg){
        try{
            int i = Integer.parseInt(arg);
            return getById(i);
        } catch (Exception e){
            return getByName(arg);
        }
    }

    public static String getRankList(){
        List<String> ranks = new ArrayList<>();

        for (RankUnit rank : values()) {
            ranks.add(rank.getColor() + rank.getName());
        }

        return String.join("§f, ", ranks);
    }
}
