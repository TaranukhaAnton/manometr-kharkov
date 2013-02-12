package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 06.04.2009
 * Time: 13:26:05
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "tbincoming_phonelog")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class PhoneIncomingLogRecord extends BaseIncomingLogRecord {
    @Column(name = "shedule_id")
    private Integer scheduleId;

    private String state;

    @Column(name = "phone_id")
    private Integer phoneId;

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Integer phoneId) {
        this.phoneId = phoneId;
    }

    public void copyTo(PhoneIncomingLogRecord copy) {
        super.copyTo(copy);
        copy.scheduleId = scheduleId;
        copy.phoneId = phoneId;
        copy.state = state;
    }

}
