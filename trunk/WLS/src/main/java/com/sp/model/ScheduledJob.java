package com.sp.model;

import com.sp.util.Constants;
import com.sp.util.Util;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "scheduled_job")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class ScheduledJob extends EnumModel {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private SecurityUser securityUser;

    @Column(name = "recurrence_pattern")
    private String recurrencePattern;

    @Column(name = "out_of_hours_week_map")
    private String outOfHoursWeekMap;


    @Column(name = "min_hours_utilisation")
    private String minHoursUtilisation;

    @Column(name = "start_time_hours")
    private short startTimeHours;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "days_filter")
    private Integer daysFilter;

    @Column(name = "start_time_mins")
    private short startTimeMins;

    @Column(name = "week_days")
    private String weekDays;

    @Column(name = "query_period_type")
    private String queryPeriodType;

    @Column(insertable = false, updatable = false)
    private Date timestamp;

    @Column(name = "last_generated_date")
    private Date lastGeneratedDate;

    @Column(name = "start_time_filter")
    private Time startTimeFilter;

    @Column(name = "end_time_filter")
    private Time endTimeFilter;

    @Column(name = "error_msg")
    private String errorMsg;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Vehicle.class,
            fetch = FetchType.EAGER
    )
    @JoinTable(name = "scheduled_job_unit",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "unit_id")
    )
    private Set<Vehicle> scheduledJobUnits;

    private String format;

    @Column(name = "exclude_weekends")
    private boolean excludeWeekends;

    @Column(name = "exclude_movements")
    private boolean excludeMovements;

    @Column(name = "is_dont_send_null_results_allowed")
    private boolean dontSendNullResultsAllowed;

    private short duration;

    private int speed;

    @Column(name = "poi_filter")
    private Integer poiFilter;

    @Column(name = "aoi_filter")
    private Integer aoiFilter;

    @Column(name = "speeding_percent")
    private Integer speedingPercent;

    @Column(name = "speeding_mph")
    private Integer speedingMph;

    @Column(name = "speeding_operation")
    private String speedingOperation;

    private int idling;

    @Column(name = "is_only_negative_behaviour")
    private boolean onlyNegativeBehaviour;

    @Transient
    private List<Integer> unitIds;

    public void copyTo(ScheduledJob copy) {
        super.copyTo(copy);
        copy.securityUser = securityUser;
        copy.recurrencePattern = recurrencePattern;
        copy.startTimeHours = startTimeHours;
        copy.startTimeMins = startTimeMins;
        copy.weekDays = weekDays;
        copy.queryPeriodType = queryPeriodType;
        copy.scheduledJobUnits = scheduledJobUnits;
        copy.timestamp = timestamp;
        copy.format = format;
        copy.excludeWeekends = excludeWeekends;
        copy.lastGeneratedDate = lastGeneratedDate;
        copy.errorMsg = errorMsg;
        copy.speed = speed;
        copy.excludeMovements = excludeMovements;
        copy.duration = duration;
        copy.poiFilter = poiFilter;
        copy.outOfHoursWeekMap = outOfHoursWeekMap;
        copy.unitIds = unitIds;
        copy.idling = idling;
        copy.daysFilter = daysFilter;
        copy.minHoursUtilisation = minHoursUtilisation;
        copy.aoiFilter = aoiFilter;
        copy.onlyNegativeBehaviour = onlyNegativeBehaviour;
        copy.endTimeFilter = endTimeFilter;
        copy.startTimeFilter = startTimeFilter;
        copy.speedingMph = speedingMph;
        copy.speedingPercent = speedingPercent;
        copy.speedingOperation = speedingOperation;
        copy.accountId = accountId;
        copy.dontSendNullResultsAllowed = dontSendNullResultsAllowed;
    }

    public SecurityUser getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public String getRecurrencePattern() {
        return recurrencePattern;
    }

    public List getWeekDaysList() {
        if (weekDays == null) {
            return null;
        }
        List res = new ArrayList();
        String days[] = weekDays.split(",");
        for (String day : days) {
            res.add(day);
        }

        return res;
    }

    public void setWeekDaysList(List days) {
        weekDays = "";
        String separator = "";
        for (String weekDay : Util.WEEK_DAYS) {
            for (Object day : days) {
                if (weekDay.equals(day.toString())) {
                    weekDays += separator + day.toString();
                    separator = ",";
                    break;
                }
            }
        }
    }

    public void setRecurrencePattern(String recurrencePattern) {
        this.recurrencePattern = recurrencePattern;
    }

    public short getStartTimeHours() {
        return startTimeHours;
    }

    public void setStartTimeHours(short startTimeHours) {
        this.startTimeHours = startTimeHours;
    }

    public short getStartTimeMins() {
        return startTimeMins;
    }

    public void setStartTimeMins(short startTimeMins) {
        this.startTimeMins = startTimeMins;
    }

    public String getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(String weekDays) {
        this.weekDays = weekDays;
    }

    public Set<Vehicle> getScheduledJobUnits() {
        return scheduledJobUnits;
    }

    public void setScheduledJobUnits(Set<Vehicle> scheduledJobUnits) {
        this.scheduledJobUnits = scheduledJobUnits;
    }

    public String getQueryPeriodType() {
        return queryPeriodType;
    }

    public void setQueryPeriodType(String queryPeriodType) {
        this.queryPeriodType = queryPeriodType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormat() {
        return format;
    }

    public Constants.RepFormat getRepFormat(){
        return Constants.RepFormat.valueOf(format);
    }

    public Constants.ReportType getReportType(){
        return Constants.ReportType.valueOf(getDescr());
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isExcludeWeekends() {
        return excludeWeekends;
    }

    public void setExcludeWeekends(boolean excludeWeekends) {
        this.excludeWeekends = excludeWeekends;
    }

    public Date getLastGeneratedDate() {
        return lastGeneratedDate;
    }

    public void setLastGeneratedDate(Date lastGeneratedDate) {
        this.lastGeneratedDate = lastGeneratedDate;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isExcludeMovements() {
        return excludeMovements;
    }

    public void setExcludeMovements(boolean excludeMovements) {
        this.excludeMovements = excludeMovements;
    }

    public short getDuration() {
        return duration;
    }

    public void setDuration(short duration) {
        this.duration = duration;
    }

    public Integer getPoiFilter() {
        return poiFilter;
    }

    public void setPoiFilter(Integer poiFilter) {
        this.poiFilter = poiFilter;
    }

	public void setUnitIds(List<Integer> unitIds) {
		this.unitIds = unitIds;
	}

	public List<Integer> getUnitIds() {
		return unitIds;
	}

    public int getIdling() {
        return idling;
    }

    public void setIdling(int idling) {
        this.idling = idling;
    }

    public Integer getAoiFilter() {
        return aoiFilter;
    }

    public void setAoiFilter(Integer aoiFilter) {
        this.aoiFilter = aoiFilter;
    }

    public Integer getDaysFilter() {
        return daysFilter;
    }

    public void setDaysFilter(Integer daysFilter) {
        this.daysFilter = daysFilter;
    }


    public boolean isOnlyNegativeBehaviour() {
        return onlyNegativeBehaviour;
    }

    public void setOnlyNegativeBehaviour(boolean onlyNegativeBehaviour) {
        this.onlyNegativeBehaviour = onlyNegativeBehaviour;
    }

    public Time getStartTimeFilter() {
        return startTimeFilter;
    }

    public void setStartTimeFilter(Time startTimeFilter) {
        this.startTimeFilter = startTimeFilter;
    }

    public Time getEndTimeFilter() {
        return endTimeFilter;
    }

    public void setEndTimeFilter(Time endTimeFilter) {
        this.endTimeFilter = endTimeFilter;
    }

    public Integer getSpeedingPercent() {
        return speedingPercent;
    }

    public void setSpeedingPercent(Integer speedingPercent) {
        this.speedingPercent = speedingPercent;
    }

    public Integer getSpeedingMph() {
        return speedingMph;
    }

    public void setSpeedingMph(Integer speedingMph) {
        this.speedingMph = speedingMph;
    }

    public String getSpeedingOperation() {
        return speedingOperation;
    }

    public void setSpeedingOperation(String speedingOperation) {
        this.speedingOperation = speedingOperation;
    }

    public boolean isDontSendNullResultsAllowed() {
        return dontSendNullResultsAllowed;
    }

    public void setDontSendNullResultsAllowed(boolean dontSendNullResultsAllowed) {
        this.dontSendNullResultsAllowed = dontSendNullResultsAllowed;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getMinHoursUtilisation() {
        return minHoursUtilisation;
    }

    public void setMinHoursUtilisation(String minHoursUtilisation) {
        this.minHoursUtilisation = minHoursUtilisation;
    }

    public String getOutOfHoursWeekMap() {
        return outOfHoursWeekMap;
    }

    public void setOutOfHoursWeekMap(String outOfHoursWeekMap) {
        this.outOfHoursWeekMap = outOfHoursWeekMap;
    }

    @Override
    public String toString() {
        return "ScheduledJob{" +
                "scheduledJobUnits=" + scheduledJobUnits +
                ", duration=" + duration +
                ", errorMsg='" + errorMsg + '\'' +
                ", excludeMovements=" + excludeMovements +
                ", excludeWeekends=" + excludeWeekends +
                ", format='" + format + '\'' +
                ", idling=" + idling +
                ", lastGeneratedDate=" + lastGeneratedDate +
                ", poiFilter=" + poiFilter +
                ", aoiFilter=" + aoiFilter +                
                ", queryPeriodType='" + queryPeriodType + '\'' +
                ", recurrencePattern='" + recurrencePattern + '\'' +
                ", securityUser=" + securityUser +
                ", speed=" + speed +
                ", startTimeHours=" + startTimeHours +
                ", startTimeMins=" + startTimeMins +
                ", timestamp=" + timestamp +
                ", unitIds=" + unitIds +
                ", weekDays='" + weekDays + '\'' +
                ", daysFilter=" + daysFilter +
                ", onlyNegativeBehaviour=" + onlyNegativeBehaviour +
                '}';
    }
}
