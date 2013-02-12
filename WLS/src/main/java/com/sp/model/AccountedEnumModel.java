package com.sp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * User: andrew
 * Date: 25.03.2010
 */
@MappedSuperclass
public class AccountedEnumModel extends EnumModel {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name = "is_deleted")
    private boolean deleted;

    @Column(name = "timestamp", insertable = false, updatable = false)
    private Date timestamp;

    public void copyTo(AccountedEnumModel copy) {
        super.copyTo(copy);
        copy.account = account;
        copy.deleted = deleted;
        copy.timestamp = timestamp;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
