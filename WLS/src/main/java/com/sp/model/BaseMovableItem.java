package com.sp.model;

import com.sp.SpContext;
import com.sp.util.Util;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 29.06.2009
 * Time: 20:46:02
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public class BaseMovableItem extends AddressedEnumModel {
    @Column(name = "lat", updatable = false, insertable = false)
    private double lat;

    @Column(name = "lon", updatable = false, insertable = false)
    private double lon;

    @Column(insertable = false,updatable = false)
    private Date timestamp;

    @Column(name = "prev_lat", updatable = false, insertable = false)
    private double prevLat;

    @Column(name = "prev_lon", updatable = false, insertable = false)
    private double prevLon;

    @Column(name = "renewal_date", updatable = false)
    private Date renewalDate;

    @Column(name = "cur_speed", updatable = false, insertable = false)
    private double curSpeed;

    @Column(name = "is_deleted")
    private boolean deleted;

    @Column(name = "imei")
    private String imei;

    @Column(name = "cur_direction", updatable = false)
    private int curDirection;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "associated_regular_id")
    private BaseSecurityUser associatedRegularDriver;

    @Column(name = "license_id")
    private Integer licenseId;

    @Transient
    private boolean expanded;

    public Integer getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Integer licenseId) {
        this.licenseId = licenseId;
    }

    public void copyTo(BaseMovableItem copy) {
        copyToLazy(copy);
        if (associatedRegularDriver != null){
            SecurityUser su = new SecurityUser();
            associatedRegularDriver.copyTo(su);
            copy.setAssociatedRegularDriver(su);
        }else{
            copy.setAssociatedRegularDriver(null);
        }
    }

    public void copyToLazy(BaseMovableItem copy) {
        super.copyTo(copy);
        copy.lat = lat;
        copy.lon = lon;
        copy.prevLat = prevLat;
        copy.prevLon = prevLon;
        copy.renewalDate = renewalDate;
        copy.curSpeed = curSpeed;
        copy.deleted = deleted;
        copy.imei = imei;
        copy.licenseId = licenseId;
        copy.expanded = expanded;
        copy.timestamp = timestamp;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
        
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public double getCurSpeed() {
        return curSpeed;
    }

    public void setCurSpeed(double curSpeed) {
        this.curSpeed = curSpeed;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getRegUserFullName(){
        if (associatedRegularDriver != null){
            return associatedRegularDriver.getFirstName() + " " + associatedRegularDriver.getLastName();
        }
        return "";
    }

    public BaseSecurityUser getAssociatedRegularDriver() {
        return associatedRegularDriver;
    }

    public void setAssociatedRegularDriver(BaseSecurityUser associatedRegularDriver) {
        this.associatedRegularDriver = associatedRegularDriver;
    }

     public double getPrevLat() {
        return prevLat;
    }

    public void setPrevLat(double prevLat) {
        this.prevLat = prevLat;
    }

    public double getPrevLon() {
        return prevLon;
    }

    public void setPrevLon(double prevLon) {
        this.prevLon = prevLon;
    }

    public float getFactoredSpeed() {
        return Math.round(curSpeed * SpContext.getUserDetailsInfo().getUserPrefs().getDistanceMetricFactor() * 100) / 100;
    }

    public void setFactoredSpeed(float fs){ }

    public String getDirectionOfTravelUpperCase() {
        return Util.angleToDirectionOfTravel(getCurDirection()).toUpperCase();
    }

    public void setDirectionOfTravelUpperCase(String dot) {}

    public String toOptionText() {
        return imei;
    }

    public int getCurDirection() {
        return curDirection;
    }

    public void setCurDirection(int curDirection) {
        this.curDirection = curDirection;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


}
