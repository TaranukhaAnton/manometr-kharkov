package com.sp.dto.report;

import java.util.Date;

/**
 * User: andrew
 * Date: 10.03.2010
 */
public class CurIncomingLogStat extends CurStat {
    private int incomingLogProcessorServiceId;
    private int processedMsgCount;
    private int journeyAssignedMsgCount;
    private int journeyCount;
    private Date maxJourneyStartDate;
    private Date maxJourneyEndDate;
    private int resellerId;

    private String boxTypeDescr;

    private int boxTypeId;
    private int totalDevices;
    private String collector;

    public String getBoxTypeDescr() {
        return boxTypeDescr;
    }

    public void setBoxTypeDescr(String boxTypeDescr) {
        this.boxTypeDescr = boxTypeDescr;
    }

    public int getJourneyCount() {
        return journeyCount;
    }

    public void setJourneyCount(int journeyCount) {
        this.journeyCount = journeyCount;
    }

    public Date getMaxJourneyStartDate() {
        return maxJourneyStartDate;
    }

    public void setMaxJourneyStartDate(Date maxJourneyStartDate) {
        this.maxJourneyStartDate = maxJourneyStartDate;
    }

    public Date getMaxJourneyEndDate() {
        return maxJourneyEndDate;
    }

    public void setMaxJourneyEndDate(Date maxJourneyEndDate) {
        this.maxJourneyEndDate = maxJourneyEndDate;
    }

    public void setProcessedMsgCount(int processedMsgCount) {
        this.processedMsgCount = processedMsgCount;
    }

    public int getProcessedMsgCount() {
        return processedMsgCount;
    }

    public int getJourneyAssignedMsgCount() {
        return journeyAssignedMsgCount;
    }

    public void setJourneyAssignedMsgCount(int journeyAssignedMsgCount) {
        this.journeyAssignedMsgCount = journeyAssignedMsgCount;
    }

    public int getIncomingLogProcessorServiceId() {
        return incomingLogProcessorServiceId;
    }

    public void setIncomingLogProcessorServiceId(int incomingLogProcessorServiceId) {
        this.incomingLogProcessorServiceId = incomingLogProcessorServiceId;
    }

    public int getResellerId() {
        return resellerId;
    }

    public void setResellerId(int resellerId) {
        this.resellerId = resellerId;
    }

    public int getBoxTypeId() {
        return boxTypeId;
    }

    public void setBoxTypeId(int boxTypeId) {
        this.boxTypeId = boxTypeId;
    }

    public int getTotalDevices() {
        return totalDevices;
    }

    public void setTotalDevices(int totalDevices) {
        this.totalDevices = totalDevices;
    }

    public String getCollector() {
        return collector;
    }

    public void setCollector(String collector) {
        this.collector = collector;
    }

    public String getPort()
    {
        return (collector != null) ? collector.substring(collector.indexOf(":")+1) : null;
    }

    public void setPort(String port)
    {
        this.port = port;  
    }

    private String port;
    public void copyTo(CurIncomingLogStat copy) {
        super.copyTo(copy);
        copy.incomingLogProcessorServiceId = incomingLogProcessorServiceId;
        copy.processedMsgCount = processedMsgCount;
        copy.journeyAssignedMsgCount = journeyAssignedMsgCount;
        copy.journeyCount = journeyCount;
        copy.maxJourneyStartDate = maxJourneyStartDate;
        copy.maxJourneyEndDate = maxJourneyEndDate;
        copy.resellerId = resellerId;
        copy.boxTypeDescr = boxTypeDescr;
        copy.boxTypeId = boxTypeId;
        copy.totalDevices = totalDevices;
        copy.boxTypeId = boxTypeId;
        copy.collector = collector;
    }

    @Override
    public String toString() {
        return "CurIncomingLogStat{" +
                "boxTypeDescr='" + boxTypeDescr + '\'' +
                ", incomingLogProcessorServiceId=" + incomingLogProcessorServiceId +
                ", processedMsgCount=" + processedMsgCount +
                ", journeyAssignedMsgCount=" + journeyAssignedMsgCount +
                ", journeyCount=" + journeyCount +
                ", maxJourneyStartDate=" + maxJourneyStartDate +
                ", maxJourneyEndDate=" + maxJourneyEndDate +
                ", resellerId=" + resellerId +
                ", boxTypeId=" + boxTypeId +
                ", totalDevices=" + totalDevices +
                ", collector=" + collector +
                super.toString() + '}';
    }
}
