package com.sp.dto.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: windows7
 * Date: 20.11.12
 * Time: 19:01
 * To change this template use File | Settings | File Templates.
 */
public class RepDriverLeagueTable {
    List<BaseDriverLeagueTableRecord> greenDrivingNotAllowedRecordList = new ArrayList<BaseDriverLeagueTableRecord>();
    List<DriverLeagueTableRecord> greenDrivingAllowedRecordList = new ArrayList<DriverLeagueTableRecord>();
    Map<String,List<String>> driverVehicleListMap = new HashMap<String, List<String>>();

    public RepDriverLeagueTable(List<BaseDriverLeagueTableRecord> greenDrivingNotAllowedRecordList, List<DriverLeagueTableRecord> greenDrivingAllowedRecordList, Map<String, List<String>> driverVehicleListMap) {
        // TODO REMOVE

        //DriverLeagueTableRecord r = greenDrivingAllowedRecordList.get(0);

//        for(int i=0;i<40;i++){
//            DriverLeagueTableRecord t = new DriverLeagueTableRecord();
//            t.setDriverName("dfsfds");
//            t.setIndex(String.valueOf(i));
//            this.greenDrivingAllowedRecordList.add(t);
//        }
//        for(int i=0;i<40;i++){
//            BaseDriverLeagueTableRecord d = new BaseDriverLeagueTableRecord();
//            d.setDriverName("dfasdasd");
//            d.setIndex(String.valueOf(i));
//            d.setVehicleReg("fds");
//            this.greenDrivingNotAllowedRecordList.add(d);
//        }
//

        this.greenDrivingNotAllowedRecordList = greenDrivingNotAllowedRecordList;
        this.greenDrivingAllowedRecordList = greenDrivingAllowedRecordList;
        this.driverVehicleListMap = driverVehicleListMap;
    }

    public List<BaseDriverLeagueTableRecord> getGreenDrivingNotAllowedRecordList() {
        return greenDrivingNotAllowedRecordList;
    }

    public void setGreenDrivingNotAllowedRecordList(List<BaseDriverLeagueTableRecord> greenDrivingNotAllowedRecordList) {
        this.greenDrivingNotAllowedRecordList = greenDrivingNotAllowedRecordList;
    }

    public List<DriverLeagueTableRecord> getGreenDrivingAllowedRecordList() {
        return greenDrivingAllowedRecordList;
    }

    public void setGreenDrivingAllowedRecordList(List<DriverLeagueTableRecord> greenDrivingAllowedRecordList) {
        this.greenDrivingAllowedRecordList = greenDrivingAllowedRecordList;
    }

    public Map<String, List<String>> getDriverVehicleListMap() {
        return driverVehicleListMap;
    }

    public void setDriverVehicleListMap(Map<String, List<String>> driverVehicleListMap) {
        this.driverVehicleListMap = driverVehicleListMap;
    }

}
