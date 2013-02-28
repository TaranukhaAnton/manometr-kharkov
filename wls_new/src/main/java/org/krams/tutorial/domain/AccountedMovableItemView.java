package org.krams.tutorial.domain;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 26.06.2009
 * Time: 10:09:13
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public class AccountedMovableItemView extends BaseMovableItemView {
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private BaseAccount account;


    public void copyTo(AccountedMovableItemView copy) {
        super.copyTo(copy);
        if (account != null) {
            BaseAccount acc = new BaseAccount();
            account.copyTo(acc);
            copy.setAccount(acc);
        }
    }

    public BaseAccount getAccount() {
        return account;
    }

    public void setAccount(BaseAccount account) {
        this.account = account;
    }
}
