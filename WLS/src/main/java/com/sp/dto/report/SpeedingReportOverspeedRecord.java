package com.sp.dto.report;

import com.sp.model.IncomingLogRecord;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg
 * Date: 18.04.12
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class SpeedingReportOverspeedRecord {

    private Date recDate;
    private int unitId;
    private String streetName;
    private String postcode;
    private String aoiPoiDescr;
    private String driverDescr;
    private boolean inStealthMode;
    private String speedLimit;
    private float vehicleSpeed;
    private float speedDiff;
    private String speedDiffPercentage;
    

    public SpeedingReportOverspeedRecord() {
    }

    public SpeedingReportOverspeedRecord(IncomingLogRecord ir) {
        this.recDate = ir.getRecDate();
        this.unitId = ir.getVehicleId();
        this.streetName = ir.getStreetName();
        this.postcode = ir.getPostcode();
        this.aoiPoiDescr = ir.getAoiPoiDescr();
        this.driverDescr = ir.getDallasDriverName();
        this.inStealthMode = ir.isInStealthMode();
        this.vehicleSpeed = ir.getFactoredSpeed();
        this.speedDiffPercentage = ir.getPercentAboveSpeedLimit();
        this.speedDiff = ir.getAboveSpeedLimitValue();
        this.speedLimit = ir.getMaxFactoredSpeedLimit() + "";
    }

    public Date getRecDate() {
        return recDate;
    }

    public void setRecDate(Date recDate) {
        this.recDate = recDate;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAoiPoiDescr() {
        return aoiPoiDescr;
    }

    public void setAoiPoiDescr(String aoiPoiDescr) {
        this.aoiPoiDescr = aoiPoiDescr;
    }

    public String getDriverDescr() {
        if (driverDescr != null) {
            return driverDescr;
        }  else {
            return "";
        }
    }

    public void setDriverDescr(String driverDescr) {
        this.driverDescr = driverDescr;
    }

    public boolean isInStealthMode() {
        return inStealthMode;
    }

    public void setInStealthMode(boolean inStealthMode) {
        this.inStealthMode = inStealthMode;
    }
    
   

    public String getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(String speedLimit) {
        this.speedLimit = speedLimit;
    }

    public float getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(float vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    public float getSpeedDiff() {
        return speedDiff;
    }

    public void setSpeedDiff(float speedDiff) {
        this.speedDiff = speedDiff;
    }

    public String getSpeedDiffPercentage() {
        return speedDiffPercentage;
    }

    public void setSpeedDiffPercentage(String speedDiffPercentage) {
        this.speedDiffPercentage = speedDiffPercentage;
    }
}
