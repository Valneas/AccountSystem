package net.valneas.account.rank;

import net.valneas.account.AccountManager;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Rank {

    private final AccountManager accountManager;

    public Rank(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void setMajorRank(RankUnit rank){
        if(rank == null) return;
        if(!accountManager.hasAnAccount()) return;

        Document doc = accountManager.getAccount();
        doc.replace("major-rank", rank.getId());
        accountManager.update(doc);
    }

    public void addRank(RankUnit rank) {
        if (rank == null) return;
        if (!accountManager.hasAnAccount()) return;
        if (!hasMajorRank()) return;
        if (hasRank(rank)) return;

        Document doc = accountManager.getAccount();
        final List<Integer> ranks = doc.getList("ranks", Integer.class);
        ranks.add(rank.getId());
        doc.replace("ranks", ranks);
        accountManager.update(doc);

    }

    public void removeRank(RankUnit rank) {
        if (rank == null) return;
        if (!accountManager.hasAnAccount()) return;
        if (!hasMajorRank()) return;
        if (!hasRanks()) return;
        if (!hasRank(rank)) return;

        Document doc = accountManager.getAccount();
        final List<Integer> ranks = doc.getList("ranks", Integer.class);
        ranks.remove(Integer.valueOf(rank.getId()));
        doc.replace("ranks", ranks);
        accountManager.update(doc);
    }

    public boolean hasRank(RankUnit rank){
        if(rank == null) return false;
        if(!accountManager.hasAnAccount()) return false;
        if(!hasMajorRank()) return false;
        if(!hasRanks()) return false;

        return accountManager.getAccount().getList("ranks", Integer.class).contains(rank.getId());
    }

    public void setRanks(RankUnit...ranks){
        if(ranks == null) return;
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
        if(!accountManager.hasAnAccount()) return false;
        return accountManager.getAccount().get("major-rank") != null;
    }

    public boolean hasRanks(){
        if(!accountManager.hasAnAccount()) return false;
        return accountManager.getAccount().get("ranks") != null;
    }

    public RankUnit getMajorRank(){
        if(!accountManager.hasAnAccount()) return RankUnit.JOUEUR;
        if(!hasMajorRank()) return RankUnit.JOUEUR;

        return RankUnit.getById(accountManager.getAccount().getInteger("major-rank"));
    }

    public List<RankUnit> getRanks(){
        if(!accountManager.hasAnAccount()) return null;
        if(!hasRanks()) return null;
        final List<Integer> ranksId = accountManager.getAccount().getList("ranks", Integer.class);
        final List<RankUnit> ranks = new ArrayList<>();
        ranksId.forEach(id -> ranks.add(RankUnit.getById(id)));
        return ranks;
    }
}
