package com.sp.dto.report;

import com.sp.model.Journey;
import com.sp.model.LightVehicle;
import com.sp.util.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander
 */
public class SpeedingReportRecord {
    private int id; // it is journeyId
    private Date startDate;
    private Date endDate;
    private String startStreetName;
    private String endStreetName;
    private String driverDescr;
    private float factoredMaxSpeed;
    private float distance;
    private String startAoiPoiDescr;
    private String endAoiPoiDescr;
    private String startPostcode;
    private String endPostcode;
    private LightVehicle vehicle;
    private float factoredDistance;
    private boolean inStealthMode;
    private Map<String,String> speedLimitMsgCountMap;
    private Map<String,String> speedLimitMaxSpeedMap;


    public SpeedingReportRecord(Journey j) {
        id = j.getId();
        startDate = j.getStartDate();
        endDate = j.getEndDate();
        startStreetName = j.getStartStreetName();
        endStreetName = j.getEndStreetName();
        driverDescr = j.getDriverDescr();
        factoredMaxSpeed = j.getFactoredMaxSpeed();
        distance = j.getDistance();
        startAoiPoiDescr = j.getStartAoiPoiDescr();
        endAoiPoiDescr = j.getEndAoiPoiDescr();
        startPostcode = j.getStartPostcode();
        endPostcode = j.getEndPostcode();
        vehicle = j.getVehicle();
        factoredDistance = j.getFactoredDistance();
        inStealthMode = j.isInStealthMode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDriverDescr() {
        return driverDescr;
    }

    public void setDriverDescr(String driverDescr) {
        this.driverDescr = driverDescr;
    }

    public float getFactoredMaxSpeed() {
        return factoredMaxSpeed;
    }

    public void setFactoredMaxSpeed(float factoredMaxSpeed) {
        this.factoredMaxSpeed = factoredMaxSpeed;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
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

    public LightVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(LightVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Map<String,String> getSpeedLimitMsgCountMap() {
        return speedLimitMsgCountMap;
    }

    public void setSpeedLimitMsgCountMap(Map<Integer, Integer> speedLimitMsgCountMap) {
        Map<String,String> res = new HashMap<String,String>();
        for(Integer key : speedLimitMsgCountMap.keySet()){
            res.put(String.valueOf(key),String.valueOf(speedLimitMsgCountMap.get(key)));
        }
        this.speedLimitMsgCountMap = res;
    }

    public Map<String,String> getSpeedLimitMaxSpeedMap() {
        return speedLimitMaxSpeedMap;
    }

    public void setSpeedLimitMaxSpeedMap(Map<Integer,Double> speedLimitMaxSpeedMap) {
        Map<String,String> res = new HashMap<String,String>();
        for(Integer key : speedLimitMaxSpeedMap.keySet()){
            res.put(String.valueOf(key), Util.decimalFormatter((speedLimitMaxSpeedMap.get(key))));
        }
        this.speedLimitMaxSpeedMap = res;
    }

    public float getFactoredDistance() {
        return factoredDistance;
    }

    public void setFactoredDistance(float factoredDistance) {
        this.factoredDistance = factoredDistance;
    }

    public boolean isInStealthMode() {
        return inStealthMode;
    }

    public void setInStealthMode(boolean inStealthMode) {
        this.inStealthMode = inStealthMode;
    }



}
