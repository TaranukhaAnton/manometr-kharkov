package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "late_start_period")
public class LateStartPeriod extends BaseModel {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "start_time_hours")
    private short startTimeHours;

    @Column(name = "start_time_mins")
    private short startTimeMins;

    @Column(name = "end_time_hours")
    private short endTimeHours;

    @Column(name = "end_time_mins")
    private short endTimeMins;

    @Column(name = "icon_colour")
    private String iconColour;

    public void copyTo(LateStartPeriod copy) {
        super.copyTo(copy);
        copy.userId = userId;
        copy.startTimeHours = startTimeHours;
        copy.startTimeMins = startTimeMins;
        copy.endTimeHours = endTimeHours;
        copy.endTimeMins = endTimeMins;
        copy.iconColour = iconColour;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public short getStartTimeHours() {
        return startTimeHours;
    }

    public void setStartTimeHours(short startTimeHours) {
        this.startTimeHours = startTimeHours;
    }

    public short getStartTimeMins() {
        return startTimeMins;
    }

    public void setStartTimeMins(short startTimeMins) {
        this.startTimeMins = startTimeMins;
    }

    public short getEndTimeHours() {
        return endTimeHours;
    }

    public void setEndTimeHours(short endTimeHours) {
        this.endTimeHours = endTimeHours;
    }

    public short getEndTimeMins() {
        return endTimeMins;
    }

    public void setEndTimeMins(short endTimeMins) {
        this.endTimeMins = endTimeMins;
    }

    public String getIconColour() {
        return iconColour;
    }

    public void setIconColour(String iconColour) {
        this.iconColour = iconColour;
    }
}
