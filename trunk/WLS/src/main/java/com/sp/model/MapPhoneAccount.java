package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "map_phone_account")
public class MapPhoneAccount extends BaseModel {
    @Column(name = "account_id")
    private int accountId;

    @Column(name = "unit_id")
    private int phoneId;

    public void copyTo(MapPhoneAccount copy) {
        super.copyTo(copy);
        copy.accountId = accountId;
        copy.phoneId = phoneId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getphoneId() {
        return phoneId;
    }

    public void setphoneId(int vrehicleId) {
        this.phoneId = vrehicleId;
    }
}
