package com.sp.model;

import com.sp.SpContext;
import com.sp.dto.flex.SnailTrailDeviceDto;
import com.sp.util.Constants;
import com.sp.util.Util;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONE)
@Table(name = "journey")
public class Journey extends BaseModel {

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "unit_id")
    private LightVehicle vehicle;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date", nullable = true)
    private Date endDate;

    @Column(name = "max_speed")
    private float maxSpeed;

    @Column(name = "start_street_name")
    private String startStreetName;

    @Column(name = "end_street_name")
    private String endStreetName;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "driver_id")
    private BaseSecurityUser driver;

    private int idling;

    @Transient
    private long stoppedTime;

    @Transient
    private boolean stoppedTimeInsteadIdling;

    private int distance;

    @Column(name = "start_log_id")
    private int startLogId;

    @Column(name = "in_stealth_mode")
    private boolean inStealthMode;

    @Column(name = "end_log_id")
    private Integer endLogId;

    @Column(name = "start_poi_descr")
    private String startPoiDescr;

    @Column(name = "start_aoi_descr")
    private String startAoiDescr;

    @Column(name = "end_poi_descr")
    private String endPoiDescr;

    @Column(name = "end_aoi_descr")
    private String endAoiDescr;

    @Column(name = "start_postcode")
    private String startPostcode;

    @Column(name = "end_postcode")
    private String endPostcode;

    @Column(name = "max_acceleration")
    private Integer maxAcceleration;

    @Column(name = "max_deceleration")
    private Integer maxDeceleration;

    @Column(name = "device_max_journey_speed")
    private float deviceMaxJourneySpeed;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "box_type_id", insertable = false, updatable = false)
    private BoxType boxType;

    @OneToMany(mappedBy = "journey", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CanJourneyValue> canJourneyValues;

    @Column(name = "end_lat", insertable = false, updatable = false)
    private Double endLat;

    @Column(name = "end_lon", insertable = false, updatable = false)
    private Double endLon;


    private Float mpg;


    @Column(name = "harsh_acceleration")
    private float harshAcceleration;

    @Column(name = "harsh_braking")
    private float harshBraking;

    @Column(name = "harsh_cornering")
    private int harshCornering;

    @Column(name = "excessive_speed")
    private int excessiveSpeed;

    @Transient
    private float factoredMaxSpeed;
    @Transient
    private float factoredDistance;
    @Transient
    private float factoredDeviceMaxSpeed;
    @Transient
    private String startAoiPoiDescr;
    @Transient
    private String endAoiPoiDescr;



    @Transient
    SnailTrailDeviceDto snailTrailDeviceDto;


    public void copyTo(Journey copy) {
        super.copyTo(copy);
        copy.vehicle = vehicle;
        copy.startDate = startDate;
        copy.endDate = endDate;
        copy.maxSpeed = maxSpeed;
        copy.startStreetName = startStreetName;
        copy.endStreetName = endStreetName;
        copy.driver = driver;
        copy.idling = idling;
        copy.distance = distance;
        copy.startLogId = startLogId;
        copy.endLogId = endLogId;
        copy.startPoiDescr = startPoiDescr;
        copy.startAoiDescr = startAoiDescr;
        copy.endPoiDescr = endPoiDescr;
        copy.endAoiDescr = endAoiDescr;
        copy.factoredMaxSpeed = factoredMaxSpeed;
        copy.factoredDistance = factoredDistance;
        copy.startPostcode = startPostcode;
        copy.endPostcode = endPostcode;
        copy.maxAcceleration = maxAcceleration;
        copy.maxDeceleration = maxDeceleration;
        copy.startDate = startDate;
        copy.endDate = endDate;
        copy.deviceMaxJourneySpeed = deviceMaxJourneySpeed;
        copy.factoredDeviceMaxSpeed = factoredDeviceMaxSpeed;
        copy.boxType = boxType;
        copy.inStealthMode = inStealthMode;
        if (canJourneyValues != null && canJourneyValues.size() > 0) {
            copy.canJourneyValues = canJourneyValues;
        }
        copy.mpg = mpg;
        copy.endLat = endLat;
        copy.endLon = endLon;
        copy.harshAcceleration = harshAcceleration;
        copy.harshBraking = harshBraking;
        copy.harshCornering = harshCornering;
        copy.excessiveSpeed = excessiveSpeed;
    }

    public double getCostPerMileToDistance() {
        double distanceMiles = Util.getDistaceInMileByKM(distance * Constants.KM_IN_METER);
        return distanceMiles * (long) vehicle.getCostPerMile() / (long) Constants.PENCES_IN_POUND;
    }

    public void calculateFields() {
        startAoiPoiDescr = Util.buildAoiPoiDescr(startPoiDescr, startAoiDescr);
        endAoiPoiDescr = Util.buildAoiPoiDescr(endPoiDescr, endAoiDescr);
        UserPrefs userPrefs = SpContext.getUserDetailsInfo().getUserPrefs();
        factoredMaxSpeed = Math.round(maxSpeed * userPrefs.getDistanceMetricFactor() * 100) / 100f;
        factoredDeviceMaxSpeed = Math.round(deviceMaxJourneySpeed * userPrefs.getDistanceMetricFactor() * 100) / 100f;
        factoredDistance = Math.round(distance * userPrefs.getDistanceMetricFactor() / 10) / 100f;


    }

    // for flex
    public void setCostPerMileToDistance(double cost) {
    }

    public void setCostPerHourToDistance(double cost) {
    }

    public double getCostPerHourToDistance() {
        long diffInSeconds = (endDate.getTime() - startDate.getTime()) / 1000;
        double hours = diffInSeconds / (60.0 * 60);
        return hours * (long) vehicle.getCostPerHour() / (long) Constants.PENCES_IN_POUND;
    }

    public double getCost() {
        return getCostPerMileToDistance() + getCostPerHourToDistance();
    }

    public String getStartTime() throws ParseException {
        return new SimpleDateFormat("HH:mm").format(startDate);
    }

    public String getEndTime() throws ParseException {
        return new SimpleDateFormat("HH:mm").format(endDate);
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

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getFactoredMaxSpeed() {
        return factoredMaxSpeed;
    }

    public void setFactoredMaxSpeed(float factoredMaxSpeed) {
        this.factoredMaxSpeed = factoredMaxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getStartStreetName() {
        return startStreetName;
    }

    public void setStartStreetName(String startStreetName) {
        this.startStreetName = startStreetName;
    }

    public String getEndStreetName() {
        return endStreetName;
    }

    public void setEndStreetName(String endStreetName) {
        this.endStreetName = endStreetName;
    }

    public LightVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(LightVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getDuration() {
        long diffInSeconds = (endDate.getTime() - startDate.getTime()) / 1000;
        return Util.getTimeDelayStr(diffInSeconds);
    }

    // for flex
    public void setDuration(String duration) {
    }

    public String getDurationAlternate() {
        long diffInSeconds = (endDate.getTime() - startDate.getTime()) / 1000;
        return Util.getTimeDelayStrAlternate(diffInSeconds, false);
    }

     public String getDurationAlternateGrid() {
        long diffInSeconds = (endDate.getTime() - startDate.getTime()) / 1000;
        return Util.getTimeDelayStrAlternateGrid(diffInSeconds);
    }

    public BaseSecurityUser getDriver() {
        return driver;
    }

    public void setDriver(BaseSecurityUser driver) {
        this.driver = driver;
    }

    public int getIdling() {
        return idling;
    }

    public String getIdlingStr() {
        return Util.getTimeDelayStr(idling);
    }

    public void setIdlingStr(String idlingStr) {
    }

    public String getIdlingStrAlternate() {
        return Util.getTimeDelayStrAlternate(stoppedTimeInsteadIdling ? stoppedTime : idling, false);
    }

    public String getIdlingStrAlternateGrid() {
        return Util.getTimeDelayStrAlternateGrid(idling);
    }

    public void setIdlingStrAlternate(String idlingStr) {

    }

    public void setIdling(int idling) {
        this.idling = idling;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getStartLogId() {
        return startLogId;
    }

    public void setStartLogId(int startLogId) {
        this.startLogId = startLogId;
    }

    public Integer getEndLogId() {
        return endLogId;
    }

    public void setEndLogId(Integer endLogId) {
        this.endLogId = endLogId;
    }

    public float getFactoredDistance() {
        return factoredDistance;
    }

    public void setFactoredDistance(float factoredDistance) {
        this.factoredDistance = factoredDistance;
    }

    public String getEndPoiDescr() {
        return endPoiDescr;
    }

    public void setEndPoiDescr(String endPoiDescr) {
        this.endPoiDescr = endPoiDescr;
    }

    public String getStartPoiDescr() {
        return startPoiDescr;
    }

    public void setStartPoiDescr(String startPoiDescr) {
        this.startPoiDescr = startPoiDescr;
    }

    public String getStartPostcode() {
        return startPostcode;
    }

    public void setStartPostcode(String startPostcode) {
        this.startPostcode = startPostcode;
    }

    public String getEndPostcode() {
        return endPostcode;
    }

    public void setEndPostcode(String endPostcode) {
        this.endPostcode = endPostcode;
    }

    public Integer getMaxAcceleration() {
        return maxAcceleration;
    }

    public void setMaxAcceleration(Integer maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }

    public Integer getMaxDeceleration() {
        return maxDeceleration;
    }

    public void setMaxDeceleration(Integer maxDeceleration) {
        this.maxDeceleration = maxDeceleration;
    }

    public float getDeviceMaxJourneySpeed() {
        return deviceMaxJourneySpeed;
    }

    public void setDeviceMaxJourneySpeed(float deviceMaxJourneySpeed) {
        this.deviceMaxJourneySpeed = deviceMaxJourneySpeed;
    }

    public float getFactoredDeviceMaxSpeed() {
        return factoredDeviceMaxSpeed;
    }

    public void setFactoredDeviceMaxSpeed(float factoredDeviceMaxSpeed) {
        this.factoredDeviceMaxSpeed = factoredDeviceMaxSpeed;
    }

    public BoxType getBoxType() {
        return boxType;
    }

    public void setBoxType(BoxType boxType) {
        this.boxType = boxType;
    }

    public List<CanJourneyValue> getCanJourneyValues() {
        return canJourneyValues;
    }

    public void setCanJourneyValues(List<CanJourneyValue> canJourneyValues) {
        this.canJourneyValues = canJourneyValues;
    }

    public String getStartAoiDescr() {
        return startAoiDescr;
    }

    public void setStartAoiDescr(String startAoiDescr) {
        this.startAoiDescr = startAoiDescr;
    }

    public String getEndAoiDescr() {
        return endAoiDescr;
    }

    public void setEndAoiDescr(String endAoiDescr) {
        this.endAoiDescr = endAoiDescr;
    }


    public boolean isInStealthMode() {
        return inStealthMode;
    }

    public void setInStealthMode(boolean inStealthMode) {
        this.inStealthMode = inStealthMode;
    }

    public Float getMpg() {
        return mpg;
    }

    public void setMpg(Float mpg) {
        this.mpg = mpg;
    }

    public String getStartAoiPoiDescr() {
        return startAoiPoiDescr;
    }

    public void setStartAoiPoiDescr(String startAoiPoiDescr) {
        this.startAoiPoiDescr = startAoiPoiDescr;
    }

    public String getEndAoiPoiDescr() {
        return endAoiPoiDescr;
    }

    public void setEndAoiPoiDescr(String endAoiPoiDescr) {
        this.endAoiPoiDescr = endAoiPoiDescr;
    }

    public String getDriverDescr() {
        if (driver != null) {
            if (driver.getDallasKey() != null) {
                return "Driver ID: " + driver.getDescr();
            } else {
                return driver.getDescr();
            }
        } else {
            return "";
        }
    }

    //For flex serialization
    public void setDriverDescr(String driverDescr) {
    }

    public Double getEndLat() {
        return endLat;
    }

    public void setEndLat(Double endLat) {
        this.endLat = endLat;
    }

    public Double getEndLon() {
        return endLon;
    }

    public void setEndLon(Double endLon) {
        this.endLon = endLon;
    }

    public float getHarshAcceleration() {
        return harshAcceleration;
    }

    public void setHarshAcceleration(float harshAcceleration) {
        this.harshAcceleration = harshAcceleration;
    }

    public float getHarshBraking() {
        return harshBraking;
    }

    public void setHarshBraking(float harshBraking) {
        this.harshBraking = harshBraking;
    }

    public int getHarshCornering() {
        return harshCornering;
    }

    public void setHarshCornering(int harshCornering) {
        this.harshCornering = harshCornering;
    }

    public int getExcessiveSpeed() {
        return excessiveSpeed;
    }

    public void setExcessiveSpeed(int excessiveSpeed) {
        this.excessiveSpeed = excessiveSpeed;
    }

    public SnailTrailDeviceDto getSnailTrailDeviceDto() {
        return snailTrailDeviceDto;
    }

    public void setSnailTrailDeviceDto(SnailTrailDeviceDto snailTrailDeviceDto) {
        this.snailTrailDeviceDto = snailTrailDeviceDto;
    }

    public String getFactoredHarshAcceleration() {
        if (!snailTrailDeviceDto.isMaxAccelerationForceAllowed()) {
            return "n/a";
        } else if (harshAcceleration == 0) {
            return "-";
        } else {
            return harshAcceleration + "";
        }
    }

    public String getFactoredHarshBraking() {
        if (!snailTrailDeviceDto.isMaxBrakingForceAllowed()) {
            return "n/a";
        } else if (harshBraking == 0) {
            return "-";
        } else {
            return harshBraking + "";
        }
    }

    public String getFactoredHarshCornering() {
        if (!snailTrailDeviceDto.isMaxCorneringAllowed()) {
            return "n/a";
        } else if (harshCornering == 0) {
            return "-";
        } else {
            return harshCornering + "";
        }
    }

    public String getFactoredExcessiveSpeed() {
        if (!snailTrailDeviceDto.isMaxAllowedSpeedAllowed()) {
            return "n/a";
        } else if (excessiveSpeed == 0) {
            return "-";
        } else {
            return Math.round(excessiveSpeed * Util.getDistanceMetricFactor())  + "";
        }
    }

    public void setStoppedTime(long stoppedTime) {
        this.stoppedTime = stoppedTime;
    }

    // For Flex serialization
    public void setFactoredExcessiveSpeed(String excessiveSpeed) {
        
    }

    public void setStoppedTimeInsteadIdling(boolean stoppedTimeInsteadIdling) {
        this.stoppedTimeInsteadIdling = stoppedTimeInsteadIdling;
    }

}
