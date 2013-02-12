package com.sp.dto.report;

import com.sp.model.BaseVehicle;
import com.sp.util.Util;

import java.util.ArrayList;
import java.util.List;

public class RepTimeOnSiteUnitSubSection extends VehicleReportSection {

    private List<RepTimeOnSiteDaySubSection> recordList = new ArrayList<RepTimeOnSiteDaySubSection>();
    private int totalVisits;
    private long totalTime;

    public RepTimeOnSiteUnitSubSection(BaseVehicle vehicle) {
        super(vehicle);
    }

    public int getTotalVisits() {
        return totalVisits;
    }

    public void setTotalVisits(int totalVisits) {
        this.totalVisits = totalVisits;
    }

    public List<RepTimeOnSiteDaySubSection> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RepTimeOnSiteDaySubSection> recordList) {
        this.recordList = recordList;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public String getTotalTimeStr() {
        return Util.getTimeDelayStrAlternate(totalTime, true);
    }

     public void incrementTotalVisit() {
        totalVisits++;
    }

    public void addDuration(long duration) {
        this.totalTime += duration;
    }
}
