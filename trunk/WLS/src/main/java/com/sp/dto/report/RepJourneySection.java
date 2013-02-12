package com.sp.dto.report;

import com.sp.model.BaseSecurityUser;
import com.sp.model.Journey;
import com.sp.model.LightVehicle;
import com.sp.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class RepJourneySection {
    private Date startDay;
    private Date endDay;
    private LightVehicle vehicle;
    private BaseSecurityUser driver;
    private String boxTypeDescr;
    private List<RepJourneyDaySubSection> daySubSections = new ArrayList<RepJourneyDaySubSection>();
    private float totalDistancePrivate;
    private float totalDistanceBusiness;
    private int journeysCount;
    private float totalDistanceForFleet;
    private long totalIdlingForFleet;
    private long totalTimeForFleet;
    private boolean stoppedTimeInsteadIdling;

    public RepJourneySection(Date startDay, LightVehicle vehicle, boolean stoppedTimeInsteadIdling, String boxTypeDescr, BaseSecurityUser driver) {
        this.startDay = startDay;
        this.vehicle = vehicle;
        this.stoppedTimeInsteadIdling = stoppedTimeInsteadIdling;
        this.boxTypeDescr = boxTypeDescr;
        this.driver = driver;
    }

    public String getTotalIdlingTimeAlternate() {
        long stopIdling = 0;
        for (RepJourneyDaySubSection subSection : daySubSections) {
            stopIdling += stoppedTimeInsteadIdling ? subSection.getStoppedTime() : subSection.getIdling();
        }
        return Util.getTimeDelayStrAlternate(stopIdling, false);
    }

    public String getTotalTimeAlternate() {
        long totalTime = 0;
        for (RepJourneyDaySubSection subSection : daySubSections) {
            totalTime += subSection.getDrivingTime();
        }
        return Util.getTimeDelayStrAlternate(totalTime, false);
    }
//
    public float getTotalDistance() {
        float totalDistance = 0;
        for(RepJourneyDaySubSection subSection : daySubSections) {
            totalDistance += subSection.getTotalDistance();
        }
        return  Math.round(totalDistance*100.0)/100.0f;
    }

    public LightVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(LightVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getTotalTime() {
        List<Journey> total = new ArrayList<Journey>();
        for (RepJourneyDaySubSection journey : daySubSections){
            total.addAll(journey.getJourneyList());
        }
        return Util.getTotalDrivingTimeByJourneyList(total);
    }

    public float getTotalCost() {
        float totalCost = 0;
        if (daySubSections.size() > 0){
            for (RepJourneyDaySubSection journey : daySubSections){
                totalCost += journey.getTotalCost();
            }
        }
        return  Math.round(totalCost*100.0)/100.0f;
    }

    public String getTotalIdlingTime() {
        return Util.getTimeDelayStr(getIdling());
    }

    public int getIdling() {
        int result = 0;
        for (RepJourneyDaySubSection journeySection : daySubSections){
            for (Journey journey : journeySection.getJourneyList()) {
                result += journey.getIdling();
            }
        }
        return result;
    }

    public List<RepJourneyDaySubSection> getDaySubSections() {
        return daySubSections;
    }

    public String getBoxTypeDescr() {
        return boxTypeDescr;
    }

    public void setBoxTypeDescr(String boxTypeDescr) {
        this.boxTypeDescr = boxTypeDescr;
    }


    public float getTotalDistancePrivate() {
        return totalDistancePrivate;
    }

    public void setTotalDistancePrivate(float totalDistancePrivate) {
        this.totalDistancePrivate = totalDistancePrivate;
    }

    public float getTotalDistanceBusiness() {
        return totalDistanceBusiness;
    }

    public void setTotalDistanceBusiness(float totalDistanceBusiness) {
        this.totalDistanceBusiness = totalDistanceBusiness;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    public int getJourneysCount() {
        return journeysCount;
    }

    public void setJourneysCount(int journeysCount) {
        this.journeysCount = journeysCount;
    }

    public float getTotalDistanceForFleet() {
        return totalDistanceForFleet;
    }

    public void setTotalDistanceForFleet(float totalDistanceForFleet) {
        this.totalDistanceForFleet = totalDistanceForFleet;
    }

    public long getTotalIdlingForFleet() {
        return totalIdlingForFleet;
    }

    public void setTotalIdlingForFleet(long totalIdlingForFleet) {
        this.totalIdlingForFleet = totalIdlingForFleet;
    }

    public String getTotalIdlingForFleetStr() {
        return Util.getTimeDelayStrAlternate(totalIdlingForFleet, true);
    }

    public long getTotalTimeForFleet() {
        return totalTimeForFleet;
    }

    public void setTotalTimeForFleet(long totalTimeForFleet) {
        this.totalTimeForFleet = totalTimeForFleet;
    }

     public String getTotalTimeForFleetStr() {
        return Util.getTimeDelayStrAlternate(totalTimeForFleet, true);
    }

    public BaseSecurityUser getDriver() {
        return driver;
    }

    public void setDriver(BaseSecurityUser driver) {
        this.driver = driver;
    }
}
