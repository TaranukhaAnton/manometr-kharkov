package com.sp.dto.flex;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class CanEventDataFlx {
    private Set<String> sensorNames = new LinkedHashSet<String>();
    private List<CanLogRecordFlx> sensorValues = new ArrayList<CanLogRecordFlx>();
    private List<CanJourneyRecordFlx> journeyValues = new ArrayList<CanJourneyRecordFlx>();

    public Set<String> getSensorNames() {
        return sensorNames;
    }

    public void setSensorNames(Set<String> sensorNames) {
        this.sensorNames = sensorNames;
    }

    public List<CanLogRecordFlx> getSensorValues() {
        return sensorValues;
    }

    public void setSensorValues(List<CanLogRecordFlx> sensorValues) {
        this.sensorValues = sensorValues;
    }

    public List<CanJourneyRecordFlx> getJourneyValues() {
        return journeyValues;
    }

    public void setJourneyValues(List<CanJourneyRecordFlx> journeyValues) {
        this.journeyValues = journeyValues;
    }
}
