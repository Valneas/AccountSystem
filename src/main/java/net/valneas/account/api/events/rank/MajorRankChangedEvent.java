package net.valneas.account.api.events.rank;

import net.valneas.account.AccountManager;
import net.valneas.account.api.events.AccountEvent;
import net.valneas.account.rank.RankUnit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class MajorRankChangedEvent extends AccountEvent {

    private static final HandlerList handlerList = new HandlerList();
    private final RankUnit previousMajorRank;
    private final RankUnit newMajorRank;
    private final CommandSender sender;

    public MajorRankChangedEvent(AccountManager account, RankUnit previousMajorRank, RankUnit newMajorRank, CommandSender sender) {
        super(account);
        this.previousMajorRank = previousMajorRank;
        this.newMajorRank = newMajorRank;
        this.sender = sender;
    }

    public RankUnit getPreviousMajorRank() {
        return previousMajorRank;
    }

    public RankUnit getNewMajorRank() {
        return newMajorRank;
    }

    public CommandSender getSender() {
        return sender;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
