package com.sp.model;

import com.sp.SpContext;
import com.sp.util.Util;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Ananda
 * Date: Mar 20, 2009
 * Time: 4:06:31 PM
 */
@Entity
@Table(name = "phone_unit")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class Phone extends AddressedEnumModel {

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "type_id")
    private PhoneType type;

    @Column(name = "renewal_date")
    private Date renewalDate;

    @Column(name = "lat", updatable = false, insertable = false)
    private double lat;

    @Column(name = "lon", updatable = false, insertable = false)
    private double lon;

    @Column(name = "prev_lat")
    private double prevLat;

    @Column(name = "prev_lon")
    private double prevLon;

    @Column(name = "cur_speed", updatable = false)
    private double curSpeed;

    @Column(name = "phone_type")
    private String phoneType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "associated_regular_id")
    private BaseSecurityUser associatedRegularDriver;


    @Column(name = "is_deleted")
    private boolean deleted;

    @Column(name = "imei")
    private String imei;

    @Column(name = "activation_date")
    private Date activationDate;

    @Column(name = "disable_date")
    private Date disableDate;

    @Column(name = "license_id")
    private Integer licenseId;

    @Column(name = "last_no_fix_date")
    private Date lastNoGpsFixDate;

    @Column(name = "force_update_sms_status")
    private String forceUpdateSmsStatus;

    @Column(name = "force_update_sms_date")
    private Date forceUpdateSmsDate;

    @Column(name = "force_update_sms_id")
    private String forceUpdateSmsId;

    @Column( name = "client_info")
    private String clientInfo;

    public String getRegUserFullName() {
        if (associatedRegularDriver != null) {
            return associatedRegularDriver.getFirstName() + " " + associatedRegularDriver.getLastName();
        }
        return "";
    }

    public Date getLastNoGpsFixDate() {
        return lastNoGpsFixDate;
    }

    public void setLastNoGpsFixDate(Date lastNoGpsFixDate) {
        this.lastNoGpsFixDate = lastNoGpsFixDate;
    }

    public Integer getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Integer licenseId) {
        this.licenseId = licenseId;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
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

    public double getCurSpeed() {
        return curSpeed;
    }

    public void setCurSpeed(double curSpeed) {
        this.curSpeed = curSpeed;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }


    public BaseSecurityUser getAssociatedRegularDriver() {
        return associatedRegularDriver;
    }

    public void setAssociatedRegularDriver(BaseSecurityUser associatedRegularDriver) {
        this.associatedRegularDriver = associatedRegularDriver;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public void copyTo(Phone copy) {
        super.copyTo(copy);
        copy.setId(getId());
        copy.setPhoneNumber(phoneNumber);
        copy.setDescr(getDescr());
        PhoneType dtoType = new PhoneType();
        type.copyTo(dtoType);
        copy.setType(dtoType);
        copy.renewalDate = renewalDate;
        copy.setLat(getLat());
        copy.setLon(getLon());
        copy.prevLon = prevLon;
        copy.prevLat = prevLat;
        copy.lastNoGpsFixDate = lastNoGpsFixDate;
        copy.curSpeed = curSpeed;
        copy.associatedRegularDriver = associatedRegularDriver;
        copy.deleted = deleted;
        copy.imei = imei;
        copy.licenseId = licenseId;
        copy.forceUpdateSmsId = forceUpdateSmsId;
        copy.forceUpdateSmsDate = forceUpdateSmsDate;
        copy.forceUpdateSmsStatus = forceUpdateSmsStatus;
        copy.clientInfo = clientInfo;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public Date getDisableDate() {
        return disableDate;
    }

    public void setDisableDate(Date disableDate) {
        this.disableDate = disableDate;
    }

    public float getFactoredSpeed() {
        return Math.round(curSpeed * SpContext.getUserDetailsInfo().getUserPrefs().getDistanceMetricFactor() * 100) / 100;
    }

    public String getDirectionOfTravelUpperCase() {
        return Util.angleToDirectionOfTravel(Util.getAngle(getLon(), getLat(), getPrevLon(), getPrevLat())).toUpperCase();
    }

    public boolean isVisibleLastNoGpsFix() {
        return lastNoGpsFixDate != null && renewalDate != null && lastNoGpsFixDate.compareTo(renewalDate) > 0;
    }

    public String getForceUpdateSmsStatus() {
        return forceUpdateSmsStatus;
    }

    public void setForceUpdateSmsStatus(String forceUpdateSmsStatus) {
        this.forceUpdateSmsStatus = forceUpdateSmsStatus;
    }

    public Date getForceUpdateSmsDate() {
        return forceUpdateSmsDate;
    }

    /**
     * Datetime when sms request considered as timed out
     *
     * @param forceUpdateSmsDate
     */
    public void setForceUpdateSmsDate(Date forceUpdateSmsDate) {
        this.forceUpdateSmsDate = forceUpdateSmsDate;
    }

    public String getForceUpdateSmsId() {
        return forceUpdateSmsId;
    }

    public void setForceUpdateSmsId(String forceUpdateSmsId) {
        this.forceUpdateSmsId = forceUpdateSmsId;
    }

    /**
     * Check if force update is sent and is not timed out
     *
     * @return
     */
    public boolean isWaitingForceUpdateStatus() {
        return forceUpdateSmsDate != null && (0 < (forceUpdateSmsDate.getTime() - new Date().getTime()));
    }
    
    public String getClientDescr() {
        String descr = "";
        if (getAssociatedRegularDriver() != null){
            descr += getAssociatedRegularDriver().getLastName() + "'s ";
        }
        descr += getTypeDescr();
        return descr;
    }

    public String toOptionText() {
        return imei;
    }


    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }
    
}
