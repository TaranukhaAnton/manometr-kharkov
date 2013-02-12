package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_unit_disabling")
public class UserUnitDisabling extends UserDisabling {
    @Column(name = "unit_id")
    private int unitId;

    public void copyTo(UserUnitDisabling copy) {
        super.copyTo(copy);
        copy.unitId = unitId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

}
