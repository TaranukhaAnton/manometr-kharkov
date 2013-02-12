package com.sp.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "map_user_account")
public class MapUserAccount extends BaseModel {
    @Column(name = "account_id")
    private int accountId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private SecurityUser securityUser;

    @Transient
    private boolean selected;

    public void copyTo(MapUserAccount copy) {
        super.copyTo(copy);
        copy.setSecurityUser(securityUser);
        copy.setAccountId(accountId);
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public SecurityUser getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }
}

