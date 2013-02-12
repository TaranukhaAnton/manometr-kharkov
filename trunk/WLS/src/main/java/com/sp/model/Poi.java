package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONE)
@Table(name = "poi")
public class Poi extends AccountedEnumModel {
    private double lat;
    private double lon;
    private int radius;
    @Column(name = "pin_color")
    private String pinColor;

    @Column(name = "area_color")
    private String areaColor;
    
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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void copyTo(Poi copy) {
        super.copyTo(copy);
        copy.setLat(getLat());
        copy.setLon(getLon());
        copy.setRadius(getRadius());
        copy.pinColor = pinColor;
        copy.areaColor = areaColor;
    }

    public String getClientDescr(){
        return super.getDescr();
    }

    public String getImageFileName(){
        return "locationIcon.gif";
    }

    public String getPinColor() {
        return pinColor;
    }

    public void setPinColor(String pinColor) {
        this.pinColor = pinColor;
    }

    public String getAreaColor() {
        return areaColor;
    }

    public void setAreaColor(String areaColor) {
        this.areaColor = areaColor;
    }
}
