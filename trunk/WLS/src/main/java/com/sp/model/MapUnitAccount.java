package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "map_unit_account")
public class MapUnitAccount extends BaseModel {
    @Column(name = "account_id")
    private int accountId;

    @Column(name = "unit_id")
    private int vrehicleId;

    public void copyTo(MapUnitAccount copy) {
        super.copyTo(copy);
        copy.accountId = accountId;
        copy.vrehicleId = vrehicleId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getVrehicleId() {
        return vrehicleId;
    }

    public void setVrehicleId(int vrehicleId) {
        this.vrehicleId = vrehicleId;
    }
}
