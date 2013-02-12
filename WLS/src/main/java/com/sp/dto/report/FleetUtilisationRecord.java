package com.sp.dto.report;

import com.sp.util.TimePeriod;
import com.sp.util.Util;

/**
 * Created by Alexander
 */
public class FleetUtilisationRecord {
    private String vehReg;
    private String vehGroups;
    private String driverDescr;
    private Integer journeysCount;
    private double mileage;
    private long totalDrivingTimeSecs;
    private long totalTimeSecs;
    private long totalIdlingSecs;
    private Integer activeDays;
    private Integer fullUtilisationHours;
    private TimePeriod timePeriod;
    

    public String getVehReg() {
        return vehReg;
    }

    public void setVehReg(String vehReg) {
        this.vehReg = vehReg;
    }

    public String getVehGroups() {
        return vehGroups;
    }

    public void setVehGroups(String vehGroups) {
        this.vehGroups = vehGroups;
    }

    public Integer getJourneysCount() {
        return journeysCount;
    }

    public void setJourneysCount(Integer journeysCount) {
        this.journeysCount = journeysCount;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public Integer getActiveDays() {
        return activeDays;
    }

    public void setActiveDays(Integer activeDays) {
        this.activeDays = activeDays;
    }

    public Integer getFullUtilisationHours() {
        return fullUtilisationHours;
    }

    public void setFullUtilisationHours(Integer fullUtilisationHours) {
        this.fullUtilisationHours = fullUtilisationHours;
    }

    public Double getUtilisationPercents() {
        return totalTimeSecs / 60d / 60d / fullUtilisationHours * 100d;
    }

    public void setUtilisationPercents(Integer utilisationPercents) {
    }

    public long getTotalDrivingTimeSecs() {
        return totalDrivingTimeSecs;
    }

    public void setTotalDrivingTimeSecs(long totalDrivingTimeSecs) {
        this.totalDrivingTimeSecs = totalDrivingTimeSecs;
    }

    public String getTotalDrivingTime() {
        return Util.getTimePeriodHoursMinutesColon(totalDrivingTimeSecs * 1000);//Util.decimalFormatter(totalDrivingTimeSecs / 60d / 60d);
    }

    public void setTotalDrivingTime(String totalDrivingTime) {}

    public String getTotalTime() {
        return Util.getTimePeriodHoursMinutesColon(totalTimeSecs * 1000);// Util.decimalFormatter(totalTimeSecs / 60d / 60d);
    }

    public void setTotalTime(String totalTimeSecs) {}

    public String getTotalTimeDecimal() {
        return Util.decimalFormatter(Util.convertHourToDecimal(getTotalTime()));
    }
    
    public String getTotalIdling(){
        return Util.getTimePeriodHoursMinutesColon(totalIdlingSecs * 1000);//Util.decimalFormatter(totalIdlingSecs / 60d / 60d);
    }

    public void setTotalIdling(String totalIdling) {}


    public long getTotalTimeSecs() {
        return totalTimeSecs;
    }

    public void setTotalTimeSecs(long totalTimeSecs) {
        this.totalTimeSecs = totalTimeSecs;
    }

    public long getTotalIdlingSecs() {
        return totalIdlingSecs;
    }

    public void setTotalIdlingSecs(long totalIdlingSecs) {
        this.totalIdlingSecs = totalIdlingSecs;
    }

    public String getDriverDescr() {
        return driverDescr;
    }

    public void setDriverDescr(String driverDescr) {
        this.driverDescr = driverDescr;
    }

    public String getMileageStr() {
        return Util.decimalFormatter(mileage);
    }

    public void setMileageStr(String mileageStr) {
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }
}
