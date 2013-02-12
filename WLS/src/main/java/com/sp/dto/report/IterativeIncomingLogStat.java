package com.sp.dto.report;

import java.util.Date;

/**
 * Created by Alexander
 */
public class IterativeIncomingLogStat {
    private String nodeId;
    private Date recDate;
    private int unitId;
    private String unitRegNumber;
    private String unitBoxTypeDescr;
    private int maxIncomingLogId;
    private int incomingLogsCount;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Date getRecDate() {
        return recDate;
    }

    public void setRecDate(Date recDate) {
        this.recDate = recDate;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getMaxIncomingLogId() {
        return maxIncomingLogId;
    }

    public void setMaxIncomingLogId(int maxIncomingLogId) {
        this.maxIncomingLogId = maxIncomingLogId;
    }

    public int getIncomingLogsCount() {
        return incomingLogsCount;
    }

    public void setIncomingLogsCount(int incomingLogsCount) {
        this.incomingLogsCount = incomingLogsCount;
    }

    public String getUnitRegNumber() {
        return unitRegNumber;
    }

    public void setUnitRegNumber(String unitRegNumber) {
        this.unitRegNumber = unitRegNumber;
    }

    public String getUnitBoxTypeDescr() {
        return unitBoxTypeDescr;
    }

    public void setUnitBoxTypeDescr(String unitBoxTypeDescr) {
        this.unitBoxTypeDescr = unitBoxTypeDescr;
    }

    
}
