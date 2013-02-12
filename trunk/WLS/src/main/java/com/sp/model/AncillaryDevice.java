package com.sp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "ancillary_device")
public class AncillaryDevice extends EnumModel {
    @Column(name = "start_date")
    private Date startDate;

    private String notes;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "imei_id")
    private TrackingDevice trackingDevice;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "account_id")
    private Account account;

    public void copyTo(AncillaryDevice copy) {
        super.copyTo(copy);
        copy.setStartDate(startDate);
        copy.setNotes(notes);
        copy.setTrackingDevice(trackingDevice);
        copy.setAccount(account);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public TrackingDevice getTrackingDevice() {
        return trackingDevice;
    }

    public void setTrackingDevice(TrackingDevice trackingDevice) {
        this.trackingDevice = trackingDevice;
    }
}
