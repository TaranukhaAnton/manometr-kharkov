package com.sp.model;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by Alexander
 */
//@Entity
//@Table(name = "incident")
public class Incident extends EnumModel {
	private int itisid;
	private String type;
	private String title;
	private String remark;

    @Column(name = "remark_date_time")
	private Date remarkDateTime;

    @Column(name = "image_link")
	private String imageLink;

    @Column(name = "grid_easting")
	private double gridEasting;

    @Column(name = "grid_northing")
	private double gridNorthing;

    @Column(name = "lat")
	private double lat;

    @Column(name = "lon")
	private double lon;

	private String location;

    @Column(name = "post_code_start")
	private String postCodeStart;

    @Column(name = "post_code_end")
	private String postCodeEnd;

    @Column(name = "severity")
	private String severity;

    @Column(name = "publish_date_time")
	private Date publishDateTime;

    @Column(name = "last_modified_service_time")
	private Date lastModifiedServiceTime;

    @Column(name = "event_start_date_time")
	private Date eventStartDateTime;

    @Column(name = "event_end_date_time")
	private Date eventEndDateTime;

    @Column(name = "event_type")
	private Integer eventType;

	private String category;

    @Column(name = "timestamp", insertable = false, updatable = false)
	private Date timestamp;

    public void copyTo(Incident copy) {
		super.copyTo(copy);
        copy.itisid = itisid;
        copy.type = type;
        copy.title = title;
        copy.remark = remark;
        copy.remarkDateTime = remarkDateTime;
        copy.gridEasting = gridEasting;
        copy.gridNorthing = gridNorthing;
        copy.lat = lat;
        copy.lon = lon;
        copy.location = location;
        copy.postCodeStart = postCodeStart;
        copy.postCodeEnd = postCodeEnd;
        copy.severity = severity;
        copy.publishDateTime = publishDateTime;
        copy.lastModifiedServiceTime = lastModifiedServiceTime;
        copy.eventStartDateTime = eventStartDateTime;
        copy.eventEndDateTime = eventEndDateTime;
        copy.eventType = eventType;
        copy.category = category;
        copy.timestamp = timestamp;
    }

    public int getItisid() {
        return itisid;
    }

    public void setItisid(int itisid) {
        this.itisid = itisid;
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

    public Date getRemarkDateTime() {
        return remarkDateTime;
    }

    public void setRemarkDateTime(Date remarkDateTime) {
        this.remarkDateTime = remarkDateTime;
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

    public String getPostCodeStart() {
        return postCodeStart;
    }

    public void setPostCodeStart(String postCodeStart) {
        this.postCodeStart = postCodeStart;
    }

    public String getPostCodeEnd() {
        return postCodeEnd;
    }

    public void setPostCodeEnd(String postCodeEnd) {
        this.postCodeEnd = postCodeEnd;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Date getPublishDateTime() {
        return publishDateTime;
    }

    public void setPublishDateTime(Date publishDateTime) {
        this.publishDateTime = publishDateTime;
    }

    public Date getLastModifiedServiceTime() {
        return lastModifiedServiceTime;
    }

    public void setLastModifiedServiceTime(Date lastModifiedServiceTime) {
        this.lastModifiedServiceTime = lastModifiedServiceTime;
    }

    public Date getEventStartDateTime() {
        return eventStartDateTime;
    }

    public void setEventStartDateTime(Date eventStartDateTime) {
        this.eventStartDateTime = eventStartDateTime;
    }

    public Date getEventEndDateTime() {
        return eventEndDateTime;
    }

    public void setEventEndDateTime(Date eventEndDateTime) {
        this.eventEndDateTime = eventEndDateTime;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }


}
