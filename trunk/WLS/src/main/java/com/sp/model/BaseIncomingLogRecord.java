package com.sp.model;

import com.sp.util.Util;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 05.08.2009
 * Time: 14:23:41
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public class BaseIncomingLogRecord extends BaseModel {

    private String nodeId;
    
    private double speed;

    @Column(name = "rec_date")
    private Date recDate;

    @Column(name = "direction_device")
    private int directionDevice;

    private Integer satellites;

    private double lat;
    
    private double lon;

    @Column(insertable = false)
    private String streetName;

    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDirection() {
        return directionDevice;
    }

    public void setDirection(double dir){}

    public String getDirectionCode() {
        return Util.angleToDirectionOfTravel(getDirection());
    }

    //flex serialization requires this setter
    public void setDirectionCode(String code) {
    }

    public Integer getSatellites() {
        return satellites;
    }

    public void setSatellites(Integer satellites) {
        this.satellites = satellites;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getRecDate() {
        return recDate;
    }

    public void setRecDate(Date recDate) {
        this.recDate = recDate;
    }

    public float getFactoredSpeed() {
        double r = speed * Util.getDistanceMetricFactor() * 100f;
        long l = Math.round(r);
        float res = l / 100f;
        return  Math.round(speed * Util.getDistanceMetricFactor() * 100f) / 100f;
    }

     //for flex serialization
    public void setFactoredSpeed(float factoredSpeed) {

    }

    public int getDirectionDevice() {
        return directionDevice;
    }

    public void setDirectionDevice(int directionDevice) {
        this.directionDevice = directionDevice;
    }

     public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void copyTo(BaseIncomingLogRecord copy) {
        super.copyTo(copy);
        copy.nodeId = nodeId;
        copy.speed = speed;
        copy.directionDevice = directionDevice;
        copy.satellites = satellites;
        copy.lat = lat;
        copy.lon = lon;
        copy.createdDate = createdDate;
        copy.recDate = recDate;
        copy.streetName = streetName;
    }
}
