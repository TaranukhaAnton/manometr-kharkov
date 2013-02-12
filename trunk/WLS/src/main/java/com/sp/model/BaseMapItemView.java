package com.sp.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 01.07.2009
 * Time: 15:57:44
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public class BaseMapItemView extends BaseModel {
    @Column(name = "group_id")
    private int groupId;

    @Column(name = "unit_id")
    private int unitId;

     public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public void copyTo(BaseMapItemView copy){
        super.copyTo(copy);
        copy.groupId = groupId;
        copy.unitId = unitId;
    }

}
