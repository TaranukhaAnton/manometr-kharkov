package com.sp.dto;

import com.sp.SpContext;
import com.sp.model.BaseTrackingDevice;
import com.sp.model.Vehicle;

public class SearchResultDto {
    private String vin;
    private String registrationNumber;
    private int vehicleId;

    private Integer accountId;
    private String accountName;

    private Integer groupId;
    private String groupName;
    private String information;
    private VehicleDto vehicleDto;

    public SearchResultDto(Integer accountId, String accountName, Integer groupId, String groupName, String information, Vehicle vehicle) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.groupId = groupId;
        this.groupName = groupName;
        this.information = information;
        BaseTrackingDevice trackingDevice = SpContext.getCachedDeviceByVehicleId(vehicle.getId());
        this.vehicleDto = new VehicleDto(vehicle, trackingDevice, false, "");
        vin = vehicle.getVin();
        registrationNumber = vehicle.getRegistrationNumber();
        vehicleId = vehicle.getId();
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public VehicleDto getVehicleDto() {
        return vehicleDto;
    }

    public void setVehicleDto(VehicleDto vehicleDto) {
        this.vehicleDto = vehicleDto;
    }
}
