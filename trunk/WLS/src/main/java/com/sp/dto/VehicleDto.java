package com.sp.dto;

import com.sp.SpContext;
import com.sp.model.BaseModel;
import com.sp.model.BaseTrackingDevice;
import com.sp.model.IncomingLogRecord;
import com.sp.model.Vehicle;
import com.sp.util.Constants;
import com.sp.util.Util;

import java.util.Calendar;

/**
 * Created by Alexander
 */
public class VehicleDto extends BaseModel {
    private String registrationNumber;
    private String curAddress;
    private boolean selected;
    private float factoredSpeed;
    private boolean inStealthMode;
    private long renewalDate;
    private double lat;
    private double lon;
    private String aoiPoiDescr;
    private int costPerMile;
    private boolean ignitionStatusVisibility;
    private boolean ignitionActive;
    private Long immobilizationDate;
    private boolean immobilizeStatus;
    private Double roadLat;
    private Double roadLon;
    private Double distanceToRoad;
    private String clientInfo;
    private String digitalInput1Info; // in format digitalInput1Color_digitalInput1Name:digitalInput1Value
    private String digitalInput2Info;
    private String digitalInput3Info;
    private String digitalInput4Info;
    private int activeDinsCount;
    private boolean expanded;
    private String clientDescr;
    private double costToDistance;
    private String directionOfTravel;
    private String ignitionStatus;
    private String imageFileName;
    private String vehicleDriverDescr;
    private String fleetId;
    private String cabPhone;
    // for jsf only
    private String ignitionColor;
    private String ignitionLabel;
    private String directionOfTravelUpperCase;
    private boolean accelAvailable;
    private boolean replicationStatus;
    private boolean alwaysDisplayDriverName;
    private boolean locked;
    private boolean dotOnMap;




    private String buildDigitalInputInfo(int color, String name, String value) {
        if (Util.isNullOrEmptyStr(name) || Util.isNullOrEmptyStr(value)) return null;
        StringBuilder inputInfo = new StringBuilder();
        inputInfo.append(color);
        inputInfo.append("_");
        inputInfo.append(name);
        inputInfo.append(":");
        inputInfo.append(value);
        return inputInfo.toString();
    }

    public VehicleDto() {}

    
    /**
     * it is used for Back To The Future report only!
     * In this constructor we MUST fill new fields based on IncomingLogRecord i.
     * And if these fields are absent in the IncomingLogRecord, ask Ross. 
     */
    public VehicleDto(IncomingLogRecord i, VehicleDto v, Vehicle vehicleWithPastDinInfo, BaseTrackingDevice trackingDevice) {
        setId(i.getVehicleId());
        curAddress = Util.getJoinedStrs(i.getStreetName(), i.getPostcode()) ;
        if (SpContext.getUserDetailsInfo().getReseller().isStickyRoads() &&
                SpContext.getUserDetailsInfo().getReseller().isPrefixSmLocationWithNr() &&
                i.getDistanceToRoad() != null &&
                i.getDistanceToRoad() > 20) {                                                   
            curAddress = "Nr. " + curAddress;
        }
        selected = v.selected;
        factoredSpeed = Util.getFactoredSpeed((int) i.getSpeed());
        inStealthMode = i.isInStealthMode();
        renewalDate = i.getRecDate().getTime();
        lat = i.getLat();
        lon = i.getLon();
        vehicleDriverDescr = null; // hide this value
        aoiPoiDescr = i.getAoiPoiDescr();
        costPerMile = -1; // hide this value
        ignitionStatusVisibility = v.isIgnitionStatusVisibility();
        immobilizeStatus = false; // I don't need this value because "Action" drop down is invisible in the SM
        roadLat = i.getRoadLat();
        roadLon = i.getRoadLon();
        distanceToRoad = i.getDistanceToRoad();
        clientInfo = null; // hide this value
        cabPhone = null; //  for "Action" drop down
        digitalInput1Info = buildDigitalInputInfo(vehicleWithPastDinInfo.getDigitalInput1Color(), trackingDevice.getDigitalInput1Name(), vehicleWithPastDinInfo.getDigitalInput1Value());
        digitalInput2Info = buildDigitalInputInfo(vehicleWithPastDinInfo.getDigitalInput2Color(), trackingDevice.getDigitalInput2Name(), vehicleWithPastDinInfo.getDigitalInput2Value());
        digitalInput3Info = buildDigitalInputInfo(vehicleWithPastDinInfo.getDigitalInput3Color(), trackingDevice.getDigitalInput3Name(), vehicleWithPastDinInfo.getDigitalInput3Value());
        digitalInput4Info = buildDigitalInputInfo(vehicleWithPastDinInfo.getDigitalInput4Color(), trackingDevice.getDigitalInput4Name(), vehicleWithPastDinInfo.getDigitalInput4Value());
        activeDinsCount = vehicleWithPastDinInfo.getActiveDinsCount();
        expanded = v.expanded;
        clientDescr = v.getClientDescr();
        costToDistance = -1; 
        directionOfTravel = Util.angleToDirectionOfTravel(i.getDirectionDevice()); // use device direction
        ignitionActive = i.isIgnitionActive();
        ignitionStatus = Util.getIgnitionStatus(ignitionActive, i.getSpeed()).toString();
        imageFileName = v.getImageFileName();// cur val
        registrationNumber = v.getRegistrationNumber();// cur val
        immobilizationDate = null; //  for "Action" drop down
        fleetId = v.getFleetId(); // cur val
     //   ignitionColor, ignitionLabel, directionOfTravelUpperCase for jsf SM only
    //    accelAvailable  for diagnostic report
        replicationStatus = false;//  for "Action" drop down
        alwaysDisplayDriverName = trackingDevice.isAlwaysDisplayDriverName()   // cur value
                && SpContext.getUserDetailsInfo().getReseller().isAlwaysDisplayDriverName();
        locked = false;
    }

