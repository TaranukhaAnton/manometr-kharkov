package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * User: andrew
 * Date: 19.01.2010
 */
@Entity
@Table(name = "can_sensor_name")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class CanSensorName extends EnumModel {
    @ManyToOne
    @JoinColumn(name = "imei_id")
    private TrackingDevice trackingDevice;

    @Column(name = "sensor_index")
    private int sensorIndex;

    @Column(name = "is_custom")
    private boolean custom;

    @Column(name = "custom_function")
    private String customFunction;

    @Transient
    private String customSensorName;

    public CanSensorName(int sensorIndex) {
        this.sensorIndex = sensorIndex;
    }

    public CanSensorName() {
    }

    public void copyTo(CanSensorName copy) {
        super.copyTo(copy);
        copy.trackingDevice = trackingDevice;
        copy.sensorIndex = sensorIndex;
        copy.custom = custom;
        copy.customFunction = customFunction;
    }

    public TrackingDevice getTrackingDevice() {
        return trackingDevice;
    }

    public void setTrackingDevice(TrackingDevice trackingDevice) {
        this.trackingDevice = trackingDevice;
    }

    public int getSensorIndex() {
        return sensorIndex;
    }

    public void setSensorIndex(int sensorIndex) {
        this.sensorIndex = sensorIndex;
    }

    public String getCustomSensorName() {
        return customSensorName;
    }

    public void setCustomSensorName(String customSensorName) {
        this.customSensorName = customSensorName;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    public String getCustomFunction() {
        return customFunction;
    }

    public void setCustomFunction(String customFunction) {
        this.customFunction = customFunction;
    }

}
