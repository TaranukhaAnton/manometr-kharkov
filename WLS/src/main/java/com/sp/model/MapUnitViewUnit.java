package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg
 * Date: 01.10.12
 * Time: 11:14
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "map_unit_view_unit")
public class MapUnitViewUnit extends BaseModel {

    @Column(name = "group_id")
    private int groupId;

    @Column(name = "vehicle_id")
    private int vehicleId;

    public void copyTo(MapUnitViewUnit copy) {
        super.copyTo(copy);
        copy.groupId = groupId;
        copy.vehicleId = vehicleId;
    }


    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
}
