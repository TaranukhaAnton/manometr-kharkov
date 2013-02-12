package com.sp.model;

import java.util.Date;


public class MileageRecord {

    private int vehicleId;
    private float distance;
    private Date day;
    private String driver;
    private String vehReg;

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
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
