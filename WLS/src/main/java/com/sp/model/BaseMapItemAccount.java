package com.sp.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 01.07.2009
 * Time: 10:17:13
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public class BaseMapItemAccount extends BaseModel {
    @Column(name = "account_id")
    private int accountId;

    @Column(name = "unit_id")
    private int unitId;

    public void copyTo(BaseMapItemAccount copy) {
        super.copyTo(copy);
        copy.accountId = accountId;
        copy.unitId = unitId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

}
