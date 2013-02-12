package com.sp.util;

import com.sp.model.CanJourneyValue;

import java.util.Comparator;


public class CanJourneyValuesComparator implements Comparator<CanJourneyValue> {
    
    public int compare(CanJourneyValue o1,CanJourneyValue o2) {
        return o1.getSensorIndex().compareTo(o2.getSensorIndex());
    }


}
