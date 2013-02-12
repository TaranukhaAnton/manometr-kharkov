package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "imei")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class BaseTrackingDevice extends BaseModel {
    private String imei;

    @Column(name = "is_immobilize")
    private boolean immobilize;

    @Column(name = "serial_number")
	private String serialNumber;

    @Column(name = "is_deleted")
    private boolean deleted;

    @Column(name = "is_speed_limit_allowed")
    private boolean speedLimitAllowed;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private BaseVehicle vehicle;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "box_type_id")
    private BoxType boxType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE },
            fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_sim_id")
    private BaseSim assignedSim;

    @Column(name = "timestamp", insertable = false, updatable = false)
    private Date timestamp;

    @Column(name = "digital_input1_value_high")
    private String digitalInput1ValueHigh;

    @Column(name = "digital_input1_value_low")
    private String digitalInput1ValueLow;

    @Column(name = "digital_input2_value_high")
    private String digitalInput2ValueHigh;

    @Column(name = "digital_input2_value_low")
    private String digitalInput2ValueLow;

    @Column(name = "digital_input3_value_high")
    private String digitalInput3ValueHigh;

    @Column(name = "digital_input3_value_low")
    private String digitalInput3ValueLow;

    @Column(name = "digital_input4_value_high")
    private String digitalInput4ValueHigh;

    @Column(name = "digital_input4_value_low")
    private String digitalInput4ValueLow;

    @Column(name = "digital_input5_value_high")
    private String digitalInput5ValueHigh;

    @Column(name = "digital_input5_value_low")
    private String digitalInput5ValueLow;

    @Column(name = "digital_input6_value_high")
    private String digitalInput6ValueHigh;

    @Column(name = "digital_input6_value_low")
    private String digitalInput6ValueLow;

    @Column(name = "ignition_input_number")
    private int ignitionInputNumber;

    @Column(name = "stealth_mode_input_number")
    private int stealthModeInputNumber;

    @Column(name = "is_calculated_orientation")
    private boolean calculatedOrientation;

    @Column(name = "is_retain_journey_end")
    private boolean retainJourneyEnd;

    @Column(name = "digital_input1_high_color")
    private int digitalInput1HighColor;
    @Column(name = "digital_input1_low_color")
    private int digitalInput1LowColor;
    @Column(name = "digital_input2_high_color")
    private int digitalInput2HighColor;
    @Column(name = "digital_input2_low_color")
    private int digitalInput2LowColor;
    @Column(name = "digital_input3_high_color")
    private int digitalInput3HighColor;
    @Column(name = "digital_input3_low_color")
    private int digitalInput3LowColor;
    @Column(name = "digital_input4_high_color")
    private int digitalInput4HighColor;
    @Column(name = "digital_input4_low_color")
    private int digitalInput4LowColor;

    @Column(name = "digital_input1_name")
    private String digitalInput1Name;

    @Column(name = "digital_input2_name")
    private String digitalInput2Name;

    @Column(name = "digital_input3_name")
    private String digitalInput3Name;

    @Column(name = "digital_input4_name")
    private String digitalInput4Name;

    @Column(name = "digital_input5_name")
    private String digitalInput5Name;

    @Column(name = "digital_input6_name")
    private String digitalInput6Name;

    @Column(name = "left_panel_display_din_number")
    private Integer leftPanelDisplayDinNumber;

    @Column(name = "is_replication_enabled")
    private boolean replicationEnabled;

    @Column(name = "is_daylight_saving")
    private boolean daylightSaving;

    @Column(name = "last_collector")
    private String lastCollector;

    @Column(name = "is_always_display_driver_name")
    private boolean alwaysDisplayDriverName;

    @Column(name = "is_max_acceleration_force_allowed")
    private boolean maxAccelerationForceAllowed;

    /**
     * Not using in system - just store the user's value.
     */
    @Column(name = "max_acceleration_force_actual_val")
    private float maxAccelerationForceActualVal;
    @Column(name = "max_acceleration_force_orange_val")
    private float maxAccelerationForceOrangeVal;
    @Column(name = "max_acceleration_force_red_val")
    private float maxAccelerationForceRedVal;

    @Column(name = "is_max_braking_force_allowed")
    private boolean maxBrakingForceAllowed;

    /**
     * Not using in system - just store the user's value.
     */
    @Column(name = "max_braking_force_actual_val")
    private float maxBrakingForceActualVal;

    @Column(name = "max_braking_force_orange_val")
    private float maxBrakingForceOrangeVal;
    @Column(name = "max_braking_force_red_val")
    private float maxBrakingForceRedVal;

    @Column(name = "is_max_cornering_allowed")
    private boolean maxCorneringAllowed;

    /**
     * Not using in system - just store the user's value.
     */
    @Column(name = "max_cornering_actual_val")
    private int maxCorneringActualVal;
    @Column(name = "max_cornering_orange_val")
    private int maxCorneringOrangeVal;
    @Column(name = "max_cornering_red_val")
    private int maxCorneringRedVal;

    @Column(name = "is_max_allowed_speed_allowed")
    private boolean maxAllowedSpeedAllowed;

    /**
     * Not using in system - just store the user's value.
     */
    @Column(name = "max_allowed_speed_actual_val")
    private int maxAllowedSpeedActualVal;
    @Column(name = "max_allowed_speed_orange_val")
    private int maxAllowedSpeedOrangeVal;
    @Column(name = "max_allowed_speed_red_val")
    private int maxAllowedSpeedRedVal;

    @Column(name = "is_green_driving_allowed")
    private boolean greenDrivingAllowed;

    @Column(name = "is_overspeed_allowed")
    private boolean overspeedAllowed;

    @Column(name = "is_device_unplugged_enabled")
    private boolean deviceUnpluggedEnabled;

    @Column(name = "is_driver_id_fitted")
    private boolean driverIdFitted;

    @Column(name = "is_retain_driver_name_after_ignition_off")
    private boolean retainDriverNameAfterIgnitionOff;

    public BoxType getBoxType() {
        return boxType;
    }

    public void copyTo(BaseTrackingDevice copy) {
        super.copyTo(copy);
        copy.imei = imei;
        if (vehicle != null) {
            BaseVehicle vehicleCopy = new BaseVehicle();
            vehicle.copyTo(vehicleCopy);
            copy.vehicle = vehicleCopy;

        } else {
            copy.vehicle = null;
        }

        if (assignedSim != null) {
            Sim simCopy = new Sim();
            assignedSim.copyTo(simCopy);
            copy.assignedSim = simCopy;
        } else {
            copy.assignedSim = null;
        }

        copy.immobilize = immobilize;
        copy.boxType = boxType;
        copy.account = account;
        copy.timestamp = timestamp;
        copy.digitalInput1ValueHigh = digitalInput1ValueHigh;
        copy.digitalInput1ValueLow = digitalInput1ValueLow;
        copy.digitalInput2ValueHigh = digitalInput2ValueHigh;
        copy.digitalInput2ValueLow = digitalInput2ValueLow;
        copy.digitalInput3ValueHigh = digitalInput3ValueHigh;
        copy.digitalInput3ValueLow = digitalInput3ValueLow;
        copy.digitalInput4ValueHigh = digitalInput4ValueHigh;
        copy.digitalInput4ValueLow = digitalInput4ValueLow;
        copy.digitalInput5ValueHigh = digitalInput5ValueHigh;
        copy.digitalInput5ValueLow = digitalInput5ValueLow;
        copy.digitalInput6ValueHigh = digitalInput6ValueHigh;
        copy.digitalInput6ValueLow = digitalInput6ValueLow;
        copy.ignitionInputNumber = ignitionInputNumber;
        copy.calculatedOrientation = calculatedOrientation;
        copy.stealthModeInputNumber = stealthModeInputNumber;
        copy.retainJourneyEnd = retainJourneyEnd;
        copy.digitalInput1HighColor = digitalInput1HighColor;
        copy.digitalInput1LowColor = digitalInput1LowColor;
        copy.digitalInput2HighColor = digitalInput2HighColor;
        copy.digitalInput2LowColor = digitalInput2LowColor;
        copy.digitalInput3HighColor = digitalInput3HighColor;
        copy.digitalInput3LowColor = digitalInput3LowColor;
        copy.digitalInput4HighColor = digitalInput4HighColor;
        copy.digitalInput4LowColor = digitalInput4LowColor;
        copy.digitalInput1Name = digitalInput1Name;
        copy.digitalInput2Name = digitalInput2Name;
        copy.digitalInput3Name = digitalInput3Name;
        copy.digitalInput4Name = digitalInput4Name;
        copy.digitalInput5Name = digitalInput5Name;
        copy.digitalInput6Name = digitalInput6Name;
        copy.leftPanelDisplayDinNumber = leftPanelDisplayDinNumber;
        copy.replicationEnabled = replicationEnabled;
        copy.daylightSaving = daylightSaving;
        copy.serialNumber = serialNumber;
        copy.deleted = deleted;
        copy.lastCollector = lastCollector;
        copy.alwaysDisplayDriverName = alwaysDisplayDriverName;
        copy.maxAccelerationForceAllowed = maxAccelerationForceAllowed;
        copy.maxAccelerationForceActualVal = maxAccelerationForceActualVal;
        copy.maxAccelerationForceOrangeVal = maxAccelerationForceOrangeVal;
        copy.maxAccelerationForceRedVal = maxAccelerationForceRedVal;
        copy.maxBrakingForceAllowed = maxBrakingForceAllowed;
        copy.maxBrakingForceActualVal = maxBrakingForceActualVal;
        copy.maxBrakingForceOrangeVal = maxBrakingForceOrangeVal;
        copy.maxBrakingForceRedVal = maxBrakingForceRedVal;
        copy.maxCorneringAllowed = maxCorneringAllowed;
        copy.maxCorneringActualVal = maxCorneringActualVal;
        copy.maxCorneringOrangeVal = maxCorneringOrangeVal;
        copy.maxCorneringRedVal = maxCorneringRedVal;
        copy.maxAllowedSpeedAllowed = maxAllowedSpeedAllowed;
        copy.maxAllowedSpeedActualVal = maxAllowedSpeedActualVal;
        copy.maxAllowedSpeedOrangeVal = maxAllowedSpeedOrangeVal;
        copy.maxAllowedSpeedRedVal = maxAllowedSpeedRedVal;
        copy.speedLimitAllowed = speedLimitAllowed;
        copy.greenDrivingAllowed = greenDrivingAllowed;
        copy.overspeedAllowed = overspeedAllowed;
        copy.deviceUnpluggedEnabled = deviceUnpluggedEnabled;
        copy.driverIdFitted = driverIdFitted;
        copy.retainDriverNameAfterIgnitionOff = retainDriverNameAfterIgnitionOff;
    }

    public String toOptionText() {
        return imei;
    }

    public boolean getImmobilize() {
        return immobilize;
    }

    public void setImmobilize(boolean immobilize) {
        this.immobilize = immobilize;
    }

    public BaseSim getAssignedSim() {
        return assignedSim;
    }

    public void setAssignedSim(BaseSim assignedSim) {
        this.assignedSim = assignedSim;
    }

    public void setBoxType(BoxType boxType) {
        this.boxType = boxType;
    }

    public BaseVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(BaseVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getDigitalInput1ValueHigh() {
        return digitalInput1ValueHigh;
    }

    public void setDigitalInput1ValueHigh(String digitalInput1ValueHigh) {
        this.digitalInput1ValueHigh = digitalInput1ValueHigh;
    }

    public String getDigitalInput1ValueLow() {
        return digitalInput1ValueLow;
    }

    public void setDigitalInput1ValueLow(String digitalInput1ValueLow) {
        this.digitalInput1ValueLow = digitalInput1ValueLow;
    }

    public String getDigitalInput2ValueHigh() {
        return digitalInput2ValueHigh;
    }

    public void setDigitalInput2ValueHigh(String digitalInput2ValueHigh) {
        this.digitalInput2ValueHigh = digitalInput2ValueHigh;
    }

    public String getDigitalInput2ValueLow() {
        return digitalInput2ValueLow;
    }

    public void setDigitalInput2ValueLow(String digitalInput2ValueLow) {
        this.digitalInput2ValueLow = digitalInput2ValueLow;
    }

    public String getDigitalInput3ValueHigh() {
        return digitalInput3ValueHigh;
    }

    public void setDigitalInput3ValueHigh(String digitalInput3ValueHigh) {
        this.digitalInput3ValueHigh = digitalInput3ValueHigh;
    }

    public String getDigitalInput3ValueLow() {
        return digitalInput3ValueLow;
    }

    public void setDigitalInput3ValueLow(String digitalInput3ValueLow) {
        this.digitalInput3ValueLow = digitalInput3ValueLow;
    }

    public String getDigitalInput4ValueHigh() {
        return digitalInput4ValueHigh;
    }

    public void setDigitalInput4ValueHigh(String digitalInput4ValueHigh) {
        this.digitalInput4ValueHigh = digitalInput4ValueHigh;
    }

    public String getDigitalInput4ValueLow() {
        return digitalInput4ValueLow;
    }

    public void setDigitalInput4ValueLow(String digitalInput4ValueLow) {
        this.digitalInput4ValueLow = digitalInput4ValueLow;
    }

    public String getDigitalInput5ValueHigh() {
        return digitalInput5ValueHigh;
    }

    public void setDigitalInput5ValueHigh(String digitalInput5ValueHigh) {
        this.digitalInput5ValueHigh = digitalInput5ValueHigh;
    }

    public String getDigitalInput5ValueLow() {
        return digitalInput5ValueLow;
    }

    public void setDigitalInput5ValueLow(String digitalInput5ValueLow) {
        this.digitalInput5ValueLow = digitalInput5ValueLow;
    }

    public String getDigitalInput6ValueHigh() {
        return digitalInput6ValueHigh;
    }

    public void setDigitalInput6ValueHigh(String digitalInput6ValueHigh) {
        this.digitalInput6ValueHigh = digitalInput6ValueHigh;
    }

    public String getDigitalInput6ValueLow() {
        return digitalInput6ValueLow;
    }

    public void setDigitalInput6ValueLow(String digitalInput6ValueLow) {
        this.digitalInput6ValueLow = digitalInput6ValueLow;
    }

    public int getIgnitionInputNumber() {
        return ignitionInputNumber;
    }

    public void setIgnitionInputNumber(int ignitionInputNumber) {
        this.ignitionInputNumber = ignitionInputNumber;
    }


    public boolean isCalculatedOrientation() {
        return calculatedOrientation;
    }

    public void setCalculatedOrientation(boolean calculatedOrientation) {
        this.calculatedOrientation = calculatedOrientation;
    }

    public int getStealthModeInputNumber() {
        return stealthModeInputNumber;
    }

    public void setStealthModeInputNumber(int stealthModeInputNumber) {
        this.stealthModeInputNumber = stealthModeInputNumber;
    }

    @Override
    public String toString() {
        return "BaseTrackingDevice{" +
                "vehicle=" + vehicle +
                ", boxType=" + boxType +
                ", digitalInput1ValueHigh=" + digitalInput1ValueHigh +
                ", digitalInput1ValueLow=" + digitalInput1ValueLow +
                ", digitalInput2ValueHigh=" + digitalInput2ValueHigh +
                ", digitalInput2ValueLow=" + digitalInput2ValueLow +
                ", digitalInput3ValueHigh=" + digitalInput3ValueHigh +
                ", digitalInput3ValueLow=" + digitalInput3ValueLow +
                ", digitalInput4ValueHigh=" + digitalInput4ValueHigh +
                ", digitalInput4ValueLow=" + digitalInput4ValueLow +
                ", digitalInput5ValueHigh=" + digitalInput5ValueHigh +
                ", digitalInput5ValueLow=" + digitalInput5ValueLow +
                ", digitalInput6ValueHigh=" + digitalInput6ValueHigh +
                ", digitalInput6ValueLow=" + digitalInput6ValueLow +
                ", ignitionInputNumber=" + ignitionInputNumber +
                ", calculatedOrientation=" + calculatedOrientation +
                ", stealthModeInputNumber=" + stealthModeInputNumber +
                '}';
    }

    public boolean isRetainJourneyEnd() {
        return retainJourneyEnd;
    }

    public void setRetainJourneyEnd(boolean retainJourneyEnd) {
        this.retainJourneyEnd = retainJourneyEnd;
    }

    public int getDigitalInput1HighColor() {
        return digitalInput1HighColor;
    }

    public void setDigitalInput1HighColor(int digitalInput1HighColor) {
        this.digitalInput1HighColor = digitalInput1HighColor;
    }

    public int getDigitalInput1LowColor() {
        return digitalInput1LowColor;
    }

    public void setDigitalInput1LowColor(int digitalInput1LowColor) {
        this.digitalInput1LowColor = digitalInput1LowColor;
    }

    public int getDigitalInput2HighColor() {
        return digitalInput2HighColor;
    }

    public void setDigitalInput2HighColor(int digitalInput2HighColor) {
        this.digitalInput2HighColor = digitalInput2HighColor;
    }

    public int getDigitalInput2LowColor() {
        return digitalInput2LowColor;
    }

    public void setDigitalInput2LowColor(int digitalInput2LowColor) {
        this.digitalInput2LowColor = digitalInput2LowColor;
    }

    public int getDigitalInput3HighColor() {
        return digitalInput3HighColor;
    }

    public void setDigitalInput3HighColor(int digitalInput3HighColor) {
        this.digitalInput3HighColor = digitalInput3HighColor;
    }

    public int getDigitalInput3LowColor() {
        return digitalInput3LowColor;
    }

    public void setDigitalInput3LowColor(int digitalInput3LowColor) {
        this.digitalInput3LowColor = digitalInput3LowColor;
    }

    public int getDigitalInput4HighColor() {
        return digitalInput4HighColor;
    }

    public void setDigitalInput4HighColor(int digitalInput4HighColor) {
        this.digitalInput4HighColor = digitalInput4HighColor;
    }

    public int getDigitalInput4LowColor() {
        return digitalInput4LowColor;
    }

    public void setDigitalInput4LowColor(int digitalInput4LowColor) {
        this.digitalInput4LowColor = digitalInput4LowColor;
    }

    public String getDigitalInput1Name() {
        return digitalInput1Name;
    }

    public void setDigitalInput1Name(String digitalInput1Name) {
        this.digitalInput1Name = digitalInput1Name;
    }

    public String getDigitalInput2Name() {
        return digitalInput2Name;
    }

    public void setDigitalInput2Name(String digitalInput2Name) {
        this.digitalInput2Name = digitalInput2Name;
    }

    public String getDigitalInput3Name() {
        return digitalInput3Name;
    }

    public void setDigitalInput3Name(String digitalInput3Name) {
        this.digitalInput3Name = digitalInput3Name;
    }

    public String getDigitalInput4Name() {
        return digitalInput4Name;
    }

    public void setDigitalInput4Name(String digitalInput4Name) {
        this.digitalInput4Name = digitalInput4Name;
    }

    public String getDigitalInput5Name() {
        return digitalInput5Name;
    }

    public void setDigitalInput5Name(String digitalInput5Name) {
        this.digitalInput5Name = digitalInput5Name;
    }

    public String getDigitalInput6Name() {
        return digitalInput6Name;
    }

    public void setDigitalInput6Name(String digitalInput6Name) {
        this.digitalInput6Name = digitalInput6Name;
    }

    public Integer getLeftPanelDisplayDinNumber() {
        return leftPanelDisplayDinNumber;
    }

    public void setLeftPanelDisplayDinNumber(Integer leftPanelDisplayDinNumber) {
        this.leftPanelDisplayDinNumber = leftPanelDisplayDinNumber;
    }

    public boolean isStealthInputPresent() {
        return stealthModeInputNumber != 0;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isReplicationEnabled() {
        return replicationEnabled;
    }

    public void setReplicationEnabled(boolean replicationEnabled) {
        this.replicationEnabled = replicationEnabled;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDaylightSaving() {
        return daylightSaving;
    }

    public void setDaylightSaving(boolean daylightSaving) {
        this.daylightSaving = daylightSaving;
    }

    public String getLastCollector() {
        return lastCollector;
    }

    public void setLastCollector(String lastCollector) {
        this.lastCollector = lastCollector;
    }

    public boolean isAlwaysDisplayDriverName() {
        return alwaysDisplayDriverName;
    }

    public void setAlwaysDisplayDriverName(boolean alwaysDisplayDriverName) {
        this.alwaysDisplayDriverName = alwaysDisplayDriverName;
    }

    public boolean isMaxAccelerationForceAllowed() {
        return maxAccelerationForceAllowed;
    }

    public void setMaxAccelerationForceAllowed(boolean maxAccelerationForceAllowed) {
        this.maxAccelerationForceAllowed = maxAccelerationForceAllowed;
    }

    public float getMaxAccelerationForceActualVal() {
        return maxAccelerationForceActualVal;
    }

    public void setMaxAccelerationForceActualVal(float maxAccelerationForceActualVal) {
        this.maxAccelerationForceActualVal = maxAccelerationForceActualVal;
    }

    public float getMaxAccelerationForceOrangeVal() {
        return maxAccelerationForceOrangeVal;
    }

    public void setMaxAccelerationForceOrangeVal(float maxAccelerationForceOrangeVal) {
        this.maxAccelerationForceOrangeVal = maxAccelerationForceOrangeVal;
    }

    public float getMaxAccelerationForceRedVal() {
        return maxAccelerationForceRedVal;
    }

    public void setMaxAccelerationForceRedVal(float maxAccelerationForceRedVal) {
        this.maxAccelerationForceRedVal = maxAccelerationForceRedVal;
    }

    public boolean isMaxBrakingForceAllowed() {
        return maxBrakingForceAllowed;
    }

    public void setMaxBrakingForceAllowed(boolean maxBrakingForceAllowed) {
        this.maxBrakingForceAllowed = maxBrakingForceAllowed;
    }

    public float getMaxBrakingForceActualVal() {
        return maxBrakingForceActualVal;
    }

    public void setMaxBrakingForceActualVal(float maxBrakingForceActualVal) {
        this.maxBrakingForceActualVal = maxBrakingForceActualVal;
    }

    public float getMaxBrakingForceOrangeVal() {
        return maxBrakingForceOrangeVal;
    }

    public void setMaxBrakingForceOrangeVal(float maxBrakingForceOrangeVal) {
        this.maxBrakingForceOrangeVal = maxBrakingForceOrangeVal;
    }

    public float getMaxBrakingForceRedVal() {
        return maxBrakingForceRedVal;
    }

    public void setMaxBrakingForceRedVal(float maxBrakingForceRedVal) {
        this.maxBrakingForceRedVal = maxBrakingForceRedVal;
    }

    public boolean isMaxCorneringAllowed() {
        return maxCorneringAllowed;
    }

    public void setMaxCorneringAllowed(boolean maxCorneringAllowed) {
        this.maxCorneringAllowed = maxCorneringAllowed;
    }

    public int getMaxCorneringActualVal() {
        return maxCorneringActualVal;
    }

    public void setMaxCorneringActualVal(int maxCorneringActualVal) {
        this.maxCorneringActualVal = maxCorneringActualVal;
    }

    public int getMaxCorneringOrangeVal() {
        return maxCorneringOrangeVal;
    }

    public void setMaxCorneringOrangeVal(int maxCorneringOrangeVal) {
        this.maxCorneringOrangeVal = maxCorneringOrangeVal;
    }

    public int getMaxCorneringRedVal() {
        return maxCorneringRedVal;
    }

    public void setMaxCorneringRedVal(int maxCorneringRedVal) {
        this.maxCorneringRedVal = maxCorneringRedVal;
    }

    public boolean isMaxAllowedSpeedAllowed() {
        return maxAllowedSpeedAllowed;
    }

    public void setMaxAllowedSpeedAllowed(boolean maxAllowedSpeedAllowed) {
        this.maxAllowedSpeedAllowed = maxAllowedSpeedAllowed;
    }

    public int getMaxAllowedSpeedActualVal() {
        return maxAllowedSpeedActualVal;
    }

    public int getMaxAllowedSpeedOrangeVal() {
        return maxAllowedSpeedOrangeVal;
    }

    public void setMaxAllowedSpeedOrangeVal(int maxAllowedSpeedOrangeVal) {
        this.maxAllowedSpeedOrangeVal = maxAllowedSpeedOrangeVal;
    }

    public int getMaxAllowedSpeedRedVal() {
        return maxAllowedSpeedRedVal;
    }

    public void setMaxAllowedSpeedRedVal(int maxAllowedSpeedRedVal) {
        this.maxAllowedSpeedRedVal = maxAllowedSpeedRedVal;
    }

    public void setMaxAllowedSpeedActualVal(int maxAllowedSpeedActualVal) {
        this.maxAllowedSpeedActualVal = maxAllowedSpeedActualVal;
    }

    public boolean isSpeedLimitAllowed() {
        return speedLimitAllowed;
    }

    public void setSpeedLimitAllowed(boolean speedLimitAllowed) {
        this.speedLimitAllowed = speedLimitAllowed;
    }

    public boolean isGreenDrivingAllowed() {
        return greenDrivingAllowed;
    }

    public void setGreenDrivingAllowed(boolean greenDrivingAllowed) {
        this.greenDrivingAllowed = greenDrivingAllowed;
    }

    public boolean isOverspeedAllowed() {
        return overspeedAllowed;
    }

    public void setOverspeedAllowed(boolean overspeedAllowed) {
        this.overspeedAllowed = overspeedAllowed;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean isDeviceUnpluggedEnabled() {
        return deviceUnpluggedEnabled;
    }

    public void setDeviceUnpluggedEnabled(boolean deviceUnpluggedEnabled) {
        this.deviceUnpluggedEnabled = deviceUnpluggedEnabled;
    }

    public boolean isDriverIdFitted() {
        return driverIdFitted;
    }

    public void setDriverIdFitted(boolean driverIdFitted) {
        this.driverIdFitted = driverIdFitted;
    }

    public boolean isRetainDriverNameAfterIgnitionOff() {
        return retainDriverNameAfterIgnitionOff;
    }

    public void setRetainDriverNameAfterIgnitionOff(boolean retainDriverNameAfterIgnitionOff) {
        this.retainDriverNameAfterIgnitionOff = retainDriverNameAfterIgnitionOff;
    }

//    ALTER TABLE `imei`
//    ADD COLUMN `is_driver_id_fitted` TINYINT(1) NOT NULL DEFAULT '0' AFTER `is_device_unplugged_enabled`,
//    ADD COLUMN `is_retain_driver_name_after_ignition_off` TINYINT(1) NOT NULL DEFAULT '0' AFTER `is_driver_id_fitted`;

}
