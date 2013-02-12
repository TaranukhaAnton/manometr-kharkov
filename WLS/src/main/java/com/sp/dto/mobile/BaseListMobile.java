package com.sp.dto.mobile;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg
 * Date: 29.08.12
 * Time: 14:40
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "userData")
public class BaseListMobile {
   
    private int count;
    private List<MobileSerializableList> mobileDataList;

    public BaseListMobile() {}

    public BaseListMobile(List<MobileSerializableList> mobileDataList) {
        this.mobileDataList = mobileDataList;
        this.count = mobileDataList.size();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MobileSerializableList> getMobileDataList() {
        return mobileDataList;
    }

    public void setMobileDataList(List<MobileSerializableList> mobileDataList) {
        this.mobileDataList = mobileDataList;
    }
}
