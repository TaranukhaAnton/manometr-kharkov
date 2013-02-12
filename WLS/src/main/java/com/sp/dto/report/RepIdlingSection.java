package com.sp.dto.report;

import com.sp.model.BaseVehicle;
import com.sp.model.IdlingLog;
import com.sp.util.Util;

import java.util.ArrayList;
import java.util.List;


public class RepIdlingSection extends VehicleReportSection {
    private List<IdlingLog> idlingLogList = new ArrayList<IdlingLog>();
    private int idlingExceptionTime;
    private long totalSectionIdling;
    private long totalIdling;

    public RepIdlingSection(BaseVehicle vehicle) {
        super(vehicle);
    }

    public List<IdlingLog> getIdlingLogList() {
        return idlingLogList;
    }

    public void setIdlingLogList(List<IdlingLog> idlingLogList) {
        this.idlingLogList = idlingLogList;
    }


    public void setIdlingExceptionTime(int idlingExceptionTime) {
        this.idlingExceptionTime = idlingExceptionTime;
    }

    public void increaseIdling(long idling) {
        this.totalSectionIdling += idling;
    }

    public long getTotalSectionIdling() {
        return totalSectionIdling;
    }

    public String getTotalSectionIdlingStr() {
        return Util.getTimeDelayStrAlternate(totalSectionIdling, true);
    }

    public String getIdlingExceptionTime() {
        return Util.getTimeDelayStrAlternate(idlingExceptionTime, false);
    }

    public String getTotalIdlingStr() {
        return Util.getTimeDelayStrAlternate(totalIdling, true);
    }

    public void setTotalIdling(long totalIdling) {
        this.totalIdling = totalIdling;
    }
}
