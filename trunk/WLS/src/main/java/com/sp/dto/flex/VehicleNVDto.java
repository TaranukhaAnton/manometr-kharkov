package com.sp.dto.flex;

public class VehicleNVDto
{
    private int id;
    private String description;
    private String registrationNumber;
    private String type;
    private String fleet;
    private String vehicleBase;
    private boolean networkActive;
    private boolean networkArchived;
    private boolean deleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFleet() {
        return fleet;
    }

    public void setFleet(String fleet) {
        this.fleet = fleet;
    }

    public String getVehicleBase() {
        return vehicleBase;
    }

    public void setVehicleBase(String vehicleBase) {
        this.vehicleBase = vehicleBase;
    }

    public boolean isNetworkActive() {
        return networkActive;
    }

    public void setNetworkActive(boolean networkActive) {
        this.networkActive = networkActive;
    }

    public boolean isNetworkArchived() {
        return networkArchived;
    }

    public void setNetworkArchived(boolean networkArchived) {
        this.networkArchived = networkArchived;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
