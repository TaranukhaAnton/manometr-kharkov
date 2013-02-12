package com.sp.dto.report;

import com.sp.model.TimeOnSiteRecord;
import com.sp.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RepTimeOnSiteDaySubSection {

    private List<TimeOnSiteRecord> timeOnSiteRecords = new ArrayList<TimeOnSiteRecord>();
    private Date day;
    private int totalVisits;
    private long totalTime;


    public int getTotalVisits() {
        return totalVisits;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public String getTotalTimeStr() {
        return Util.getTimeDelayStrAlternate(totalTime, true);
    }

    public List<TimeOnSiteRecord> getTimeOnSiteRecords() {
        return timeOnSiteRecords;
    }

    public void setTimeOnSiteRecords(List<TimeOnSiteRecord> timeOnSiteRecords) {
        this.timeOnSiteRecords = timeOnSiteRecords;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public void addDuration(long duration) {
        this.totalTime += duration;
    }
    public void incrementTotalVisit() {
        totalVisits++;
    }

}
