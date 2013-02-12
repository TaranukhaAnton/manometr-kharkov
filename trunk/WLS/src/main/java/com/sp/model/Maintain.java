package com.sp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "maintain")
public class Maintain extends EnumModel {
    private Date date;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "engineer_id")
    private SecurityUser engineer;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "imei_id")
    private TrackingDevice trackingDevice;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "unit_id")
    private Vehicle vehicle;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "maintain_type_id")
    private MaintainType type;

    public void copyTo(Maintain copy) {
        super.copyTo(copy);
        copy.setEngineer(engineer);
        copy.setTrackingDevice(trackingDevice);
        copy.setVehicle(vehicle);
        copy.setMaintainType(type);
        copy.setDate(date);
    }

    public SecurityUser getEngineer() {
        return engineer;
    }

    public void setEngineer(SecurityUser engineer) {
        this.engineer = engineer;
    }

    public TrackingDevice getTrackingDevice() {
        return trackingDevice;
    }

    public void setTrackingDevice(TrackingDevice trackingDevice) {
        this.trackingDevice = trackingDevice;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public MaintainType getMaintainType() {
        return type;
    }

    public void setMaintainType(MaintainType maintainType) {
        this.type = maintainType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
