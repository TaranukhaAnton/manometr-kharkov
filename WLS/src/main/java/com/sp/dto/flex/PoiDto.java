package com.sp.dto.flex;

import com.sp.model.BaseModel;
import com.sp.model.Poi;

/**
 * Created by Alexander
 */
public class PoiDto extends BaseModel {
    private double lat;
    private double lon;
    private String descr;

    public PoiDto() {
    }

    public PoiDto(Poi poi) {
        setId(poi.getId());
        descr = poi.getDescr();
        lat = poi.getLat();
        lon = poi.getLon();
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

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }



}
