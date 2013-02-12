package com.sp.dto.report;

import com.sp.model.BaseVehicle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg
 * Date: 18.04.12
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
public class SpeedingRepOverspeedSection {

    private BaseVehicle vehicle;
    private Date startDay;
    private Date endDay;
    private int percentageCriteria;
    private int speedCriteria;

    private List<SpeedingReportOverspeedRecord> speedingReportOverspeedRecords
            = new ArrayList<SpeedingReportOverspeedRecord>();

    public List<SpeedingReportOverspeedRecord> getSpeedingReportOverspeedRecords() {
        return speedingReportOverspeedRecords;
    }

    public void setSpeedingReportOverspeedRecords(List<SpeedingReportOverspeedRecord> speedingReportOverspeedRecords) {
        this.speedingReportOverspeedRecords = speedingReportOverspeedRecords;
    }


    public BaseVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(BaseVehicle vehicle) {
        this.vehicle = vehicle;
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

    public int getPercentageCriteria() {
        return percentageCriteria;
    }

    public void setPercentageCriteria(int percentageCriteria) {
        this.percentageCriteria = percentageCriteria;
    }

    public int getSpeedCriteria() {
        return speedCriteria;
    }

    public void setSpeedCriteria(int speedCriteria) {
        this.speedCriteria = speedCriteria;
    }
}
