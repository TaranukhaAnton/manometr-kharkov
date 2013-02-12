package com.sp.dto.report;

import com.sp.model.BaseVehicle;
import com.sp.model.MileageRecord;

import java.util.ArrayList;
import java.util.List;

public class RepMileageSection  {
    private List<MileageRecord> recordlist;
    private float totalDistanceForFleet;
    private BaseVehicle vehicle;

    public float getTotalDistance() {
        float totalDistance = 0;
        for (MileageRecord record : recordlist) {
            totalDistance += record.getDistance();
        }
        return totalDistance;
    }

    // For Flex serialization
    public void setTotalDistance(float totalDistance) {
    }

    public List<MileageRecord> getRecordlist() {
        return recordlist;
    }

    public void setRecordlist(List<MileageRecord> recordlist) {
        this.recordlist = recordlist;
    }

    public BaseVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(BaseVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public RepMileageSection(BaseVehicle vehicle) {
        this.vehicle = vehicle;
         recordlist = new ArrayList<MileageRecord>();
    }

    public void addRecord(MileageRecord rec) {
		if (rec != null) {
			recordlist.add(rec);
		}
	}

    public float getTotalDistanceForFleet() {
        return totalDistanceForFleet;
    }

    public void setTotalDistanceForFleet(float totalDistanceForFleet) {
        this.totalDistanceForFleet = totalDistanceForFleet;
    }
}
