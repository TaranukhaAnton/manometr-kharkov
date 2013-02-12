package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_unit_view_disabling")
public class UserUnitViewDisabling extends UserDisabling {
    @Column(name = "unit_view_id")
    private int unitViewId;

    public void copyTo(UserUnitViewDisabling copy) {
        super.copyTo(copy);
        copy.unitViewId = unitViewId;
    }

    public int getUnitViewId() {
        return unitViewId;
    }

    public void setUnitViewId(int unitViewId) {
        this.unitViewId = unitViewId;
    }
}
