package com.sp.dto.report;

import com.sp.model.BaseVehicle;
import com.sp.model.TimeOnSiteRecord;
import com.sp.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RepTimeOnSiteSection extends VehicleReportSection {

    private int journeyCount;
	private List<TimeOnSiteRecord> recordlist;
    private Date day;

	public RepTimeOnSiteSection() {
		super();
		init();
	}

	public RepTimeOnSiteSection(BaseVehicle vehicle) {
		super(vehicle);
		init();
	}

	private void init() {
		recordlist = new ArrayList<TimeOnSiteRecord>();
	}

	public List<TimeOnSiteRecord> getRecordlist() {
		return recordlist;
	}

	public void setRecordlist(List<TimeOnSiteRecord> recordlist) {
		this.recordlist = recordlist;
	}
	
	public void addRecord(TimeOnSiteRecord rec) {
		if (rec != null) {
			recordlist.add(rec);
		}
	}


    public String getAverageTimeOnSite() {
        return Util.getTimeDelayStr(getAverageTimeOnSiteInSec());
    }

    public long getAverageTimeOnSiteInSec(){
        long sumInSeconds = 0;
        int count = 0;
        for (TimeOnSiteRecord record : recordlist) {
            if (record.getDepartureTime() == null) {
               continue;
            }
            count++;
            sumInSeconds += (record.getDepartureTime().getTime() - record.getArrivalTime().getTime()) / 1000;
        }
        if (count != 0) {
           return sumInSeconds / count;
        } else
            return  0;
    }

    public void setAverageTimeOnSite(String s){}
    public void setAverageTimeOnSiteInSec(long s){}
    
    public void setTotalInstancesOfTimeOnSite(int i) {}

    public int getTotalInstancesOfTimeOnSite() {
        return recordlist.size();
    }

    public int getJourneyCount() {
        return journeyCount;
    }

    public void setJourneyCount(int journeyCount) {
        this.journeyCount = journeyCount;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }
}
