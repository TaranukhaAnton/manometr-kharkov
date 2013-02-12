package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "outgoing_log")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class OutgoingLogRecord extends EnumModel {

    @Column(insertable = false, updatable = false)
    private Date timestamp;

    @Column(name = "nodeId", updatable = false)
    private String imei;

    @Column(name = "unit_id", updatable = false)
    private Integer unitId;

    @Column(name = "tracking_device_id", updatable = false)
    private Integer trackingDeviceId;

    @Column(name = "request_received_date", updatable = false)
    private Date requestReceivedDate;

    public void copyTo(OutgoingLogRecord copy) {
        super.copyTo(copy);
        copy.timestamp = timestamp;
        copy.imei = imei;
        copy.unitId = unitId;
        copy.trackingDeviceId = trackingDeviceId;
        copy.requestReceivedDate = requestReceivedDate;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Date getRequestReceivedDate() {
        return requestReceivedDate;
    }

    public void setRequestReceivedDate(Date requestReceivedDate) {
        this.requestReceivedDate = requestReceivedDate;
    }


    public Integer getTrackingDeviceId() {
        return trackingDeviceId;
    }

    public void setTrackingDeviceId(Integer trackingDeviceId) {
        this.trackingDeviceId = trackingDeviceId;
    }
}
