package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: 05.10.2009
 */
@Entity
@Table(name = "aoi")
public class Aoi extends AccountedEnumModel {

    @Transient
    private String polygonWkt;

    @Column(name = "pin_color")
    private String pinColor;

    @Column(name = "area_color")
    private String areaColor;

    @Column(name = "geometry_id") // it is ID of the Postgre AOI
    private int geometryId;

    public void copyTo(Aoi copy) {
        super.copyTo(copy);
        copy.pinColor = pinColor;
        copy.areaColor = areaColor;
        copy.geometryId = geometryId;
    }
    
    public String getPolygonWkt() {
        return polygonWkt;
    }

    public void setPolygonWkt(String polygonWkt) {
        this.polygonWkt = polygonWkt;
    }

    public String getPinColor() {
        return pinColor;
    }

    public void setPinColor(String pinColor) {
        this.pinColor = pinColor;
    }

    public int getGeometryId() {
        return geometryId;
    }

    public void setGeometryId(int geometryId) {
        this.geometryId = geometryId;
    }

    public String getAreaColor() {
        return areaColor;
    }

    public void setAreaColor(String areaColor) {
        this.areaColor = areaColor;
    }
}
