package com.sp.dto.report;

import com.sp.model.BaseModel;
import com.sp.model.VehicleIncomingLogRecord;
import com.sp.util.Util;

/**
 * Created by Alexander
 */
public class ActivityLogRecord extends BaseModel {
    //private String registrationNumber;
    private long recDate;
    private String input1Str;
    private String input2Str;
    private String input3Str;
    private String streetName;
    private String postcode;
    private String poiDescr;
    private float factoredSpeed;
    private boolean inStealthMode;
    private boolean journeyStart;
    private boolean journeyEnd;
    private String directionCode;
    private String ignitionStr;
    private int vehicleId;
    private double lat;
    private double lon;
    private String trakm8MessageTypeDescr;

    public ActivityLogRecord(VehicleIncomingLogRecord incomingLogRecord) {
        super.setId(incomingLogRecord.getId());
        Util.setDaylightTimeUK(incomingLogRecord);
        recDate = incomingLogRecord.getRecDate().getTime();
        input1Str = incomingLogRecord.getInput1Str();
        input2Str = incomingLogRecord.getInput2Str();
        input3Str = incomingLogRecord.getInput3Str();
        streetName = incomingLogRecord.getStreetName();
        postcode = incomingLogRecord.getPostcode();
        poiDescr = incomingLogRecord.getPoiDescr();
        factoredSpeed = incomingLogRecord.getFactoredSpeed();
        inStealthMode = incomingLogRecord.isInStealthMode();
        journeyStart = incomingLogRecord.isJourneyStart();
        journeyEnd = incomingLogRecord.isJourneyEnd();
        directionCode = incomingLogRecord.getDirectionCode();
        ignitionStr = incomingLogRecord.getIgnitionStr();
        vehicleId = incomingLogRecord.getVehicleId();
        lat = incomingLogRecord.getLat();
        lon = incomingLogRecord.getLon();
        trakm8MessageTypeDescr = incomingLogRecord.getTrakm8MessageTypeDescr();
    }

    public long getRecDate() {
        return recDate;
    }

    public void setRecDate(long recDate) {
        this.recDate = recDate;
    }

    public String getInput1Str() {
        return input1Str;
    }

    public void setInput1Str(String input1Str) {
        this.input1Str = input1Str;
    }

    public String getInput2Str() {
        return input2Str;
    }

    public void setInput2Str(String input2Str) {
        this.input2Str = input2Str;
    }

    public String getInput3Str() {
        return input3Str;
    }

    public void setInput3Str(String input3Str) {
        this.input3Str = input3Str;
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

    public String getPoiDescr() {
        return poiDescr;
    }

    public void setPoiDescr(String poiDescr) {
        this.poiDescr = poiDescr;
    }

    public float getFactoredSpeed() {
        return factoredSpeed;
    }

    public void setFactoredSpeed(float factoredSpeed) {
        this.factoredSpeed = factoredSpeed;
    }

    public boolean isInStealthMode() {
        return inStealthMode;
    }

    public void setInStealthMode(boolean inStealthMode) {
        this.inStealthMode = inStealthMode;
    }

    public boolean isJourneyStart() {
        return journeyStart;
    }

    public void setJourneyStart(boolean journeyStart) {
        this.journeyStart = journeyStart;
    }

    public boolean isJourneyEnd() {
        return journeyEnd;
    }

    public void setJourneyEnd(boolean journeyEnd) {
        this.journeyEnd = journeyEnd;
    }

    public String getDirectionCode() {
        return directionCode;
    }

    public void setDirectionCode(String directionCode) {
        this.directionCode = directionCode;
    }

    public String getIgnitionStr() {
        return ignitionStr;
    }

    public void setIgnitionStr(String ignitionStr) {
        this.ignitionStr = ignitionStr;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
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

    public String getTrakm8MessageTypeDescr() {
        return trakm8MessageTypeDescr;
    }

    public void setTrakm8MessageTypeDescr(String trakm8MessageTypeDescr) {
        this.trakm8MessageTypeDescr = trakm8MessageTypeDescr;
    }

}
