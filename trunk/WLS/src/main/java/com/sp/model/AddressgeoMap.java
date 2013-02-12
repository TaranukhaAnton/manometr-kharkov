package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "addressgeo_map")
public class AddressgeoMap extends EnumModel {
    @Column(name = "alternate_name")
    private String alternateName;

    @Column(name = "dot_name")
    private String dotName;

    private String postcode;
    private String district;
    
    private int midx;
    private int midy;

    @Column(name = "latitude")
    private double lat;

    @Column(name = "longtitude")
    private double lon;

    public void copyTo(AddressgeoMap copy) {
        super.copyTo(copy);

        copy.alternateName = alternateName;
        copy.dotName = dotName;
        copy.postcode = postcode;
        copy.district = district;
        copy.midx = midx;
        copy.midy = midy;
        copy.lat = lat;
        copy.lon = lon;
    }

    public String getAlternateName() {
        return alternateName;
    }

    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }

    public String getDotName() {
        return dotName;
    }

    public void setDotName(String dotName) {
        this.dotName = dotName;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getMidx() {
        return midx;
    }

    public void setMidx(int midx) {
        this.midx = midx;
    }

    public int getMidy() {
        return midy;
    }

    public void setMidy(int midy) {
        this.midy = midy;
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
}
