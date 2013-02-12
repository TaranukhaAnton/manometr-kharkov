package com.sp.model;

import com.sp.util.Util;

import java.util.Date;


public class DigitalInputReportRecord {
    private String startPostcode;
    private String endPostcode;
    private String startStreetName;
    private String endStreetName;
    private Date startDate;
    private Date endDate;
    private String digitalInputName;
    private int vehicleId;
    private String startPoiDescr;
    private String endPoiDescr;
    private String startAoiDescr;
    private String endAoiDescr;
    private int inputIndex;
    private String startAoiPoiDescr;
    private String endAoiPoiDescr;
    private boolean ignition;

    public String getStartPostcode() {
        return startPostcode;
    }

    public void setStartPostcode(String startPostcode) {
        this.startPostcode = startPostcode;
    }

    public String getEndPostcode() {
        return endPostcode;
    }

    public void setEndPostcode(String endPostcode) {
        this.endPostcode = endPostcode;
    }

    public void calculateFields() {
        startAoiPoiDescr = Util.buildAoiPoiDescr(startPoiDescr, startAoiDescr);
        endAoiPoiDescr = Util.buildAoiPoiDescr(endPoiDescr, endAoiDescr);        
    }


    public String getStartStreetName() {
        return startStreetName;
    }

    public void setStartStreetName(String startStreetName) {
        this.startStreetName = startStreetName;
    }

    public String getEndStreetName() {
        return endStreetName;
    }

    public void setEndStreetName(String endStreetName) {
        this.endStreetName = endStreetName;
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

   public String getDuration() {
        if (endDate == null) {
            return null;
        }
        long diffInSeconds = (endDate.getTime() - startDate.getTime()) / 1000;
        return Util.getTimeDelayStr(diffInSeconds);
    }

    // For flex serialization
    public void setDuration(String duration) {

    }

    public String getDigitalInputName() {
        return digitalInputName;
    }

    public void setDigitalInputName(String digitalInputName) {
        this.digitalInputName = digitalInputName;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getStartPoiDescr() {
        return startPoiDescr;
    }

    public void setStartPoiDescr(String startPoiDescr) {
        this.startPoiDescr = startPoiDescr;
    }

    public String getEndPoiDescr() {
        return endPoiDescr;
    }

    public void setEndPoiDescr(String endPoiDescr) {
        this.endPoiDescr = endPoiDescr;
    }

    public String getStartAoiDescr() {
        return startAoiDescr;
    }

    public void setStartAoiDescr(String startAoiDescr) {
        this.startAoiDescr = startAoiDescr;
    }

    public String getEndAoiDescr() {
        return endAoiDescr;
    }

    public void setEndAoiDescr(String endAoiDescr) {
        this.endAoiDescr = endAoiDescr;
    }

    public int getInputIndex() {
        return inputIndex;
    }

    public void setInputIndex(int inputIndex) {
        this.inputIndex = inputIndex;
    }

    public String getStartAoiPoiDescr() {
        return startAoiPoiDescr;
    }

    public void setStartAoiPoiDescr(String startAoiPoiDescr) {
        this.startAoiPoiDescr = startAoiPoiDescr;
    }

    public String getEndAoiPoiDescr() {
        return endAoiPoiDescr;
    }

    public void setEndAoiPoiDescr(String endAoiPoiDescr) {
        this.endAoiPoiDescr = endAoiPoiDescr;
    }

    public boolean isIgnition() {
        return ignition;
    }

    public void setIgnition(boolean ignition) {
        this.ignition = ignition;
    }

}
