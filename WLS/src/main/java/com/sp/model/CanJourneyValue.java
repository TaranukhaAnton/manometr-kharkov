package com.sp.model;

import javax.persistence.*;

@Entity
@Table(name = "can_journey_value")
public class CanJourneyValue extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "journey_id")
    private Journey journey;

    @Column(name = "sensor_index")
    private int sensorIndex;

    @Column(name = "sensor_value")
    private int sensorValue;

    @Column(name = "sensor_descr")
    private String sensorDescr;

    @Column(name = "sensor_name")
    private String sensorName;

    public void copyTo(CanJourneyValue copy) {
        super.copyTo(copy);
        copy.journey = journey;
        copy.sensorIndex = sensorIndex;
        copy.sensorDescr = sensorDescr;
        copy.sensorName = sensorName;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    public Integer getSensorIndex() {
        return sensorIndex;
    }

    public void setSensorIndex(Integer sensorIndex) {
        this.sensorIndex = sensorIndex;
    }

    public int getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(int sensorValue) {
        this.sensorValue = sensorValue;
    }

    public String getSensorDescr() {
        return sensorDescr;
    }

    public void setSensorDescr(String sensorDescr) {
        this.sensorDescr = sensorDescr;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }
}
