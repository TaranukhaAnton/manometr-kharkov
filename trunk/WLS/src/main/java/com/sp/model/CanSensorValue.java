package com.sp.model;

import javax.persistence.*;

/**
 * User: andrew
 * Date: 09.01.2010
 */
@Entity
@Table(name = "can_sensor_value")
public class CanSensorValue extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "can_log_id")
    private CanLogRecord canLogRecord;

    @Column(name = "sensor_value")
    private Integer sensorValue;

    @Column(name = "sensor_name", insertable = false)
    private String sensorName;
    
    @Column(name = "sensor_index")
    private int sensorIndex;

    public CanSensorValue(CanLogRecord canLogRecord) {
        this.canLogRecord = canLogRecord;
    }

    public CanSensorValue() {
    }

    public void copyTo(CanSensorValue copy) {
        super.copyTo(copy);
        copy.canLogRecord = canLogRecord;
        copy.sensorValue = sensorValue;
        copy.sensorName = sensorName;
    }

    public CanLogRecord getCanLogRecord() {
        return canLogRecord;
    }

    public void setCanLogRecord(CanLogRecord canLogRecord) {
        this.canLogRecord = canLogRecord;
    }

    public Integer getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(Integer sensorValue) {
        this.sensorValue = sensorValue;
    }

    @Override
    public String toString() {
        return "CanSensorValue{" +
                ", sensorValue=" + sensorValue +
                '}';
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public int getSensorIndex() {
        return sensorIndex;
    }

    public void setSensorIndex(int sensorIndex) {
        this.sensorIndex = sensorIndex;
    }
}