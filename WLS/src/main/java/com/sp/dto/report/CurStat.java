package com.sp.dto.report;

import com.sp.model.EnumModel;

import java.util.Date;

/**
 * User: andrew
 * Date: 16.11.2009
 */
public class CurStat extends EnumModel {
    private int msgCount;
    private int nodeCount;
    private Date maxMsgDate;

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public Date getMaxMsgDate() {
        return maxMsgDate;
    }

    public void setMaxMsgDate(Date maxMsgDate) {
        this.maxMsgDate = maxMsgDate;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public void copyTo(CurStat copy) {
        super.copyTo(copy);
        copy.msgCount = msgCount;
        copy.nodeCount = nodeCount;
        copy.maxMsgDate = maxMsgDate;
    }

    @Override
    public String toString() {
        return "CurStat{" +
                "maxMsgDate=" + maxMsgDate +
                ", msgCount=" + msgCount +
                ", nodeCount=" + nodeCount +
                super.toString() + '}';
    }
}
