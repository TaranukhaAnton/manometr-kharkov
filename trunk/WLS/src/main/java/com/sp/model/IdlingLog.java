package com.sp.model;

import com.sp.util.Util;

import javax.persistence.*;
import java.util.Date;

// TODO extend Journey from Idling
@Entity
@Table(name="idling_log")
public class IdlingLog extends BaseModel {

    @Column(name="start_date")
    private Date startDate;
    @Column(name="end_date")
    private Date endDate;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "unit_id")
    private BaseVehicle vehicle;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "security_user_id")
    private BaseSecurityUser driver;
    @Column(name="poi_descr")
    private String poiDescr;
    @Column(name="aoi_descr")
    private String aoiDescr;
    @Column(name="street_name")
    private String streetName;
    private String postcode;
    @Transient
    private String aoiPoiDescr;

    public void copyTo(IdlingLog copy) {
        super.copyTo(copy);
        copy.startDate = startDate;
        copy.endDate = endDate;
        copy.driver = driver;
        copy.poiDescr = poiDescr;
        copy.aoiDescr = aoiDescr;
        copy.vehicle = vehicle;
        copy.streetName = streetName;
        copy.postcode = postcode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BaseVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(BaseVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public BaseSecurityUser getDriver() {
        return driver;
    }

    public void setDriver(BaseSecurityUser driver) {
        this.driver = driver;
    }

    public String getPoiDescr() {
        return poiDescr;
    }

    public void setPoiDescr(String poiDescr) {
        this.poiDescr = poiDescr;
    }

    public String getAoiDescr() {
        return aoiDescr;
    }

    public void setAoiDescr(String aoiDescr) {
        this.aoiDescr = aoiDescr;
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

    public long getIdling() {
        return (endDate.getTime() - startDate.getTime()) / 1000;
    }

    public String getIdlingStr() {
        return Util.getTimeDelayStrAlternate(getIdling(), false);
    }

    public String getAoiPoiDescr() {
        return aoiPoiDescr;
    }

    public void calculateFields() {
        aoiPoiDescr = Util.buildAoiPoiDescr(poiDescr, aoiDescr);
    }

     public String getDriverDescr() {
        if (driver != null) {
            if (driver.getDallasKey() != null) {
                return "Driver ID: " + driver.getDescr();
            } else {
                return driver.getDescr();
            }
        } else {
            return "";
        }
    }

    public String getAddress() {
        if (streetName != null && postcode != null) {
            return streetName + ", " + postcode;
        } else if (streetName != null) {
            return streetName;
        } else if (postcode != null) {
            return postcode;
        } else {
            return "";
        }
    }
}
