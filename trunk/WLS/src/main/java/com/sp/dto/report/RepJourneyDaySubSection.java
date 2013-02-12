package com.sp.dto.report;

import com.sp.model.Journey;
import com.sp.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class RepJourneyDaySubSection {
    private Date day;
    private List<Journey> journeyList = new ArrayList<Journey>();
    private float totalDistancePrivate;
    private float totalDistanceBusiness;
    private boolean stoppedTimeInsteadIdling;

    public RepJourneyDaySubSection(Date day,boolean stoppedTimeInsteadIdling) {
        this.day = day;
        this.stoppedTimeInsteadIdling = stoppedTimeInsteadIdling;
    }


    public float getTotalCostForMiles() {
        double result = 0;
        for (Journey journey : journeyList) {
            result += journey.getCostPerMileToDistance();
        }

        return Math.round(result * 100) / 100f;
    }

    public float getTotalCostForHours() {
        double result = 0;
        for (Journey journey : journeyList) {
            result += journey.getCostPerHourToDistance();
        }
        return Math.round(result * 100) / 100f;
    }

    public float getTotalCost(){
        return Math.round((getTotalCostForHours() + getTotalCostForMiles())*100)/100f;
    }
    
    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public List<Journey> getJourneyList() {
        return journeyList;
    }

    public int getJourneyListSize() {
        return journeyList.size();
    }

    public float getTotalDistance() {
        return Math.round((totalDistanceBusiness + totalDistancePrivate) * 100) / 100f;
    }

    public String getTotalTime() {
        return Util.getTotalDrivingTimeByJourneyList(journeyList);
    }

    public String getTotalTimeAlternate() {
        return Util.getTotalDrivingTimeByJourneyListAlternate(journeyList);
    }

    // For flex
    public void setTotalDistance(float totalDistance) {
    }

    public String getTotalIglingTime() {
        return Util.getTimeDelayStr(getIdling());
    }

    public String getTotalIdlingTimeAlternate() {
        return Util.getTimeDelayStrAlternate(stoppedTimeInsteadIdling ? getStoppedTime() : getIdling(), false);
    }

    public int getIdling() {
        int result = 0;
        for (Journey journey : journeyList) {
            result += journey.getIdling();
        }
        return result;
    }


    public long getStoppedTime(){
        return Util.getStoppedTimeBetweenJourneys(journeyList);
    }

    public long getDrivingTime() {
        return Util.getTotalDrivingTimeByJourneyListLong(journeyList);
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
}
