package com.sp.dto.flex;

/**
 * Created by Alexander
 */
public class PickListVehicleDto {
    private String label;
    private int value;
    private String lockedReportsGranting;

    public PickListVehicleDto(String label, int value, String lockedReportsGranting) {
        this.label = label;
        this.value = value;
        this.lockedReportsGranting = lockedReportsGranting;
    }

    public PickListVehicleDto() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLockedReportsGranting() {
        return lockedReportsGranting;
    }

    public void setLockedReportsGranting(String lockedReportsGranting) {
        this.lockedReportsGranting = lockedReportsGranting;
    }
}
