package com.sp.dto.report;

import com.sp.util.Util;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;


public class RepTimeOnSiteLocationSection {
    private List<RepTimeOnSiteUnitSubSection> recordList = new ArrayList<RepTimeOnSiteUnitSubSection>();
    private String location;
    private String postcode;
    private String aoiPoiDescr;
    private int totalVisits;
    private long totalTime;
    private String latLon;


    public int getTotalVisits() {
        return totalVisits;
    }

    public void setTotalVisits(int totalVisits) {
        this.totalVisits = totalVisits;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAoiPoiDescr() {
        return aoiPoiDescr;
    }

    public void setAoiPoiDescr(String aoiPoiDescr) {
        this.aoiPoiDescr = aoiPoiDescr;
    }

    public String getLatLon() {
        return latLon;
    }

    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public List<RepTimeOnSiteUnitSubSection> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RepTimeOnSiteUnitSubSection> recordList) {
        this.recordList = recordList;
    }

    public String getSite() {
        if (aoiPoiDescr == null && latLon == null) {
            return "";
        } else if(aoiPoiDescr == null) {
            return latLon;
        } else {
            return StringEscapeUtils.escapeHtml(aoiPoiDescr);
        }
    }

    public String getAddressStr() {
        if (location != null && postcode != null) {
            return StringEscapeUtils.escapeHtml(location + ", " + postcode);
        } else if (location != null) {
            return location;
        } else if (postcode != null) {
            return postcode;
        } else {
            return "";
        }
    }

    public void incrementTotalVisit() {
        totalVisits++;
    }

    public void addDuration(long duration) {
        this.totalTime += duration;
    }

    public String getTotalTimeStr() {
        return Util.getTimeDelayStrAlternate(totalTime, true);
    }
}
