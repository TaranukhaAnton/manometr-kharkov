package com.sp.dto.report;

import java.util.Date;

/**
 * Created by Alexander
 */
public class UtilisationReportRecord {
    private String accountNames;
    private int vehicleId;
    private String regNumber;
    private String locationPostcode;
    private String poiAoi;
    private Date lastJourneyEndDate;
    private int elapsedDays;
    private String vehicleDriverDescr;

    public void copyTo(UtilisationReportRecord copy){
        copy.accountNames = accountNames;
        copy.regNumber = regNumber;
        copy.locationPostcode = locationPostcode;
        copy.poiAoi = poiAoi;
        copy.lastJourneyEndDate = lastJourneyEndDate;
        copy.elapsedDays = elapsedDays;
        copy.vehicleId = vehicleId;
        copy.vehicleDriverDescr = vehicleDriverDescr;
    }

    public String getAccountNames() {
        return accountNames;
    }

    public void setAccountNames(String accountNames) {
        this.accountNames = accountNames;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getLocationPostcode() {
        return locationPostcode;
    }

    public void setLocationPostcode(String locationPostcode) {
        this.locationPostcode = locationPostcode;
    }

    public String getPoiAoi() {
        return poiAoi;
    }

    public void setPoiAoi(String poiAoi) {
        this.poiAoi = poiAoi;
    }

    public Date getLastJourneyEndDate() {
        return lastJourneyEndDate;
    }

    public void setLastJourneyEndDate(Date lastJourneyEndDate) {
        this.lastJourneyEndDate = lastJourneyEndDate;
    }

    public int getElapsedDays() {
        return elapsedDays;
    }

    public void setElapsedDays(int elapsedDays) {
        this.elapsedDays = elapsedDays;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleDriverDescr() {
        return vehicleDriverDescr;
    }

    public void setVehicleDriverDescr(String vehicleDriverDescr) {
        this.vehicleDriverDescr = vehicleDriverDescr;
    }


}
