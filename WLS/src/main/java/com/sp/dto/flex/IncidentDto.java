package com.sp.dto.flex;

import com.sp.model.BaseModel;

import java.util.Date;

/**
 * Created by Alexander
 */
public class IncidentDto extends BaseModel {
	private String type;
	private String title;
	private String remark;
	private String remarkDateTime;
	private String imageLink;
	private double lat;
	private double lon;
	private String location;
	private String severity;
    private String eventStartDateTime;
    private String category;
    private String descr;
    private Date timestamp;
    private double gridEasting;
    private double gridNorthing;


    public IncidentDto() {
//        setId(incident.getId());
//        type = incident.getType();
//        title = incident.getTitle();
//        remark = incident.getRemark();
//        remarkDateTime = incident.getRemarkDateTime() != null ? Constants.DATE_TIME_FORMAT.format(incident.getRemarkDateTime()) : "";
//        imageLink = incident.getImageLink();
//        lat = incident.getLat();
//        lon = incident.getLon();
//        location = incident.getLocation();
//        severity = incident.getSeverity();
//        eventStartDateTime = incident.getEventStartDateTime()!=null ? Constants.DATE_TIME_FORMAT.format(incident.getEventStartDateTime()) : "";
//        category = incident.getCategory();
//        descr = incident.getDescr();
//        timestamp = incident.getTimestamp();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemarkDateTime() {
        return remarkDateTime;
    }

    public void setRemarkDateTime(String remarkDateTime) {
        this.remarkDateTime = remarkDateTime;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEventStartDateTime() {
        return eventStartDateTime;
    }

    public void setEventStartDateTime(String eventStartDateTime) {
        this.eventStartDateTime = eventStartDateTime;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getGridEasting() {
        return gridEasting;
    }

    public void setGridEasting(double gridEasting) {
        this.gridEasting = gridEasting;
    }

    public double getGridNorthing() {
        return gridNorthing;
    }

    public void setGridNorthing(double gridNorthing) {
        this.gridNorthing = gridNorthing;
    }

}
