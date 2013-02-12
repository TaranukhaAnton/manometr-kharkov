package com.sp.dto.report;

/**
 * Created by Alexander
 */
public class DrivingTimeDto {
    private String vehReg;
    private String driverName;
    private long drivingTime;
    private long stoppedTime;
    private long idlingTime;
    private long totalTime;

    public String getVehReg() {
        return vehReg;
    }

    public void setVehReg(String vehReg) {
        this.vehReg = vehReg;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public long getDrivingTime() {
        return drivingTime;
    }

    public void setDrivingTime(long drivingTime) {
        this.drivingTime = drivingTime;
    }

    public long getStoppedTime() {
        return stoppedTime;
    }

    public void setStoppedTime(long stoppedTime) {
        this.stoppedTime = stoppedTime;
    }

    public long getIdlingTime() {
        return idlingTime;
    }

    public void setIdlingTime(long idlingTime) {
        this.idlingTime = idlingTime;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

}
