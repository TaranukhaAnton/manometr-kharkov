package com.sp.dto.flex;

import com.sp.dto.VehicleDto;
import com.sp.model.BaseModel;

/**
 * Created by Alexander
 */
 public class SimpleVehicle extends BaseModel {
    private String registrationNumber;

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public SimpleVehicle(VehicleDto vehicle) {
        setId(vehicle.getId());
        setRegistrationNumber(vehicle.getRegistrationNumber());
    }
}