    public static boolean isVehicleLocked(BaseTrackingDevice trackingDevice, String repType, boolean isLockedWhenDeviceNull){
        if(trackingDevice == null){
            return isLockedWhenDeviceNull;
        }
        if(!Util.isNullOrEmptyStr(repType)){
            if(Constants.ReportType.Speeding.toString().equals(repType)){
                return !trackingDevice.isSpeedLimitAllowed();
            }else if(Constants.ReportType.DriverBehaviour.toString().equals(repType)) {
                int boxTypeId = trackingDevice.getBoxType().getId();
                if(boxTypeId == Constants.FM11_BOX_TYPE_ID ){
                    return !(trackingDevice.isGreenDrivingAllowed() || trackingDevice.isOverspeedAllowed());
                }else if(Util.isCalampDevice(boxTypeId)){
                    return !trackingDevice.isGreenDrivingAllowed();
                }
                return true;
            }
        }
        return false;
    }

    public VehicleDto(Vehicle v, BaseTrackingDevice trackingDevice, String repType, boolean satelliteDrift, String address) {
        this(v,trackingDevice, satelliteDrift, address);
        locked = isVehicleLocked(trackingDevice, repType,false);
        selected = !locked && selected;
        expanded = locked || expanded;
    }

    public VehicleDto(Vehicle v, BaseTrackingDevice trackingDevice, boolean satelliteDrift, String address) {
        setId(v.getId());
        if (address == null) {
            curAddress = v.getCurAddress();
        } else {
            curAddress = address;
        }
        dotOnMap = v.isDotOnMap();
        if (SpContext.getUserDetailsInfo().getReseller().isStickyRoads() &&
                SpContext.getUserDetailsInfo().getReseller().isPrefixSmLocationWithNr() &&
                v.getDistanceToRoad() != null &&
                v.getDistanceToRoad() > 20 && curAddress != null) {
            curAddress = "Nr. " + curAddress;
        }
        selected = v.isSelected();
        if (satelliteDrift && !v.isIgnitionActive()) {
            factoredSpeed = 0;
        } else {
            factoredSpeed = v.getFactoredSpeed();
        }
        inStealthMode = v.isInStealthMode();
        renewalDate = Util.getDaylightTimeUK(v.getRenewalDate(), trackingDevice).getTime();
        lat = v.getLat();
        lon = v.getLon();
        vehicleDriverDescr = v.getCurrentDriver() != null
                ? v.getCurrentDriver().getDescr()
                : (v.getAssociatedRegularDriver() != null
                    ? v.getAssociatedRegularDriver().getDescr()
                    : null);

        aoiPoiDescr = v.getAoiPoiDescr();
        costPerMile = v.getCostPerMile();
        ignitionStatusVisibility = v.isIgnitionStatusVisibility();
        immobilizeStatus = v.getImmobilizeStatus();
        roadLat = v.getRoadLat();
        roadLon = v.getRoadLon();
        distanceToRoad = v.getDistanceToRoad();
        clientInfo = v.getClientInfo();
        cabPhone = v.getCabPhone();
        digitalInput1Info = buildDigitalInputInfo(v.getDigitalInput1Color(), v.getDigitalInput1Name(), v.getDigitalInput1Value());
        digitalInput2Info = buildDigitalInputInfo(v.getDigitalInput2Color(), v.getDigitalInput2Name(), v.getDigitalInput2Value());
        digitalInput3Info = buildDigitalInputInfo(v.getDigitalInput3Color(), v.getDigitalInput3Name(), v.getDigitalInput3Value());
        digitalInput4Info = buildDigitalInputInfo(v.getDigitalInput4Color(), v.getDigitalInput4Name(), v.getDigitalInput4Value());
        activeDinsCount = v.getActiveDinsCount();
        expanded = v.isExpanded();
        clientDescr = v.getClientDescr();
        costToDistance = getCostToDistance(v);
        directionOfTravel = v.getDirectionOfTravel();
        // TODO maybe we need to find less expensive approach
        if (trackingDevice != null && trackingDevice.getBoxType().getId() == Constants.CALAMP_3000_BOX_TYPE_ID &&
                trackingDevice.isDeviceUnpluggedEnabled()) {
            if (v.getTimestamp().getTime() < Util.getCalampUnplaggedDelayTime()) {
                ignitionStatus = Constants.IgnitionStatus.Unplugged.toString();
            }  else {
                ignitionStatus = v.getIgnitionStatus();
            }
        }  else {
            ignitionStatus = v.getIgnitionStatus();
        }   
        imageFileName = v.getType().getImageFileName();
        registrationNumber = v.getRegistrationNumber();
        immobilizationDate = v.getImmobilizationDate() != null ? v.getImmobilizationDate().getTime() : null;
        ignitionActive = v.isIgnitionActive();
        fleetId = v.getFleetId();
        ignitionColor = v.getIgnitionColor();
        ignitionLabel = v.getIgnitionLabel();
        directionOfTravelUpperCase = v.getDirectionOfTravelUpperCase();
        accelAvailable = v.getAccelAvailable();
        replicationStatus = trackingDevice != null && trackingDevice.isReplicationEnabled();
        alwaysDisplayDriverName = trackingDevice != null && trackingDevice.isAlwaysDisplayDriverName()
                && SpContext.getUserDetailsInfo().getReseller().isAlwaysDisplayDriverName();

    }

