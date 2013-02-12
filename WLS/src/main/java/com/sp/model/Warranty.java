package com.sp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "warranty")
public class Warranty extends EnumModel {
    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    private String supplier;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "imei_id")
    private TrackingDevice trackingDevice;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "ancillary_id")
    private AncillaryDevice ancillaryDevice;

    @Column(name = "is_deleted")
    private boolean deleted;

    public void copyTo(Warranty copy) {
        super.copyTo(copy);
        copy.setStartDate(startDate);
        copy.setEndDate(endDate);
        copy.setSupplier(supplier);
        copy.setTrackingDevice(trackingDevice);
        copy.setAncillaryDevice(ancillaryDevice);
        copy.setDeleted(deleted);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public TrackingDevice getTrackingDevice() {
        return trackingDevice;
    }

    public void setTrackingDevice(TrackingDevice trackingDevice) {
        this.trackingDevice = trackingDevice;
    }

    public AncillaryDevice getAncillaryDevice() {
        return ancillaryDevice;
    }

    public void setAncillaryDevice(AncillaryDevice ancillaryDevice) {
        this.ancillaryDevice = ancillaryDevice;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
