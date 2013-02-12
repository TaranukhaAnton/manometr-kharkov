package com.sp.model;

import com.sp.util.Util;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class TimeOnSiteRecord {
    private String location;

    private String postcode;

    private Date arrivalTime;

    private Date departureTime;

    private BaseVehicle vehicle;

    private String poiDescr;

    private String aoiDescr;

    private String aoiPoiDescr;

    private String latLon;


    // For alternate report
    private Date arrivalSectionDate;

    private String vehicleRegSection;

    public String getVehicleRegistrationNumber() {
        return vehicle.getRegistrationNumber();
    }

    public String getAoiPoiDescr() {
        return aoiPoiDescr;
    }

    public void setAoiPoiDescr(String aoiPoiDescr) {
        this.aoiPoiDescr = aoiPoiDescr;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public BaseVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(BaseVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getDuration() {
        if (departureTime == null) {
            return null;
        }
        long diffInSeconds = getDurationSeconds();
        return Util.getTimeDelayStr(diffInSeconds);
    }

    public void setDuration(String s){}

    public String getDurationAlternate() {
        if (departureTime == null) {
            return null;
        }
        long diffInSeconds = getDurationSeconds();
        return Util.getTimeDelayStrAlternate(diffInSeconds, true);
    }

    public long getDurationSeconds() {
         return (departureTime.getTime() - arrivalTime.getTime()) / 1000;
    }

    public long getDurationMinutes() {
        return getDurationSeconds() / 60;
    }

    public String getPoiDescr() {
        return poiDescr;
    }

    public void setPoiDescr(String poiDescr) {
        this.poiDescr = poiDescr;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setAoiDescr(String aoiDescr) {
        this.aoiDescr = aoiDescr;
    }

    public String getAoiDescr() {
        return aoiDescr;
    }

    public String getLatLon() {
        return latLon;
    }

    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }

    public Date getArrivalSectionDate() {
        return arrivalSectionDate;
    }

    public void setArrivalSectionDate(Date arrivalSectionDate) {
        this.arrivalSectionDate = arrivalSectionDate;
    }

    public String getVehicleRegSection() {
        return vehicleRegSection;
    }

    public void setVehicleRegSection(String vehicleRegSection) {
        this.vehicleRegSection = vehicleRegSection;
    }

    public String getSite() {
        if (aoiPoiDescr == null && latLon == null) {
            return "";
        } else if(aoiPoiDescr == null) {
            return latLon;
        } else {
            return aoiPoiDescr;
        }
    }

    public String getAddressStr() {
        if (location != null && postcode != null) {
            return location + ", " + postcode;
        } else if (location != null) {
            return location;
        } else if (postcode != null) {
            return postcode;
        } else {
            return "";
        }
    }
}
