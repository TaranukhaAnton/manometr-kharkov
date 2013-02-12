package com.sp.dto.mobile;

import com.sp.model.IncomingLogRecord;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 25.10.12
 * Time: 15:00
 * To change this template use File | Settings | File Templates.
 */
public class IncomingLogRecordMobile {
    private double lat;
    private double lon;
    private String street;
    private String heading;
    private double speed;
    private Date date;
    private String aoiPOIDescription;


    public IncomingLogRecordMobile(IncomingLogRecord incomingLogRecord) {
        this.lat = incomingLogRecord.getLat();
        this.lon = incomingLogRecord.getLon();
        this.street = incomingLogRecord.getStreetName();
        this.heading = angleToDirectionOfTravel(incomingLogRecord.getDirectionDevice());
        this.speed = incomingLogRecord.getFactoredSpeed();
        this.date = incomingLogRecord.getRecDate();
        this.aoiPOIDescription = incomingLogRecord.getAoiPoiDescr();
    }

    private static String angleToDirectionOfTravel(double angle) {
        return angle < 22.5 ? "n"
                : angle < 67.5 ? "ne"
                : angle < 112.5 ? "e"
                : angle < 157.5 ? "se"
                : angle < 202.5 ? "s"
                : angle < 247.5 ? "sw"
                : angle < 292.5 ? "w"
                : angle < 337.5 ? "nw" : "n";
    }

    public IncomingLogRecordMobile(double lat, double lon, String street, String heading) {
        this.lat = lat;
        this.lon = lon;
        this.street = street;
        this.heading = heading;
    }

    public String getAoiPOIDescription() {
        return aoiPOIDescription;
    }

    public void setAoiPOIDescription(String aoiPOIDescription) {
        this.aoiPOIDescription = aoiPOIDescription;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}
