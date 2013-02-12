package com.sp.dto.mobile;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 18.10.12
 * Time: 11:25
 * To change this template use File | Settings | File Templates.
 */
public class BaseListJourneys extends MobileSerializableList {
    private List<MobileSerializableList> journeyList;
    private int count = 0;

    public List getJourneyList() {
        return journeyList;
    }

    public void setJourneyList(List journeyList) {
        this.journeyList = journeyList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BaseListJourneys(List mobileDataList) {
        this.journeyList = mobileDataList;
        count = mobileDataList.size();
    }
}
