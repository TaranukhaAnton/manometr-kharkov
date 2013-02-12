package com.sp.model;

import com.sp.util.Constants;

import java.util.Date;


public class VehicleState {
    
    private Constants.IgnitionStatus ignitionStatus;

    private Date journeyStartDate;

    private Date dateInStealhMode;

    private Boolean stealthModeDinHigh;

    public Constants.IgnitionStatus getIgnitionStatus() {
        return ignitionStatus;
    }

    public void setIgnitionStatus(Constants.IgnitionStatus ignitionStatus) {
        this.ignitionStatus = ignitionStatus;
    }

    public Date getJourneyStartDate() {
        return journeyStartDate;
    }

    public void setJourneyStartDate(Date journeyStartDate) {
        this.journeyStartDate = journeyStartDate;
    }

    public Boolean isStealthModeDinHigh() {
        return stealthModeDinHigh;
    }

    public void setStealthModeDinHigh(Boolean stealthModeDinHigh) {
        this.stealthModeDinHigh = stealthModeDinHigh;
    }

    public Date getDateInStealhMode() {
        return dateInStealhMode;
    }

    public void setDateInStealhMode(Date dateInStealhMode) {
        this.dateInStealhMode = dateInStealhMode;
    }
}
