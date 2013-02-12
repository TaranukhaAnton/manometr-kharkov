package com.sp.dto.report;

import com.sp.model.DigitalInputReportRecord;
import com.sp.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RepDigitalIputDaySubSection {
    private Date day;
    private List<DigitalInputReportRecord> recordList = new ArrayList<DigitalInputReportRecord>();
    // For alternate PDF and XLS reports  only
    private List<DigitalInputEventRecord> eventRecordList = new ArrayList<DigitalInputEventRecord>();
    private String digitalInputName;
    private long totalIgnitionOn;
    private long totalIgnitionOff;
    private long totalDigitalInputOn;
    private float totalDistance;
    private boolean isDigitalInputPresent;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getDigitalInputName() {
        return digitalInputName;
    }

    public void setDigitalInputName(String digitalInputName) {
        this.digitalInputName = digitalInputName;
    }

    public List<DigitalInputReportRecord> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<DigitalInputReportRecord> recordList) {
        this.recordList = recordList;
    }

    public String getTotalTime() {
        return Util.getTotalDurationByInputRecordList(recordList);
    }

    // For flex serialization
    public void setTotalTime(String totalTime) {
    }

    public List<DigitalInputEventRecord> getEventRecordList() {
        return eventRecordList;
    }

    public void setEventRecordList(List<DigitalInputEventRecord> eventRecordList) {
        this.eventRecordList = eventRecordList;
    }

    public long getTotalIgnitionOn() {
        return totalIgnitionOn;
    }

    public void setTotalIgnitionOn(long totalIgnitionOn) {
        this.totalIgnitionOn = totalIgnitionOn;
    }

    public long getTotalIgnitionOff() {
        return totalIgnitionOff;
    }

    public void setTotalIgnitionOff(long totalIgnitionOff) {
        this.totalIgnitionOff = totalIgnitionOff;
    }

    public long getTotalDigitalInputOn() {
        return totalDigitalInputOn;
    }

    public void setTotalDigitalInputOn(long totalDigitalInputOn) {
        this.totalDigitalInputOn = totalDigitalInputOn;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
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

    public boolean isDigitalInputPresent() {
        return isDigitalInputPresent;
    }

    public void setDigitalInputPresent(boolean digitalInputPresent) {
        isDigitalInputPresent = digitalInputPresent;
    }
}
