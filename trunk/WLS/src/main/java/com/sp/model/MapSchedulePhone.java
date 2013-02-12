package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 16.04.2009
 * Time: 10:50:05
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "map_schedule_phone")
public class MapSchedulePhone extends BaseModel {
    @Column(name = "phone_id")
    private int phoneId;

    @Column(name = "schedule_id")
    private int scheduleId;

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
    }

    public void copyTo(MapSchedulePhone copy) {
        super.copyTo(copy);
        copy.phoneId = phoneId;
        copy.scheduleId = scheduleId;
    }
}
