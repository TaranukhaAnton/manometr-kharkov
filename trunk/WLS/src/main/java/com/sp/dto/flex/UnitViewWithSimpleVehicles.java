package com.sp.dto.flex;

import com.sp.dto.VehicleDto;
import com.sp.model.EnumModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Alexander
 */
public class UnitViewWithSimpleVehicles {
    private Set<SimpleVehicle> groupVehicles = new HashSet<SimpleVehicle>();
    private EnumModel vehicleView = new EnumModel();

    public UnitViewWithSimpleVehicles(EnumModel unitView, List<VehicleDto> vehicleList) {
        unitView.copyTo(vehicleView);
        for (VehicleDto vehicle : vehicleList){
             SimpleVehicle simpleVehicle = new SimpleVehicle(vehicle);
             groupVehicles.add(simpleVehicle);
        }
    }

    public Set<SimpleVehicle> getGroupVehicles() {
        return groupVehicles;
    }

    public EnumModel getVehicleView() {
        return vehicleView;
    }

    public void setGroupVehicles(Set<SimpleVehicle> groupVehicles) {
        this.groupVehicles = groupVehicles;
    }

    public void setVehicleView(EnumModel vehicleView) {
        this.vehicleView = vehicleView;
    }



}
