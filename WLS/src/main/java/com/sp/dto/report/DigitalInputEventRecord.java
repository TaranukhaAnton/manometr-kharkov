package com.sp.dto.report;

import com.sp.SpContext;
import com.sp.model.UserPrefs;
import com.sp.util.Util;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;


public class DigitalInputEventRecord {
    private String postcode;
    private String streetName;
    private Date recDate;
    private String digitalInputName;
    private int vehicleId;
    private String poiDescr;
    private String aoiDescr;
    private String aoiPoiDescr;
    private boolean ignition;
    private int inputIndex;
    private float distance;
    private long ignitionOn;
    private long ignitionOff;
    private long digitalInputOn;
    private float factoredDistance;
    private Integer journeyId;
    private String driver;
    private String vehReg;

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Date getRecDate() {
        return recDate;
    }

    public void setRecDate(Date recDate) {
        this.recDate = recDate;
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

    public int getInputIndex() {
        return inputIndex;
    }

    public void setInputIndex(int inputIndex) {
        this.inputIndex = inputIndex;
    }

    public String getAoiPoiDescr() {
        if (aoiPoiDescr == null) {
            return "";
        }
        return aoiPoiDescr;
    }

    public void setAoiPoiDescr(String aoiPoiDescr) {
        this.aoiPoiDescr = aoiPoiDescr;
    }

    public boolean isIgnition() {
        return ignition;
    }

    public void setIgnition(boolean ignition) {
        this.ignition = ignition;
    }

    public float getDistance() {

        return distance;
    }

    public String getDistanceStr() {
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        if (factoredDistance == 0.0f || factoredDistance < 0.1) {
            return "-";
        } else {
            return nf.format(factoredDistance) + "";
        }
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getIgnitionOnFor() {
        if (ignitionOn == 0.0f) {
            return "-";
        }
        return Util.getTimeDelayStrAlternate(ignitionOn, false);
    }

    public String getIgnitionOffFor() {
        if (ignitionOff == 0.0f) {
            return "-";
        }
        return Util.getTimeDelayStrAlternate(ignitionOff, false);
    }

    public Float getDistanceGrid() {
        if (factoredDistance == 0.0f || factoredDistance < 0.1) {
            return null;
        } else {
            return factoredDistance;
        }
    }

    public String getDigitalInputOnFor() {
         if (digitalInputOn == 0.0f) {
            return "-";
        }
        return Util.getTimeDelayStrAlternate(digitalInputOn, false);
    }

    public String getIgnitionOnGrid() {
        if (ignitionOn == 0.0f) {
            return null;
        }
        return Util.getTimeDelayStr(ignitionOn);
    }

    public String getIgnitionOffGrid() {
        if (ignitionOff == 0.0f) {
            return null;
        }
        return Util.getTimeDelayStr(ignitionOff);
    }

    public String getDigitalInputOnGrid() {
         if (digitalInputOn == 0.0f) {
            return null;
        }
        return Util.getTimeDelayStr(digitalInputOn);
    }

    public long getIgnitionOn() {
        return ignitionOn;
    }

    public void setIgnitionOn(long ignitionOn) {
        this.ignitionOn = ignitionOn;
    }

    public long getIgnitionOff() {
        return ignitionOff;
    }

    public void setIgnitionOff(long ignitionOff) {
        this.ignitionOff = ignitionOff;
    }

    public long getDigitalInputOn() {
        return digitalInputOn;
    }

    public void setDigitalInputOn(long digitalInputOn) {
        this.digitalInputOn = digitalInputOn;
    }

    public String getAddress() {
        if (streetName != null && postcode != null) {
            return streetName + ", " + postcode;
        } else if (streetName != null) {
            return streetName;
        } else if (postcode != null) {
            return postcode;
        } else {
            return "-";
        }
    }
    public void calculateFields() {
        aoiPoiDescr = Util.buildAoiPoiDescr(poiDescr, aoiDescr);
        UserPrefs userPrefs = SpContext.getUserDetailsInfo().getUserPrefs();
        factoredDistance = Math.round(distance * userPrefs.getDistanceMetricFactor() / 10) / 100f;
    }

    public float getFactoredDistance() {
        return factoredDistance;
    }

    public void setFactoredDistance(float factoredDistance) {
        this.factoredDistance = factoredDistance;
    }

    public Integer getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(Integer journeyId) {
        this.journeyId = journeyId;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getVehReg() {
        return vehReg;
    }

    public void setVehReg(String vehReg) {
        this.vehReg = vehReg;
    }
}