    private static double getCostToDistance(Vehicle v){
        if(SpContext.getUserDetailsInfo().getReseller().isZeroTodaysCostAtMidnight()){
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY,0);
            cal.set(Calendar.MINUTE,0);
            cal.set(Calendar.SECOND,0);
            if(v.getRenewalDate().before(cal.getTime())){
                return 0;
            }
        }
        return (Math.round((v.getCostPerHourToDistance() + v.getCostPerMileToDistance()) * 100)) / 100d;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getCurAddress() {
        return curAddress;
    }

    public void setCurAddress(String curAddress) {
        this.curAddress = curAddress;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = !locked && selected;
    }

    public float getFactoredSpeed() {
        return factoredSpeed;
    }

    public void setFactoredSpeed(float factoredSpeed) {
        this.factoredSpeed = factoredSpeed;
    }

    public boolean isInStealthMode() {
        return inStealthMode;
    }

    public void setInStealthMode(boolean inStealthMode) {
        this.inStealthMode = inStealthMode;
    }

    public long getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(long renewalDate) {
        this.renewalDate = renewalDate;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAoiPoiDescr() {
        return aoiPoiDescr;
    }

    public void setAoiPoiDescr(String aoiPoiDescr) {
        this.aoiPoiDescr = aoiPoiDescr;
    }

    public int getCostPerMile() {
        return costPerMile;
    }

    public void setCostPerMile(int costPerMile) {
        this.costPerMile = costPerMile;
    }

    public boolean isIgnitionStatusVisibility() {
        return ignitionStatusVisibility;
    }

    public void setIgnitionStatusVisibility(boolean ignitionStatusVisibility) {
        this.ignitionStatusVisibility = ignitionStatusVisibility;
    }

    public boolean isIgnitionActive() {
        return ignitionActive;
    }

    public void setIgnitionActive(boolean ignitionActive) {
        this.ignitionActive = ignitionActive;
    }

    public Long getImmobilizationNumber() {
        return SpContext.getCachedImmobilizationNumberByVehicleId(getId());
    }

    //for flex serialization
    public void setImmobilizationNumber(Long immobilizationNumber) {
    }

    public Long getImmobilizationDate() {
        return immobilizationDate;
    }

    public void setImmobilizationDate(Long immobilizationDate) {
        this.immobilizationDate = immobilizationDate;
    }

    public boolean isImmobilizeStatus() {
        return immobilizeStatus;
    }

    public void setImmobilizeStatus(boolean immobilizeStatus) {
        this.immobilizeStatus = immobilizeStatus;
    }

    public Double getRoadLat() {
        return roadLat;
    }

    public void setRoadLat(Double roadLat) {
        this.roadLat = roadLat;
    }

    public Double getRoadLon() {
        return roadLon;
    }

    public void setRoadLon(Double roadLon) {
        this.roadLon = roadLon;
    }

    public Double getDistanceToRoad() {
        return distanceToRoad;
    }

    public void setDistanceToRoad(Double distanceToRoad) {
        this.distanceToRoad = distanceToRoad;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getDigitalInput1Info() {
        return digitalInput1Info;
    }

    public void setDigitalInput1Info(String digitalInput1Info) {
        this.digitalInput1Info = digitalInput1Info;
    }

    public String getDigitalInput2Info() {
        return digitalInput2Info;
    }

    public void setDigitalInput2Info(String digitalInput2Info) {
        this.digitalInput2Info = digitalInput2Info;
    }

    public String getDigitalInput3Info() {
        return digitalInput3Info;
    }

    public void setDigitalInput3Info(String digitalInput3Info) {
        this.digitalInput3Info = digitalInput3Info;
    }

    public String getDigitalInput4Info() {
        return digitalInput4Info;
    }

    public void setDigitalInput4Info(String digitalInput4Info) {
        this.digitalInput4Info = digitalInput4Info;
    }

    public int getActiveDinsCount() {
        return activeDinsCount;
    }

    public void setActiveDinsCount(int activeDinsCount) {
        this.activeDinsCount = activeDinsCount;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = locked || expanded;
    }

    public String getClientDescr() {
        return clientDescr;
    }

    public void setClientDescr(String clientDescr) {
        this.clientDescr = clientDescr;
    }

    public double getCostToDistance() {
        return costToDistance;
    }

    public void setCostToDistance(double costToDistance) {
        this.costToDistance = costToDistance;
    }

    public String getDirectionOfTravel() {
        return directionOfTravel;
    }

    public void setDirectionOfTravel(String directionOfTravel) {
        this.directionOfTravel = directionOfTravel;
    }

    public String getIgnitionStatus() {
        return ignitionStatus;
    }

    public void setIgnitionStatus(String ignitionStatus) {
        this.ignitionStatus = ignitionStatus;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getVehicleDriverDescr() {
        return vehicleDriverDescr;
    }

    public void setVehicleDriverDescr(String vehicleDriverDescr) {
        this.vehicleDriverDescr = vehicleDriverDescr;
    }

    public String getFleetId() {
        return fleetId;
    }

    public void setFleetId(String fleetId) {
        this.fleetId = fleetId;
    }

    public String getIgnitionColor() {
        return ignitionColor;
    }

    public String getIgnitionLabel() {
        return ignitionLabel;
    }

    public String getDirectionOfTravelUpperCase() {
        return directionOfTravelUpperCase;
    }

    public boolean getAccelAvailable() {
        return accelAvailable;
    }

    public String getTypeDescr() {
        return "Vehicle";
    }

    public boolean isReplicationStatus() {
        return replicationStatus;
    }

    public void setReplicationStatus(boolean replicationStatus) {
        this.replicationStatus = replicationStatus;
    }

    public String getCabPhone() {
        return cabPhone;
    }

    public void setCabPhone(String cabPhone) {
        this.cabPhone = cabPhone;
    }

    public boolean isAlwaysDisplayDriverName() {
        return alwaysDisplayDriverName;
    }

    public void setAlwaysDisplayDriverName(boolean alwaysDisplayDriverName) {
        this.alwaysDisplayDriverName = alwaysDisplayDriverName;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isDotOnMap() {
        return dotOnMap;
    }

    public void setDotOnMap(boolean dotOnMap) {
        this.dotOnMap = dotOnMap;
    }

}
