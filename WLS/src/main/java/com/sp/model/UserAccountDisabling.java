package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_account_disabling")
public class UserAccountDisabling extends UserDisabling {
    @Column(name = "account_id")
    private int accountId;

    public void copyTo(UserAccountDisabling copy) {
        super.copyTo(copy);
        copy.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
