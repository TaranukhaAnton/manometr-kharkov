package com.sp.dto.report;

import com.sp.model.BaseVehicle;
import com.sp.util.Util;

import java.util.ArrayList;
import java.util.List;


public class RepDigitalInputSection  {
    private List<RepDigitalIputDaySubSection> daySubSections = new ArrayList<RepDigitalIputDaySubSection>();
    private BaseVehicle vehicle;
    private long totalIgnitionOn;
    private long totalIgnitionOff;
    private long totalDigitalInputOn;
    private long totalIgnitionOnFleet;
    private long totalIgnitionOffFleet;
    private long totalDigitalInputOnFleet;
    private float totalDistance;
    private float totalDistanceFleet;

    public List<RepDigitalIputDaySubSection> getDaySubSections() {
        return daySubSections;
    }

    public void setDaySubSections(List<RepDigitalIputDaySubSection> daySubSections) {
        this.daySubSections = daySubSections;
    }

    public BaseVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(BaseVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getTotalIgnitionOnTime() {
        return Util.getTimeDelayStrAlternate(totalIgnitionOn, true);
    }

    public String getTotalIgnitionOffTime() {
        return Util.getTimeDelayStrAlternate(totalIgnitionOff, true);
    }

    public String getTotalDigitalInputOnTime() {
        return Util.getTimeDelayStrAlternate(totalDigitalInputOn, true);
    }

     public String getTotalIgnitionOnTimeFleet() {
        return Util.getTimeDelayStrAlternate(totalIgnitionOnFleet, true);
    }

    public String getTotalIgnitionOffTimeFleet() {
        return Util.getTimeDelayStrAlternate(totalIgnitionOffFleet, true);
    }

    public String getTotalDigitalInputOnTimeFleet() {
        return Util.getTimeDelayStrAlternate(totalDigitalInputOnFleet, true);
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public long getTotalIgnitionOn() {
        return totalIgnitionOn;
    }

    public long getTotalIgnitionOff() {
        return totalIgnitionOff;
    }

    public long getTotalDigitalInputOn() {
        return totalDigitalInputOn;
    }

    

    public void increaseTotalIgnitionOn(long ignitionOn) {
        this.totalIgnitionOn += ignitionOn;
    }

    public void increaseTotalIgnitionOff(long ignitionOff) {
        this.totalIgnitionOff += ignitionOff;
    }

    public void increaseTotalDigitalInputOn(long digitalInputOn) {
        this.totalDigitalInputOn += digitalInputOn;
    }

    public void increaseTotalDistance(float distance) {
        this.totalDistance += distance;
    }

    public long getTotalIgnitionOnFleet() {
        return totalIgnitionOnFleet;
    }

    public void setTotalIgnitionOnFleet(long totalIgnitionOnFleet) {
        this.totalIgnitionOnFleet = totalIgnitionOnFleet;
    }

    public long getTotalIgnitionOffFleet() {
        return totalIgnitionOffFleet;
    }

    public void setTotalIgnitionOffFleet(long totalIgnitionOffFleet) {
        this.totalIgnitionOffFleet = totalIgnitionOffFleet;
    }

    public long getTotalDigitalInputOnFleet() {
        return totalDigitalInputOnFleet;
    }

    public void setTotalDigitalInputOnFleet(long totalDigitalInputOnFleet) {
        this.totalDigitalInputOnFleet = totalDigitalInputOnFleet;
    }

    public float getTotalDistanceFleet() {
        return totalDistanceFleet;
    }

    public void setTotalDistanceFleet(float totalDistanceFleet) {
        this.totalDistanceFleet = totalDistanceFleet;
    }

}
