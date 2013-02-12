package com.sp.dto.report;

/**
 * Created with IntelliJ IDEA.
 * User: windows7
 * Date: 20.11.12
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public class BaseDriverLeagueTableRecord {
    private String index;
    private int unitId;
    private String driverName;
    private String vehicleReg;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehicleReg() {
        return vehicleReg;
    }

    public void setVehicleReg(String vehicleReg) {
        this.vehicleReg = vehicleReg;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
}
