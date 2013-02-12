package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.text.SimpleDateFormat;

/**
 * User: andrey
 */
@Entity
@Table(name = "handheld_incoming_log")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class HandheldIncomingLogRecord extends BaseIncomingLogRecord {

    private double altitude;

    @Column(name = "cell_id")
    private String cellId;

    @Column(name = "poi_descr")
    private String poiDescr;

    @Column(name = "handheld_id")
    private int handheldId;

    @Column(name = "handheld_box_type_id")
    private int handheldBoxTypeId;

    @Column(name = "processing_status")
    private Integer processingStatus;
    
    @Column(insertable = false)
    private String postcode;


    @Column(name = "operator_code")
    private Integer operatorCode;

    @Column(name = "battery_level")
    private Integer batteryLevel;

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Column(name = "alarm_descr")
    private String alarmDescr;

    @Column(name = "signal_quality")
    private int signalQuality;
    
    private int hdop;
    private int vdop;
    private int pdop;


    public String toString() {
        return "recDate=" + sdf.format(super.getRecDate())
                + (super.getCreatedDate() == null ? "" :",createdDate=" + sdf.format(super.getCreatedDate()))
                + ",lat=" + super.getLat() + ",lon=" + super.getLon()
                + ",speed=" + super.getSpeed()
                + ",nodeId=" + super.getNodeId()
                + ",direction=" + super.getDirection()
                + ",satellites=" + super.getSatellites()
                + ",streetName=" + getStreetName()
                + ",altitude=" + altitude
                + ",cellId=" + cellId
                + ",operatorCode=" + operatorCode
                + ",signalQuality=" + signalQuality
                + ",hdop=" + hdop
                + ",vdop=" + vdop
                + ",pdop=" + pdop
                ;
    }


    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public Integer getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(Integer operatorCode) {
        this.operatorCode = operatorCode;
    }
    
    public void setSignalQuality(int signalQuality) {
        this.signalQuality = signalQuality;
    }

    public int getSignalQuality() {
        return signalQuality;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public void setHdop(int hdop) {
        this.hdop = hdop;
    }

    public int getHdop() {
        return hdop;
    }

    public int getVdop() {
        return vdop;
    }

    public void setVdop(int vdop) {
        this.vdop = vdop;
    }

    public int getPdop() {
        return pdop;
    }

    public void setPdop(int pdop) {
        this.pdop = pdop;
    }

    public int getHandheldId() {
        return handheldId;
    }

    public void setHandheldId(int handheldId) {
        this.handheldId = handheldId;
    }

    public String getPoiDescr() {
        return poiDescr;
    }

    public void setPoiDescr(String poiDescr) {
        this.poiDescr = poiDescr;
    }

    public Integer getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(Integer processingStatus) {
        this.processingStatus = processingStatus;
    }
    
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void copyTo(HandheldIncomingLogRecord copy){
        super.copyTo(copy);
        copy.altitude = altitude;
        copy.cellId = cellId;
        copy.operatorCode = operatorCode;
        copy.batteryLevel = batteryLevel;
        copy.signalQuality = signalQuality;
        copy.hdop = hdop;
        copy.vdop = vdop;
        copy.pdop = pdop;
        copy.handheldId = handheldId;
        copy.processingStatus = processingStatus;
        copy.poiDescr = poiDescr;
        copy.postcode = postcode;
        copy.alarmDescr = alarmDescr;
        copy.handheldBoxTypeId = handheldBoxTypeId;
    }

    public String getAlarmDescr() {
        return alarmDescr;
    }

    public void setAlarmDescr(String alarmDescr) {
        this.alarmDescr = alarmDescr;
    }

    public int getHandheldBoxTypeId() {
        return handheldBoxTypeId;
    }

    public void setHandheldBoxTypeId(int handheldBoxTypeId) {
        this.handheldBoxTypeId = handheldBoxTypeId;
    }




}
