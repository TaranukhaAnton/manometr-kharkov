package com.sp.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "driver_message")
public class DriverMessage extends AddressedEnumModel {

    public static final String[] TASK_TYPE = {"Service", "Delivery", "Collection"};

    @Column(name = "rec_date")
    private Date recDate;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "unit_id")
    private LightVehicle vehicle;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_mobile")
    private String contactMobile;

    private double lat;

    private double lon;

    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    private MessageTypeFilter messageType;

    private boolean unread;

    public DriverMessage() {
        
    }



    public void copyTo(DriverMessage copy) {
        super.copyTo(copy);
        copy.recDate = recDate;
        copy.vehicle = vehicle;
        copy.contactEmail = contactEmail;
        copy.contactName = contactName;
        copy.contactMobile = contactMobile;
        copy.messageType = messageType;
        copy.lat = lat;
        copy.lon = lon;
        copy.unread = unread;
    }

    public Date getRecDate() {
        return recDate;
    }

    public void setRecDate(Date recDate) {
        this.recDate = recDate;
    }

    public LightVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(LightVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public MessageTypeFilter getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypeFilter messageType) {
        this.messageType = messageType;
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

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }
}
