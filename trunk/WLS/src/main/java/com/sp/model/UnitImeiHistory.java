package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Alexander
 */

@Entity
@Table(name = "unit_imei_history")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class UnitImeiHistory extends BaseModel {
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "imei_id")
    private BaseTrackingDevice trackingDevice;

    @Column(name = "unit_id")
    private int unitId;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    public void copyTo(UnitImeiHistory copy) {
		super.copyTo(copy);
        copy.trackingDevice = trackingDevice;
        copy.unitId = unitId;
        copy.startDate = startDate;
        copy.endDate = endDate;
    }

    public BaseTrackingDevice getTrackingDevice() {
        return trackingDevice;
    }

    public void setTrackingDevice(BaseTrackingDevice trackingDevice) {
        this.trackingDevice = trackingDevice;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
