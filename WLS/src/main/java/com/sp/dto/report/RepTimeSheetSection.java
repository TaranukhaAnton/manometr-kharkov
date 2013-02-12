package com.sp.dto.report;

import java.util.ArrayList;
import java.util.List;

/**
 * User: andrew
 * Date: 23.08.2010
 */
public class RepTimeSheetSection {
    private String vehReg;

    private List<TimeSheetJourneyRecord> timeSheetJourneyRecords = new ArrayList<TimeSheetJourneyRecord>();

    public List<TimeSheetJourneyRecord> getTimeSheetJourneyRecords() {
        return timeSheetJourneyRecords;
    }

    public void setTimeSheetJourneyRecords(List<TimeSheetJourneyRecord> timeSheetJourneyRecords) {
        this.timeSheetJourneyRecords = timeSheetJourneyRecords;
    }

    public String getVehReg() {
        return vehReg;
    }

    public void setVehReg(String vehReg) {
        this.vehReg = vehReg;
    }
}
