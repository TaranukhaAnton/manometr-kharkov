package com.sp.dto.report;

import com.sp.util.Constants;

import java.util.Date;

/**
 */
public class TimeSheetJourneyRecord {
    private String vehReg;
    private Date date;
    private Date startTime;
    private Date endTime;
    private int duration;
    private int travelledDuration;
    private int stoppedDuration;
    private int travelledDistance;
    private String weekday;
    private int unitId;

    private Constants.TotalTypes totalType = Constants.TotalTypes.None;

    public String getVehReg() {
        return vehReg;
    }

    public void setVehReg(String vehReg) {
        this.vehReg = vehReg;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStoppedDuration() {
        return stoppedDuration;
    }

    public void setStoppedDuration(int stoppedDuration) {
        this.stoppedDuration = stoppedDuration;
    }

    public int getTravelledDuration() {
        return travelledDuration;
    }

    public void setTravelledDuration(int travelledDuration) {
        this.travelledDuration = travelledDuration;
    }

    public Constants.TotalTypes getTotalType() {
        return totalType;
    }

    public void setTotalType(Constants.TotalTypes totalType) {
        this.totalType = totalType;
    }

    public void setTravelledDistance(int travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public int getTravelledDistance() {
        return travelledDistance;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getWeekday() {
        return weekday;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

}
