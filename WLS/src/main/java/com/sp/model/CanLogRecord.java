package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * User: andrew
 * Date: 09.01.2010
 */
@Entity
@Table(name = "can_log")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class CanLogRecord extends BaseModel {
    @Column(name = "rec_date")
    private Date recDate;

    @Column(name = "unit_number")
    private Integer unitNumber;

    @Column(name = "unit_id", insertable = false, updatable = false)
    private Integer unitId;

    @Column(name = "triggered_sensor_index")
    private Integer triggeredSensorIndex;

    @Column(name = "incoming_log_id")
    private Integer incomingLogId;

    @Column(name = "imei_id", insertable = false, updatable = false)
    private Integer trackingDeviceId;

    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

    @OneToMany(mappedBy = "canLogRecord", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CanSensorValue> canSensorValues;

    @Column(name = "msg_text")
    private String msgText; 

    @Transient
    private IncomingLogRecord incomingLogRecord;

    @Column(name = "transmission_reason")
    private Short transmissionReason;

    public void copyTo(CanLogRecord copy) {
        super.copyTo(copy);
        copy.recDate = recDate;
        copy.unitNumber = unitNumber;
        copy.unitId = unitId;
        copy.triggeredSensorIndex = triggeredSensorIndex;
        copy.incomingLogId = incomingLogId;
        copy.createdDate = createdDate;
        copy.canSensorValues = canSensorValues;
        copy.trackingDeviceId = trackingDeviceId;
        copy.msgText = msgText;
    }

    public Integer getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getTriggeredSensorIndex() {
        return triggeredSensorIndex;
    }

    public void setTriggeredSensorIndex(Integer triggeredSensorIndex) {
        this.triggeredSensorIndex = triggeredSensorIndex;
    }

    public IncomingLogRecord getIncomingLogRecord() {
        return incomingLogRecord;
    }

    public void setIncomingLogRecord(IncomingLogRecord incomingLogRecord) {
        this.incomingLogRecord = incomingLogRecord;
    }

    public List<CanSensorValue> getCanSensorValues() {
        return canSensorValues;
    }

    public void setCanSensorValues(List<CanSensorValue> canSensorValues) {
        this.canSensorValues = canSensorValues;
    }

    @Override
    public String toString() {
        return "CanLogRecord{" +
                "recDate=" + recDate +
                ", unitNumber=" + unitNumber +
                ", unitId=" + unitId +
                ", triggeredSensorIndex=" + triggeredSensorIndex +
                ", incomingLogId=" + incomingLogId +
                ", trackingDeviceId=" + trackingDeviceId +
                ", createdDate=" + createdDate +
                ", canSensorValues=" + canSensorValues +
                ", msgText='" + msgText + '\'' +
                ", incomingLogRecord=" + incomingLogRecord +
                ", transmissionReason=" + transmissionReason +
                '}';
    }

    public Integer getTrackingDeviceId() {
        return trackingDeviceId;
    }

    public void setTrackingDeviceId(Integer trackingDeviceId) {
        this.trackingDeviceId = trackingDeviceId;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public Integer getIncomingLogId() {
        return incomingLogId;
    }

    public void setIncomingLogId(Integer incomingLogId) {
        this.incomingLogId = incomingLogId;
    }

    public void setTransmissionReason(Short transmissionReason) {
        this.transmissionReason = transmissionReason;
    }

    public Short getTransmissionReason() {
        return transmissionReason;
    }

    public Date getRecDate() {
        return recDate;
    }

    public void setRecDate(Date recDate) {
        this.recDate = recDate;
    }
}
