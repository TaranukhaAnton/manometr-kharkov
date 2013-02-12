package com.sp.model;

import com.sp.SpContext;
import com.sp.util.Util;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "incoming_log")
@Cache(usage = CacheConcurrencyStrategy.NONE)

public class IncomingLogRecord extends VehicleIncomingLogRecord {
    private static double[] accelDecoding = new double[]{
            0,
            0.44,
            0.88,
            1.33,
            1.77,
            2.21,
            2.66,
            3.10,
            3.54,
            4.00,
            4.44,
            4.88,
            5.33,
            5.77,
            6.21,
            6.66
    };

    @Column(name = "journey_id", insertable = false, updatable = false)
    private Integer journeyId;

    private boolean unitIsInMotion;
    
    private boolean dout1Active;
    private boolean dout2Active;
    private boolean dout3Active;
    private boolean dout4Active;

    private Double voltage;


    @Column(name = "distance_from_last_msg")
    private Integer distanceFromLastMsg;

    @Column(name = "device_max_journey_speed")
    private int maxJourneySpeed;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "box_type_id", updatable = false, insertable = false)
    private BoxType boxType;

    private Integer acceleration;

    private Integer deceleration;

    private Integer idling;

    @Column(name = "journey_distance")
    private Integer journeyDistance;

    @Column(name = "vehicle_odometer")
    private Integer vehicleOdometer;

    @Column(name = "received_date")
    private Date receivedDate;

    @Column(name = "dallas_key_code")
    private String dallasKeyCode;

    @Column(name = "harsh_acceleration")
    private float harshAcceleration;

    @Column(name = "harsh_braking")
    private float harshBraking;

    @Column(name = "harsh_cornering")
    private int harshCornering;

    @Column(name = "overspeed_start")
    private int overspeedStart;

    @Column(name = "overspeed_end")
    private int overspeedEnd;

    @Column(name = "overspeed")
    private int overspeed;

    
    // For processing aoi only
    @Transient
    private TrackingDevice trackingDevice;

    // For processing aoi only
    @Transient
    private Vehicle vehicle;

    @Transient
    private boolean stealthMode;

    @Transient
    private String dallasDriverName;

    public void copyTo(IncomingLogRecord copy) {
        super.copyTo(copy);
        copy.journeyId = journeyId;
        copy.unitIsInMotion = unitIsInMotion;
        copy.dout1Active = dout1Active;
        copy.dout2Active = dout2Active;
        copy.voltage = voltage;
        copy.distanceFromLastMsg = distanceFromLastMsg;
        copy.acceleration = acceleration;
        copy.deceleration = deceleration;
        copy.idling = idling;
        copy.boxType = boxType;
        copy.maxJourneySpeed = maxJourneySpeed;
        copy.stealthMode = stealthMode;
        copy.journeyDistance = journeyDistance;
        copy.vehicleOdometer = vehicleOdometer;
        copy.receivedDate = receivedDate;
        copy.dout3Active = dout3Active;
        copy.dout4Active = dout4Active;
        copy.dallasKeyCode = dallasKeyCode;
		copy.dallasDriverName = dallasDriverName;
        copy.harshAcceleration = harshAcceleration;
        copy.harshBraking = harshBraking;
        copy.harshCornering = harshCornering;
        copy.overspeedStart = overspeedStart;
        copy.overspeedEnd = overspeedEnd;
        copy.overspeed = overspeed;
    }

    @Override
    public String toString() {
        return "IncomingLogRecord{" +
                "id=" + getId() +
                ", createdDate=" + getCreatedDate() +
                ", recDate=" + getRecDate() +
                ", lat=" + getLat() +
                ", lon=" + getLon() +
                ", speed=" + getSpeed() +
                ", satellites=" + getSatellites() +
                ", nodeId='" + getNodeId() + '\'' +
                ", direction=" + getDirection() +
                ", journeyId=" + journeyId +
                ", journeyStart=" + isJourneyStart() +
                ", journeyEnd=" + isJourneyEnd() +
                ", streetName='" + getStreetName() + '\'' +
                ", unitIsInMotion=" + unitIsInMotion +
                ", input1High=" + isInput1High() +
                ", input2High=" + isInput2High() +
                ", input3High=" + isInput3High() +
                ", input4High=" + isInput4High() +
                ", input5High=" + isInput5High() +
                ", input6High=" + isInput6High() +
                ", dout1Active=" + dout1Active +
                ", dout2Active=" + dout2Active +
                ", dout3Active=" + dout1Active +
                ", dout4Active=" + dout2Active +
                ", ignitionActive=" + isIgnitionActive() +
                ", voltage='" + voltage + '\'' +
                ", distanceFromLastMsg=" + distanceFromLastMsg +
                ", poiDescr='" + getPoiDescr() + '\'' +
                ", vehicleId=" + getVehicleId() +
                ", maxJourneySpeed=" + maxJourneySpeed +
                ", trackingDeviceId=" + getTrackingDeviceId() +
                ", boxTypeId=" + (boxType!=null ? boxType.getId() : "") +
                ", vehicleId=" + getVehicleId() +
                ", postcode='" + getPostcode() + '\'' +
                ", acceleration=" + acceleration +
                ", deceleration=" + deceleration +
                ", idling=" + idling +
                ", trakm8MessageType=" + super.getTrakm8MessageType() +
                ", dallasKeyCode=" + dallasKeyCode +
                ", harshAcceleration=" + harshAcceleration +
                ", harshBraking=" + harshBraking +
                ", harshCornering=" + harshCornering +
                ", overspeedStart=" + overspeedStart +
                ", overspeedEnd=" + overspeedEnd +
                ", overspeed=" + overspeed +
                '}';
    }

    @Override
    public void calculateFields(BaseTrackingDevice trackingDevice, BaseVehicle vehicle) {
       super.calculateFields(trackingDevice, vehicle);
       stealthMode = trackingDevice.isStealthInputPresent();

    }

    public String getAoiPoiDescr() {
        return Util.buildAoiPoiDescr(getPoiDescr(), getAoiDescr());
    }

    //for flex serialization
    public void setAoiPoiDescr(String aoiDescr) {    }

    public float getDeviceMaxFactoredSpeed() {
        return Math.round(maxJourneySpeed * SpContext.getUserDetailsInfo().getUserPrefs().getDistanceMetricFactor() * 100) / 100;
    }

      //for flex serialization
    public void setDeviceMaxFactoredSpeed(float factoredSpeed) {

    }

    public float getFactoredOverspeedStart(){
        return Math.round(overspeedStart * SpContext.getUserDetailsInfo().getUserPrefs().getDistanceMetricFactor() * 100) / 100;
    }

    public void setFactoredOverspeedStart(float v){}

    public float getFactoredOverspeedEnd(){
        return Math.round(overspeedEnd * SpContext.getUserDetailsInfo().getUserPrefs().getDistanceMetricFactor() * 100) / 100;
    }

    public void setFactoredOverspeedEnd(float v){}


    public Integer getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(Integer journeyId) {
        this.journeyId = journeyId;
    }

    public boolean getUnitIsInMotion() {
        return unitIsInMotion;
    }

    public void setUnitIsInMotion(boolean unitIsInMotion) {
        this.unitIsInMotion = unitIsInMotion;
    }

    public boolean isDout1Active() {
        return dout1Active;
    }

    public void setDout1Active(boolean dout1Active) {
        this.dout1Active = dout1Active;
    }

    public boolean isDout2Active() {
        return dout2Active;
    }

    public void setDout2Active(boolean dout2Active) {
        this.dout2Active = dout2Active;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public Integer getDistanceFromLastMsg() {
        return distanceFromLastMsg;
    }

    public void setDistanceFromLastMsg(Integer distanceFromLastMsg) {
        this.distanceFromLastMsg = distanceFromLastMsg;
    }

    public void setAcceleration(Integer acceleration) {
        this.acceleration = acceleration;
    }

    public Integer getAcceleration() {
        return acceleration;
    }

    public Integer getDeceleration() {
        return deceleration;
    }

    public void setDeceleration(Integer deceleration) {
        this.deceleration = deceleration;
    }

    public Double getAccelerationMs2() {
        return acceleration == null ? null : accelDecoding[acceleration];
    }

    //flex serialization requires this setter
    public void setAccelerationMs2(Double value) {
    }

    public Double getDecelerationMs2() {
        return deceleration == null ? null : accelDecoding[deceleration];
    }

    //flex serialization requires this setter
    public void setDecelerationMs2(Double value) {
    }

    public Integer getIdling() {
        return idling;
    }

    public void setIdling(Integer idling) {
        this.idling = idling;
    }

    public BoxType getBoxType() {
        return boxType;
    }

    public void setBoxType(BoxType boxType) {
        this.boxType = boxType;
    }

    public int getMaxJourneySpeed() {
        return maxJourneySpeed;
    }

    public void setMaxJourneySpeed(int maxJourneySpeed) {
        this.maxJourneySpeed = maxJourneySpeed;
    }

    public TrackingDevice getTrackingDevice() {
        return trackingDevice;
    }

    public void setTrackingDevice(TrackingDevice trackingDevice) {
        this.trackingDevice = trackingDevice;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isStealthMode() {
        return stealthMode;
    }

    public void setStealthMode(boolean stealthMode) {
        this.stealthMode = stealthMode;
    }

    public Integer getJourneyDistance() {
        return journeyDistance;
    }

    public void setJourneyDistance(Integer journeyDistance) {
        this.journeyDistance = journeyDistance;
    }

    public Integer getVehicleOdometer() {
        return vehicleOdometer;
    }

    public void setVehicleOdometer(Integer vehicleOdometer) {
        this.vehicleOdometer = vehicleOdometer;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public boolean isDout3Active() {
        return dout3Active;
    }

    public void setDout3Active(boolean dout3Active) {
        this.dout3Active = dout3Active;
    }

    public boolean isDout4Active() {
        return dout4Active;
    }

    public void setDout4Active(boolean dout4Active) {
        this.dout4Active = dout4Active;
    }

    public String getDallasKeyCode() {
        return dallasKeyCode;
    }

    public void setDallasKeyCode(String dallasKeyCode) {
        this.dallasKeyCode = dallasKeyCode;
    }

	public String getDallasDriverName() {
        return dallasDriverName;
    }

    public void setDallasDriverName(String dallasDriverName) {
        this.dallasDriverName = dallasDriverName;
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

    public int getOverspeedStart() {
        return overspeedStart;
    }

    public void setOverspeedStart(int overspeedStart) {
        this.overspeedStart = overspeedStart;
    }

    public int getOverspeedEnd() {
        return overspeedEnd;
    }

    public void setOverspeedEnd(int overspeedEnd) {
        this.overspeedEnd = overspeedEnd;
    }

    public int getOverspeed() {
        return overspeed;
    }

    public void setOverspeed(int overspeed) {
        this.overspeed = overspeed;
    }

    public void setFactoredOverspeed(int overspeed) {

    }

    public int getFactoredOverspeed() {
        return Math.round(overspeed * Util.getDistanceMetricFactor());
    }
}

