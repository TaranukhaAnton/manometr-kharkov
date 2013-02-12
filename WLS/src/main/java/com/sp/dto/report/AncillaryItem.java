package com.sp.dto.report;

import java.util.Date;


public class AncillaryItem {

    private String registrationNumber;
    private String streetName;
    private String postcode;
    private String poiDescr;
    private String ignitionStr;
    private String input1Str;
    private String input2Str;
    private String input3Str;
    private boolean unitIsInMotion;
    private boolean dout1Active;
    private boolean dout2Active;
    private Date recDate;
    private boolean isStealthMode;
   
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
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

    public String getIgnitionStr() {
        return ignitionStr;
    }

    public void setIgnitionStr(String ignitionStr) {
        this.ignitionStr = ignitionStr;
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

    public boolean isUnitIsInMotion() {
        return unitIsInMotion;
    }

    public void setUnitIsInMotion(boolean unitIsInMotion) {
        this.unitIsInMotion = unitIsInMotion;
    }

    public boolean isDout1Active() {
        return dout1Active;
    }

    public void setDout1Active(boolean dout1Active) {
        this.dout1Active = dout1Active;
    }

    public boolean isDout2Active() {
        return dout2Active;
    }

    public void setDout2Active(boolean dout2Active) {
        this.dout2Active = dout2Active;
    }

    public Date getRecDate() {
        return recDate;
    }

    public void setRecDate(Date recDate) {
        this.recDate = recDate;
    }

    public boolean isStealthMode() {
        return isStealthMode;
    }

    public void setStealthMode(boolean stealthMode) {
        isStealthMode = stealthMode;
    }
}
