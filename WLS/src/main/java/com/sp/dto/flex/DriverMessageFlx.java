package com.sp.dto.flex;

import com.sp.model.MessageTypeFilter;


public class DriverMessageFlx {

    private int vehicleId;

    private String contactEmail;

    private String contactName;

    private String contactMobile;

    private MessageTypeFilter messageType;

    private String curStreet;

    private String curPostcode;

    private int id;

    private String descr;

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
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

    public String getCurStreet() {
        return curStreet;
    }

    public void setCurStreet(String curStreet) {
        this.curStreet = curStreet;
    }

    public String getCurPostcode() {
        return curPostcode;
    }

    public void setCurPostcode(String curPostcode) {
        this.curPostcode = curPostcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
