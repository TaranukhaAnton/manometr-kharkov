package com.sp.dto.mobile;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 25.10.12
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class BaseListIncomingLogs extends MobileSerializableList {
    private List<MobileSerializableList> logList;
    private int count = 0;

    public List<MobileSerializableList> getLogList() {
        return logList;
    }

    public void setLogList(List<MobileSerializableList> logList) {
        this.logList = logList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BaseListIncomingLogs(List mobileDataList) {
        this.logList = mobileDataList;
        count = mobileDataList.size();
    }
}
