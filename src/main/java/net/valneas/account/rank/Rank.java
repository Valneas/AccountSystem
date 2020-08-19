package net.valneas.account.rank;

import net.valneas.account.AccountManager;
import net.valneas.account.api.events.rank.MajorRankChangedEvent;
import net.valneas.account.api.events.rank.RankAddedEvent;
import net.valneas.account.api.events.rank.RankRemovedEvent;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rank {

    private final AccountManager accountManager;

    public Rank(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void setMajorRank(RankUnit rank){
        setMajorRank(rank, null, false);
    }

    public void setMajorRank(RankUnit rank, @Nullable CommandSender sender, boolean event){
        if(rank == null) {
            return;
        }else if(!accountManager.hasAnAccount()) {
            return;
        }

        RankUnit previousMajorRank = null;
        if(event){
            previousMajorRank = getMajorRank();
        }

        Document doc = accountManager.getAccount();
        doc.replace("major-rank", rank.getId());
        accountManager.update(doc);

        if(event){
            if(previousMajorRank != null){
                Bukkit.getPluginManager().callEvent(new MajorRankChangedEvent(accountManager, previousMajorRank, rank, sender));
            }
        }
    }

    public void addRank(RankUnit rank) {
        addRank(rank, null, false);
    }

    public void addRank(RankUnit rank, @Nullable CommandSender sender, boolean event){
        if (rank == null) {
            return;
        }else if (!accountManager.hasAnAccount()) {
            return;
        }else if (!hasMajorRank()) {
            return;
        }else if (hasRank(rank)) {
            return;
        }

        Document doc = accountManager.getAccount();
        final List<Integer> ranks = doc.getList("ranks", Integer.class);
        ranks.add(rank.getId());
        doc.replace("ranks", ranks);
        accountManager.update(doc);

        if(event) {
            System.out.println("AccountManager : " + accountManager);
            System.out.println("Rank : " + rank);
            Bukkit.getPluginManager().callEvent(new RankAddedEvent(accountManager, rank, sender));
        }
    }

    public void removeRank(RankUnit rank) {
        removeRank(rank, null, false);
    }

    public void removeRank(RankUnit rank, @Nullable CommandSender sender, boolean event){
        if (rank == null) {
            return;
        }else if (!accountManager.hasAnAccount()) {
            return;
        }else if (!hasMajorRank()) {
            return;
        }else if (!hasRanks()) {
            return;
        }else if (!hasRank(rank)) {
            return;
        }

        Document doc = accountManager.getAccount();
        final List<Integer> ranks = doc.getList("ranks", Integer.class);
        ranks.remove(Integer.valueOf(rank.getId()));
        doc.replace("ranks", ranks);
        accountManager.update(doc);

        if(event){
            Bukkit.getPluginManager().callEvent(new RankRemovedEvent(accountManager, rank, sender));
        }
    }

    public boolean hasRank(RankUnit rank){
        if(rank == null) {
            return false;
        }else if(!accountManager.hasAnAccount()) {
            return false;
        } else if(hasMajorRank() && getMajorRank().equals(rank)) {
            return true;
        }else if(!hasRanks()) {
            return false;
        }

        return getRanks().contains(rank);
    }

    public boolean hasAtLeast(RankUnit rank){
        if(rank == null){
            return false;
        }else if(!accountManager.hasAnAccount()){
            return false;
        }else if(hasMajorRank() && getMajorRank().getPower() <= rank.getPower()){
            return true;
        }else if(!hasRanks()){
            return false;
        }

        return getRanks().stream().anyMatch(r -> r.getPower() <= rank.getPower());
    }

    public void setRanks(RankUnit...ranks){
        if(ranks == null) {
            return;
        }
        List<Integer> ranksId = new ArrayList<>();
        for (RankUnit rank : ranks) {
            ranksId.add(rank.getId());
        }
        setRanks(ranksId);
    }

    public void setRanks(List<Integer> ranks){
        if(ranks == null) return;
        if(!accountManager.hasAnAccount()) return;
        if(!hasMajorRank()) return;

        Document doc = accountManager.getAccount();
        doc.replace("ranks", ranks);
        accountManager.update(doc);
    }


    public boolean hasMajorRank(){
        if(!accountManager.hasAnAccount()) {
            return false;
        }
        return accountManager.getAccount().get("major-rank") != null;
    }

    public boolean hasRanks(){
        if(!accountManager.hasAnAccount()) {
            return false;
        }
        return accountManager.getAccount().get("ranks") != null;
    }

    public RankUnit getMajorRank(){
        if(!accountManager.hasAnAccount()) {
            return null;
        }else if(!hasMajorRank()) {
            return null;
        }

        return RankUnit.getById(accountManager.getAccount().getInteger("major-rank"));
    }

    public List<RankUnit> getRanks(){
        if(!accountManager.hasAnAccount()) {
            return null;
        }else if(!hasRanks()) {
            return null;
        }
        final List<Integer> ranksId = accountManager.getAccount().getList("ranks", Integer.class);
        Collections.sort(ranksId);
        final List<RankUnit> ranks = new ArrayList<>();
        ranksId.forEach(id -> ranks.add(RankUnit.getById(id)));
        return ranks;
    }
}
