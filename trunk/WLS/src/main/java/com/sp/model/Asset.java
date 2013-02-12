package com.sp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "tag")
public class Asset extends AccountedEnumModel {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "type_id")
    private AssetType type;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "tag_carrier_type_id")
    private TagCarrierType tagCarrierType;

    @Column(name = "last_contact_date")
    private Date lastContactDate;

    @Column(name = "site_code")
    private int siteCode;

    @Column(name = "tag_id")
    private int tagId;

    private double lat;
    private double lon;

    @Column(name = "is_active")
    private boolean active;
    
    public void copyTo(Asset copy) {
        super.copyTo(copy);
        copy.setType(getType());
        copy.setTagCarrierType(getTagCarrierType());
        copy.setLastContactDate(getLastContactDate());
        copy.setSiteCode(getSiteCode());
        copy.setTagId(getTagId());
        copy.setLon(getLon());
        copy.setLat(getLat());
        copy.active = active;
    }

    public AssetType getType() {
        return type;
    }

    public void setType(AssetType type) {
        this.type = type;
    }

    public Date getLastContactDate() {
        return lastContactDate;
    }

    public void setLastContactDate(Date lastContactDate) {
        this.lastContactDate = lastContactDate;
    }

    public int getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(int siteCode) {
        this.siteCode = siteCode;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
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

    public TagCarrierType getTagCarrierType() {
        return tagCarrierType;
    }

    public void setTagCarrierType(TagCarrierType tagCarrierType) {
        this.tagCarrierType = tagCarrierType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getClientDescr(){
        return super.getDescr();
    }
}
