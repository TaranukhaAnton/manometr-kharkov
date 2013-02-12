package com.sp.model;

import com.sp.util.Util;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class DailyLogRecordDto {
    private BaseVehicle vehicle;

    private Date day;

    private Date startDate;

    private Date endDate;

    private int drivingTime;

    private int journeyCount;

    public BaseVehicle getVehicle() {
        return vehicle;
    }

    public String getVehicleRegistrationNumber() {
        return vehicle.getRegistrationNumber();
    }

    public void setVehicle(BaseVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDrivingTime() {
        return drivingTime;
    }

    public String getDrivingTimeStr() {
        return Util.getTimeDelayStr(drivingTime);
    }

    public String getTotalTimeStr() {
        return Util.getTimeDelayStr((endDate.getTime() - startDate.getTime()) / 1000);
    }

    public String getStoppedTimeStr() {
        return Util.getTimeDelayStr((endDate.getTime() - startDate.getTime()) / 1000 - drivingTime);
    }

    public void setDrivingTime(int drivingTime) {
        this.drivingTime = drivingTime;
    }

    public int getJourneyCount() {
        return journeyCount;
    }

    public void setJourneyCount(int journeyCount) {
        this.journeyCount = journeyCount;
    }
}
