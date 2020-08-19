package net.valneas.account.api.events.rank;

import net.valneas.account.AccountManager;
import net.valneas.account.api.events.AccountEvent;
import net.valneas.account.rank.RankUnit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class RankAddedEvent extends AccountEvent {

    private static final HandlerList handlerList = new HandlerList();
    private final RankUnit rankAdded;
    private final CommandSender sender;

    public RankAddedEvent(AccountManager account, RankUnit rankAdded, CommandSender sender) {
        super(account);
        this.rankAdded = rankAdded;
        this.sender = sender;
    }

    public RankUnit getRankAdded() {
        return rankAdded;
    }

    public CommandSender getSender() {
        return sender;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
