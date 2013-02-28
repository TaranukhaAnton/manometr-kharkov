package org.krams.tutorial.dto;


import org.apache.commons.lang.StringUtils;
import org.krams.tutorial.domain.Vehicle;

/**
 * Created by Alexander
 */
public class VehicleDto  {
    int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String buildDigitalInputInfo(int color, String name, String value) {
        if ( StringUtils.isBlank(name) || StringUtils.isBlank(value)) return null;


        StringBuilder inputInfo = new StringBuilder();
        inputInfo.append(color);
        inputInfo.append("_");
        inputInfo.append(name);
        inputInfo.append(":");
        inputInfo.append(value);
        return inputInfo.toString();
    }

    public VehicleDto() {}

    


    public static boolean isVehicleLocked( String repType, boolean isLockedWhenDeviceNull){

        return false;
    }



    public VehicleDto(Vehicle v) {
        setId(v.getId());
            curAddress = v.getCurAddress();

        dotOnMap = v.isDotOnMap();

        selected = v.isSelected();
        factoredSpeed = v.getFactoredSpeed();
        inStealthMode = v.isInStealthMode();
        renewalDate = 0L;
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
              ignitionStatus = v.getIgnitionStatus();

        imageFileName = v.getType().getImageFileName();
        registrationNumber = v.getRegistrationNumber();
        immobilizationDate = v.getImmobilizationDate() != null ? v.getImmobilizationDate().getTime() : null;
        ignitionActive = v.isIgnitionActive();
        fleetId = v.getFleetId();
        ignitionColor = v.getIgnitionColor();
        ignitionLabel = v.getIgnitionLabel();
        directionOfTravelUpperCase = v.getDirectionOfTravelUpperCase();
        accelAvailable = v.getAccelAvailable();
        replicationStatus =true;
        alwaysDisplayDriverName = true;

    }

    private static double getCostToDistance(Vehicle v){
       return 0l;
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

        return 0l;
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
