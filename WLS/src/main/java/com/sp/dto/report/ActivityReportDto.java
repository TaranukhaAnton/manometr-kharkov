package com.sp.dto.report;

import com.sp.model.VehicleIncomingLogRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander
 */
public class ActivityReportDto {
    private String input1Name;
    private String input2Name;
    private String input3Name;
    private Integer boxTypeId;
    private String vehicleDescr;
    private List<ActivityLogRecord> activityLogRecordList = new ArrayList<ActivityLogRecord>();

    public ActivityReportDto(List<VehicleIncomingLogRecord> vehicleIncomingLogRecordList) {
        if (vehicleIncomingLogRecordList!=null && vehicleIncomingLogRecordList.size()>0){
            input1Name = vehicleIncomingLogRecordList.get(0).getInput1Name();
            input2Name = vehicleIncomingLogRecordList.get(0).getInput2Name();
            input3Name = vehicleIncomingLogRecordList.get(0).getInput3Name();
            vehicleDescr = vehicleIncomingLogRecordList.get(0).getVehicleDescr();
            boxTypeId = vehicleIncomingLogRecordList.get(0).getBoxTypeId();
            for (VehicleIncomingLogRecord logRecord : vehicleIncomingLogRecordList){
                activityLogRecordList.add(new ActivityLogRecord(logRecord));
            }
        }
    }

    public String getInput1Name() {
        return input1Name;
    }

    public void setInput1Name(String input1Name) {
        this.input1Name = input1Name;
    }

    public String getInput2Name() {
        return input2Name;
    }

    public void setInput2Name(String input2Name) {
        this.input2Name = input2Name;
    }

    public String getInput3Name() {
        return input3Name;
    }

    public void setInput3Name(String input3Name) {
        this.input3Name = input3Name;
    }

    public Integer getBoxTypeId() {
        return boxTypeId;
    }

    public void setBoxTypeId(Integer boxTypeId) {
        this.boxTypeId = boxTypeId;
    }

    public String getVehicleDescr() {
        return vehicleDescr;
    }

    public void setVehicleDescr(String vehicleDescr) {
        this.vehicleDescr = vehicleDescr;
    }

    public List<ActivityLogRecord> getActivityLogRecordList() {
        return activityLogRecordList;
    }

    public void setActivityLogRecordList(List<ActivityLogRecord> activityLogRecordList) {
        this.activityLogRecordList = activityLogRecordList;
    }
}
