package com.sp.dto.flex;

import com.sp.util.TimePeriod;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: paveliva
 * Date: 20.08.2009
 * Time: 13:16:59
 * To change this template use File | Settings | File Templates.
 */
public class UnitViewFlx {
    private TimePeriod period;
    private List<VehicleFlx> vehicles;
    private int journeyNumberMaxValue = 0;
    private int journeyDistanceMaxValue = 0;
    private String distanceMetric = "m";//"km"
    public TimePeriod getPeriod() {
        return period;
    }

    public void setPeriod(com.sp.util.TimePeriod period) {
        this.period = period;
    }

    public List<VehicleFlx> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleFlx> vehicles) {
        this.vehicles = vehicles;
        Collections.sort(this.vehicles, new Comparator<VehicleFlx>(){
            public int compare(VehicleFlx o1, VehicleFlx o2) {
                return o2.getNumberOfJourneys()-o1.getNumberOfJourneys();//desc 
        }});
        for (VehicleFlx v : vehicles) {
            if (this.journeyDistanceMaxValue < v.getDrivingDistance()) {
                this.journeyDistanceMaxValue = v.getDrivingDistance();
            }
            if (this.journeyNumberMaxValue < v.getNumberOfJourneys()) {
                this.journeyNumberMaxValue = v.getNumberOfJourneys();
            }
        }
    }

    public int getJourneyNumberMaxValue() {
        return journeyNumberMaxValue;
    }

    public void setJourneyNumberMaxValue(int journeyNumberMaxValue) {
        this.journeyNumberMaxValue = journeyNumberMaxValue;
    }

    public int getJourneyDistanceMaxValue() {
        return journeyDistanceMaxValue;
    }

    public void setJourneyDistanceMaxValue(int journeyDistanceMaxValue) {
        this.journeyDistanceMaxValue = journeyDistanceMaxValue;
    }

    public String getDistanceMetric() {
        return distanceMetric;
    }

    public void setDistanceMetric(String distanceMetric) {
        this.distanceMetric = distanceMetric;
    }
}
