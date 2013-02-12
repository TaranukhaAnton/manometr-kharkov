package com.sp.dto.report;

import com.sp.model.Journey;
import com.sp.model.LateStartPeriod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class RepLateStartSection {
    private String comment;
    private short lateStartPeriodNum;
    private LateStartPeriod lateStartPeriod;
    private Date reportDay;
    private String fromTime;
    private String toTime;

    private List<Journey> journeyList = new ArrayList<Journey>();


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Journey> getJourneyList() {
        return journeyList;
    }

    public void setJourneyList(List<Journey> journeyList) {
        this.journeyList = journeyList;
    }

    public void setLateStartPeriodNum(short lateStartPeriodNum) {
        this.lateStartPeriodNum = lateStartPeriodNum;
    }

    public short getLateStartPeriodNum() {
        return lateStartPeriodNum;
    }

    public void setLateStartPeriod(LateStartPeriod lateStartPeriod) {
        this.lateStartPeriod = lateStartPeriod;
    }

    public LateStartPeriod getLateStartPeriod() {
        return lateStartPeriod;
    }

    public Date getReportDay() {
        return reportDay;
    }

    public void setReportDay(Date reportDay) {
        this.reportDay = reportDay;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }
}
