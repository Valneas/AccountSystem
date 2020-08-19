package net.valneas.account.api.events;

import net.valneas.account.AccountManager;
import org.bukkit.event.Event;

public abstract class AccountEvent extends Event {

    protected AccountManager account;

    public AccountEvent(AccountManager account) {
        this.account = account;
    }

    public AccountEvent(boolean isAsync, AccountManager account) {
        super(isAsync);
        this.account = account;
    }

    public final AccountManager getAccount() {
        return account;
    }
}
