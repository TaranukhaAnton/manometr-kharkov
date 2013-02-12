package com.sp.dto.mobile;

import com.sp.SpContext;
import com.sp.model.Vehicle;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg
 * Date: 28.08.12
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class VehicleMobile {
    private int id;
    private String vehReg;
    private Date date;
    private String driver;
    private String heading;
    private String location;
    private double lat;
    private double lon;
    private String ignitionStatus;
    private String imageName;
    private String unitViewIds;
    private float speed;
    private String speedUnits;


    public VehicleMobile() {}

    public VehicleMobile(Vehicle vehicle) {
        this.id = vehicle.getId();
        this.vehReg = vehicle.getRegistrationNumber();
        this.date = vehicle.getRenewalDate();
//        this.date = new Date(); // for testing s
        this.driver = vehicle.getRegDriverFullName();
        this.heading = vehicle.getDirectionOfTravel();
        this.location = vehicle.getCurAddress();
        this.lat = vehicle.getLat();
        this.lon = vehicle.getLon();
        this.ignitionStatus = vehicle.getIgnitionStatus();
        this.imageName = vehicle.getType().getImageFileName();
        this.speed = vehicle.getFactoredSpeed();
        this.speedUnits = SpContext.getUserPrefs().getDistanceMetric();

    }

    public String getVehReg() {
        return vehReg;
    }

    public void setVehReg(String vehReg) {
        this.vehReg = vehReg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getIgnitionStatus() {
        return ignitionStatus;
    }

    public void setIgnitionStatus(String ignitionStatus) {
        this.ignitionStatus = ignitionStatus;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnitViewIds() {
        return unitViewIds;
    }

    public void setUnitViewIds(String unitViewIds) {
        this.unitViewIds = unitViewIds;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getSpeedUnits() {
        return speedUnits;
    }

    public void setSpeedUnits(String speedUnits) {
        this.speedUnits = speedUnits;
    }
}
