package net.valneas.account.util;

import org.bukkit.entity.Player;

import java.net.InetSocketAddress;
import java.util.UUID;

public final class PlayerUtil {

    private PlayerUtil(){}

    public static String getIp(Player player){
        InetSocketAddress IPAdressPlayer = player.getAddress();
        String sfullip = IPAdressPlayer.toString();
        String[] fullip;
        String[] ipandport;
        fullip = sfullip.split("/");
        String sIpandPort = fullip[1];
        return sIpandPort;
    }

    public static boolean ArgIsAnUuid(String arg){
        try{
            UUID uuid = UUID.fromString(arg);
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
