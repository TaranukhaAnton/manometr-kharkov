package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by IntelliJ IDEA.
 * User: Ananda
 * Date: Apr 3, 2009
 * Time: 4:57:40 PM
 */
@Entity
@Table(name = "phone_schedule_details")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class PhoneScheduleDetails extends BaseModel {


    @Column(name = "day_of_week")
    private String dayOfWeek;


    @Transient
    private String passiveStartEndTimeMinutes;

    @Transient
    private String passiveStartEndTimeHour;

    @Transient
    private String passiveEndStartTimeMinutes;

    @Transient
    private String passiveEndStartTimeHour;

    @Column(name = "active_start_time_hours")
    private int activeStartTimeHour;

    @Column(name = "active_start_time_minutes")
    private int activeStartTimeMinutes;

    @Column(name = "active_end_time_hours")
    private int activeEndTimeHour;

    @Column(name = "active_end_time_minutes")
    private int activeEndTimeMinutes;

    @Column(name = "frequency_active")
    private long frequencyActive;

    @Column(name = "frequency_passive")
    private long frequencyPassive;

    @Column(name = "activity_passive")
    private boolean activityPassive;

    @Column(name = "activity_active")
    private boolean activityActive;

//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(name = "scheduleId")
//    private PhoneSchedule phoneSchedule;

    @Column(name = "schedule_id")
    private int scheduleId;

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

//     public PhoneSchedule getPhoneSchedule() {
//        return phoneSchedule;
//    }
//
//    public void setPhoneSchedule(PhoneSchedule phoneSchedule) {
//        this.phoneSchedule = phoneSchedule;
//    }

     public long getFrequencyPassive() {
        return frequencyPassive;
    }

    public boolean getActivityPassive() {
        return activityPassive;
    }

    public void setActivityPassive(boolean activityPassive) {
        this.activityPassive = activityPassive;
    }

    public void setFrequencyPassive(long frequencyPassive) {
        this.frequencyPassive = frequencyPassive;
    }


    public long getFrequencyActive() {
        return frequencyActive;
    }

    public boolean getActivityActive() {
        return activityActive;
    }

    public void setActivityActive(boolean activityActive) {
        this.activityActive = activityActive;
    }

    public void setFrequencyActive(long frequencyActive) {
        this.frequencyActive = frequencyActive;
    }
   
    public void copyTo(PhoneScheduleDetails copy) {
        super.copyTo(copy);
        copy.passiveStartEndTimeMinutes = passiveStartEndTimeMinutes;
        copy.passiveStartEndTimeHour = passiveStartEndTimeHour;
        copy.passiveEndStartTimeMinutes = passiveEndStartTimeMinutes;
        copy.passiveEndStartTimeHour = passiveEndStartTimeHour;
        copy.dayOfWeek = dayOfWeek;
        copy.activeStartTimeHour = activeStartTimeHour;
        copy.activeStartTimeMinutes = activeStartTimeMinutes;
        copy.activeEndTimeHour = activeEndTimeHour;
        copy.activeEndTimeMinutes = activeEndTimeMinutes;
        copy.frequencyActive = frequencyActive;
        copy.frequencyPassive=frequencyPassive;
        copy.activityPassive = activityPassive;
        copy.activityActive = activityActive;
        //copy.phoneSchedule = phoneSchedule;
        copy.scheduleId = scheduleId;

    }

    public int getActiveStartTimeHour() {
        return activeStartTimeHour;
    }

    public void setActiveStartTimeHour(int activeStartTimeHour) {
        this.activeStartTimeHour = activeStartTimeHour;
    }

    public int getActiveStartTimeMinutes() {
        return activeStartTimeMinutes;
    }

    public void setActiveStartTimeMinutes(int activeStartTimeMinutes) {
        this.activeStartTimeMinutes = activeStartTimeMinutes;
    }


    public int getActiveEndTimeHour() {
        return activeEndTimeHour;
    }

    public void setActiveEndTimeHour(int activeEndTimeHour) {
        this.activeEndTimeHour = activeEndTimeHour;
    }

    public int getActiveEndTimeMinutes() {
        return activeEndTimeMinutes;
    }
   
    public void setActiveEndTimeMinutes(int activeEndTimeMinutes) {
        this.activeEndTimeMinutes = activeEndTimeMinutes;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }
    public String getPassiveStartEndTimeMinutes() {
            return passiveStartEndTimeMinutes;
        }

        public void setPassiveStartEndTimeMinutes(String passiveStartEndTimeMinutes) {
            this.passiveStartEndTimeMinutes = passiveStartEndTimeMinutes;
        }

        public String getPassiveStartEndTimeHour() {
            return passiveStartEndTimeHour;
        }

        public void setPassiveStartEndTimeHour(String passiveStartEndTimeHour) {
            this.passiveStartEndTimeHour = passiveStartEndTimeHour;
        }

        public String getPassiveEndStartTimeMinutes() {
            return passiveEndStartTimeMinutes;
        }

        public void setPassiveEndStartTimeMinutes(String passiveEndStartTimeMinutes) {
            this.passiveEndStartTimeMinutes = passiveEndStartTimeMinutes;
        }

        public String getPassiveEndStartTimeHour() {
            return passiveEndStartTimeHour;
        }

        public void setPassiveEndStartTimeHour(String passiveEndStartTimeHour) {
            this.passiveEndStartTimeHour = passiveEndStartTimeHour;
        }
    
}
