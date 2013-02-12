package com.sp.dto.flex;

import com.sp.model.BaseTrackingDevice;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg
 * Date: 17.02.12
 * Time: 17:58
 * To change this template use File | Settings | File Templates.
 */
public class SnailTrailDeviceDto {

    private boolean maxAccelerationForceAllowed;
    private float maxAccelerationForceOrangeVal;
    private float maxAccelerationForceRedVal;

    private boolean maxBrakingForceAllowed;
    private float maxBrakingForceOrangeVal;
    private float maxBrakingForceRedVal;

    private boolean maxCorneringAllowed;
    private int maxCorneringOrangeVal;
    private int maxCorneringRedVal;

    private boolean maxAllowedSpeedAllowed;
    private int maxAllowedSpeedOrangeVal;
    private int maxAllowedSpeedRedVal;
    
    private boolean greenDrivingAllowed;
    private boolean overspeedAllowed;

    public SnailTrailDeviceDto(BaseTrackingDevice baseTrackingDevice) {
        this.maxAccelerationForceAllowed = baseTrackingDevice.isMaxAccelerationForceAllowed();
        this.maxAccelerationForceOrangeVal = baseTrackingDevice.getMaxAccelerationForceOrangeVal();
        this.maxAccelerationForceRedVal = baseTrackingDevice.getMaxAccelerationForceRedVal();
        this.maxBrakingForceAllowed = baseTrackingDevice.isMaxBrakingForceAllowed();
        this.maxBrakingForceOrangeVal = baseTrackingDevice.getMaxBrakingForceOrangeVal();
        this.maxBrakingForceRedVal = baseTrackingDevice.getMaxBrakingForceRedVal();
        this.maxCorneringAllowed = baseTrackingDevice.isMaxCorneringAllowed();
        this.maxCorneringOrangeVal = baseTrackingDevice.getMaxCorneringOrangeVal();
        this.maxCorneringRedVal = baseTrackingDevice.getMaxCorneringRedVal();
        this.maxAllowedSpeedAllowed = baseTrackingDevice.isMaxAllowedSpeedAllowed();
        this.maxAllowedSpeedOrangeVal = baseTrackingDevice.getMaxAllowedSpeedOrangeVal();
        this.maxAllowedSpeedRedVal = baseTrackingDevice.getMaxAllowedSpeedRedVal();
        this.overspeedAllowed = baseTrackingDevice.isOverspeedAllowed();
        this.greenDrivingAllowed = baseTrackingDevice.isGreenDrivingAllowed();
    }

    public boolean isMaxAccelerationForceAllowed() {
        return maxAccelerationForceAllowed;
    }

    public void setMaxAccelerationForceAllowed(boolean maxAccelerationForceAllowed) {
        this.maxAccelerationForceAllowed = maxAccelerationForceAllowed;
    }

    public float getMaxAccelerationForceOrangeVal() {
        return maxAccelerationForceOrangeVal;
    }

    public void setMaxAccelerationForceOrangeVal(float maxAccelerationForceOrangeVal) {
        this.maxAccelerationForceOrangeVal = maxAccelerationForceOrangeVal;
    }

    public float getMaxAccelerationForceRedVal() {
        return maxAccelerationForceRedVal;
    }

    public void setMaxAccelerationForceRedVal(float maxAccelerationForceRedVal) {
        this.maxAccelerationForceRedVal = maxAccelerationForceRedVal;
    }

    public boolean isMaxBrakingForceAllowed() {
        return maxBrakingForceAllowed;
    }

    public void setMaxBrakingForceAllowed(boolean maxBrakingForceAllowed) {
        this.maxBrakingForceAllowed = maxBrakingForceAllowed;
    }

    public float getMaxBrakingForceOrangeVal() {
        return maxBrakingForceOrangeVal;
    }

    public void setMaxBrakingForceOrangeVal(float maxBrakingForceOrangeVal) {
        this.maxBrakingForceOrangeVal = maxBrakingForceOrangeVal;
    }

    public float getMaxBrakingForceRedVal() {
        return maxBrakingForceRedVal;
    }

    public void setMaxBrakingForceRedVal(float maxBrakingForceRedVal) {
        this.maxBrakingForceRedVal = maxBrakingForceRedVal;
    }

    public boolean isMaxCorneringAllowed() {
        return maxCorneringAllowed;
    }

    public void setMaxCorneringAllowed(boolean maxCorneringAllowed) {
        this.maxCorneringAllowed = maxCorneringAllowed;
    }

    public int getMaxCorneringOrangeVal() {
        return maxCorneringOrangeVal;
    }

    public void setMaxCorneringOrangeVal(int maxCorneringOrangeVal) {
        this.maxCorneringOrangeVal = maxCorneringOrangeVal;
    }

    public int getMaxCorneringRedVal() {
        return maxCorneringRedVal;
    }

    public void setMaxCorneringRedVal(int maxCorneringRedVal) {
        this.maxCorneringRedVal = maxCorneringRedVal;
    }

    public boolean isMaxAllowedSpeedAllowed() {
        return maxAllowedSpeedAllowed;
    }

    public void setMaxAllowedSpeedAllowed(boolean maxAllowedSpeedAllowed) {
        this.maxAllowedSpeedAllowed = maxAllowedSpeedAllowed;
    }

    public int getMaxAllowedSpeedOrangeVal() {
        return maxAllowedSpeedOrangeVal;
    }

    public void setMaxAllowedSpeedOrangeVal(int maxAllowedSpeedOrangeVal) {
        this.maxAllowedSpeedOrangeVal = maxAllowedSpeedOrangeVal;
    }

    public int getMaxAllowedSpeedRedVal() {
        return maxAllowedSpeedRedVal;
    }

    public void setMaxAllowedSpeedRedVal(int maxAllowedSpeedRedVal) {
        this.maxAllowedSpeedRedVal = maxAllowedSpeedRedVal;
    }

    public boolean isGreenDrivingAllowed() {
        return greenDrivingAllowed;
    }

    public void setGreenDrivingAllowed(boolean greenDrivingAllowed) {
        this.greenDrivingAllowed = greenDrivingAllowed;
    }

    public boolean isOverspeedAllowed() {
        return overspeedAllowed;
    }

    public void setOverspeedAllowed(boolean overspeedAllowed) {
        this.overspeedAllowed = overspeedAllowed;
    }

}
