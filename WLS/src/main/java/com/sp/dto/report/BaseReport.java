package com.sp.dto.report;

import com.sp.util.TimePeriod;

/**
 * User: andrew
 * Date: 26.08.2010
 */
public class BaseReport {
    private TimePeriod timePeriod;
    private String dateRangeStr;

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getDateRangeStr() {
        return dateRangeStr;
    }

    public void setDateRangeStr(String dateRangeStr) {
        this.dateRangeStr = dateRangeStr;
    }
}
