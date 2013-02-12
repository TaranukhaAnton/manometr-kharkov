package com.sp.dto.report;

import com.sp.model.BaseVehicle;
import com.sp.model.Vehicle;

public class VehicleReportSection {
	
    protected BaseVehicle vehicle;
    
    public VehicleReportSection() {
    }
    
    public VehicleReportSection(BaseVehicle vehicle) {
    	this.vehicle = vehicle;
    }

    public BaseVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

}
