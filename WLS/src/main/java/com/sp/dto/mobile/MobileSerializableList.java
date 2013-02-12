package com.sp.dto.mobile;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg
 * Date: 29.08.12
 * Time: 14:43
 * To change this template use File | Settings | File Templates.
 */
public class MobileSerializableList {
    
    protected int count;

    MobileSerializableList() {}
    
    MobileSerializableList(List<MobileSerializableList> elements) {
        this.count = elements.size();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
