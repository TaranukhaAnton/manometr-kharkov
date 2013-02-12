package com.sp.dto.report;

import java.util.List;
import java.util.Map;

/**
 * Created by Alexander
 */
public class SpeedingRepJourneySection {
    // key = journey ID, value = Map from SpeedingReportRecord class
    private Map<Integer,Map<Integer,Integer>> speedLimitMsgCountMap;
    private Map<Integer,Map<Integer,Double>> speedLimitMaxSpeedMap;
    private List<RepJourneySection> repJourneySections;

    // Fot alternate report
    private List<SpeedingRepOverspeedSection> speedingRepOverspeedSections;



    public Map<Integer,Map<Integer,Integer>> getSpeedLimitMsgCountMap() {
        return speedLimitMsgCountMap;
    }

    public void setSpeedLimitMsgCountMap(Map<Integer,Map<Integer,Integer>> speedLimitMsgCountMap) {
        this.speedLimitMsgCountMap = speedLimitMsgCountMap;
    }

    public Map<Integer,Map<Integer,Double>> getSpeedLimitMaxSpeedMap() {
        return speedLimitMaxSpeedMap;
    }

    public void setSpeedLimitMaxSpeedMap(Map<Integer,Map<Integer,Double>> speedLimitMaxSpeedMap) {
        this.speedLimitMaxSpeedMap = speedLimitMaxSpeedMap;
    }

    public List<RepJourneySection> getRepJourneySections() {
        return repJourneySections;
    }

    public void setRepJourneySections(List<RepJourneySection> repJourneySections) {
        this.repJourneySections = repJourneySections;
    }

    public List<SpeedingRepOverspeedSection> getSpeedingRepOverspeedSections() {
        return speedingRepOverspeedSections;
    }

    public void setSpeedingRepOverspeedSections(List<SpeedingRepOverspeedSection> speedingRepOverspeedSections) {
        this.speedingRepOverspeedSections = speedingRepOverspeedSections;
    }
}
