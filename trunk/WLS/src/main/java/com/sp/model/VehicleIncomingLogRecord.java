package com.sp.model;

import com.sp.SpContext;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "incoming_log")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class VehicleIncomingLogRecord extends LightVehicleIncomingLogRecord {
    @Column(insertable = false)
    private String postcode;

    @Column(name = "unit_id", updatable = false)
    private Integer vehicleId;

    @Column(name = "tracking_device_id", updatable = false)
    private Integer trackingDeviceId;

    @Column(name = "aoi_descr", insertable = false)
    private String aoiDescr;

    @Column(name = "speed_limit")
    private String speedLimit;

    @Column(name = "poi_descr", insertable = false)
    private String poiDescr;

    @Column(name = "trakm8_message_type")
    private Integer trakm8MessageType;

    @Column(name = "processing_status", insertable = false)
    private Integer processingStatus;

    @Column(name = "road_lat", insertable = false)
    private Double roadLat;

    @Column(name = "road_lon", insertable = false)
    private Double roadLon;

    @Column(name = "distance_to_road", insertable = false)
    private Double distanceToRoad;

    @Column(name = "distance_from_last_msg_calc", updatable = false)
    private Integer distanceFromLastMsgCalc;

    @Column(name = "box_type_id", updatable = false)
    private Integer boxTypeId;

    @Transient
    private double direction;

    @Transient
    private String imageFileName;
    
    @Transient
    private String vehicleDescr;

    @Transient
    private String registrationNumber;

    @Transient
    private int ignitionInputNumber;

    @Transient
    private String input1Name;

    @Transient
    private String input2Name;

    @Transient
    private String input3Name;

    @Transient
    private String input1Str;

    @Transient
    private String input2Str;

    @Transient
    private String input3Str;

    public void copyTo(VehicleIncomingLogRecord copy) {
        super.copyTo(copy);
        copy.postcode = postcode;
        copy.imageFileName = imageFileName;
        copy.vehicleId = vehicleId;
        copy.trackingDeviceId = trackingDeviceId;
        copy.vehicleDescr = vehicleDescr;
        copy.direction = direction;
        copy.registrationNumber = registrationNumber;
        copy.aoiDescr = aoiDescr;
        copy.poiDescr = poiDescr;
        copy.processingStatus = processingStatus;
        copy.roadLat = roadLat;
        copy.roadLon = roadLon;
        copy.distanceToRoad = distanceToRoad;
        copy.distanceFromLastMsgCalc = distanceFromLastMsgCalc;
        copy.ignitionInputNumber = ignitionInputNumber;
        copy.input1Name = input1Name;
        copy.input2Name = input2Name;
        copy.input3Name = input3Name;
        copy.input1Str = input1Str;
        copy.input2Str = input2Str;
        copy.input3Str = input3Str;
        copy.trakm8MessageType = trakm8MessageType;
        copy.speedLimit = speedLimit; 
    }

    public void calculateFields(BaseTrackingDevice trackingDevice, BaseVehicle vehicle) {
        input1Str = "n/a";
        input2Str = "n/a";
        input3Str = "n/a";
        input1Name = "";
        input2Name = "";
        input3Name = "";
        if(trackingDevice!=null){
            if (!trackingDevice.isStealthInputPresent()) {
                if (trackingDevice.getIgnitionInputNumber() != 1) {
                    if (StringUtils.isNotEmpty(trackingDevice.getDigitalInput1Name())) {
                        input1Name = trackingDevice.getDigitalInput1Name();
                        input1Str = isInput1High() ? trackingDevice.getDigitalInput1ValueHigh() : trackingDevice.getDigitalInput1ValueLow();
                    }
                } else {
                    if (StringUtils.isNotEmpty(trackingDevice.getDigitalInput2Name())) {
                        input1Name = trackingDevice.getDigitalInput2Name();
                        input1Str = isInput2High() ? trackingDevice.getDigitalInput2ValueHigh() : trackingDevice.getDigitalInput2ValueLow();
                    }
                }
                if (trackingDevice.getIgnitionInputNumber() != 2 && trackingDevice.getIgnitionInputNumber() != 1) {
                    if (StringUtils.isNotEmpty(trackingDevice.getDigitalInput2Name())) {
                        input2Name = trackingDevice.getDigitalInput2Name();
                        input2Str = isInput2High() ? trackingDevice.getDigitalInput2ValueHigh() : trackingDevice.getDigitalInput2ValueLow();
                    }
                } else {
                    if (StringUtils.isNotEmpty(trackingDevice.getDigitalInput3Name())) {
                        input2Name = trackingDevice.getDigitalInput3Name();
                        input2Str = isInput3High() ? trackingDevice.getDigitalInput3ValueHigh() : trackingDevice.getDigitalInput3ValueLow();
                    }
                }
                if (trackingDevice.getIgnitionInputNumber() != 3 &&
                        trackingDevice.getIgnitionInputNumber() != 2 &&
                        trackingDevice.getIgnitionInputNumber() != 1) {
                    if (StringUtils.isNotEmpty(trackingDevice.getDigitalInput3Name())) {
                        input3Name = trackingDevice.getDigitalInput3Name();
                        input3Str = isInput3High() ? trackingDevice.getDigitalInput3ValueHigh() : trackingDevice.getDigitalInput3ValueLow();
                    }
                } else {
                    if (StringUtils.isNotEmpty(trackingDevice.getDigitalInput4Name())) {
                        input3Name = trackingDevice.getDigitalInput4Name();
                        input3Str = isInput4High() ? trackingDevice.getDigitalInput4ValueHigh() : trackingDevice.getDigitalInput4ValueLow();
                    }
                }

            }
            direction = trackingDevice.isCalculatedOrientation() ? getDirectionCalc() : getDirectionDevice();
            ignitionInputNumber = trackingDevice.getIgnitionInputNumber();
        } else {
            direction = getDirectionDevice();
        }
        imageFileName = vehicle.getType().getImageFileName();
        vehicleDescr = vehicle.getDescr();
        registrationNumber = vehicle.getRegistrationNumber();

    }


    public double getDirection(){
       return direction;
    }

    public String getFactoredSpeedLimit(){
        if(StringUtils.isNotEmpty(speedLimit)){
            int index = SpContext.getUserDetailsInfo().getUserPrefs().getDistanceMetric().equals("KM") ? 0 : 1;
            return speedLimit.split("/")[index];
        }else{
            return speedLimit;
        }
    } 

    public void setFactoredSpeedLimit(String factoredSpeedLimit) {

    }

    public int getMaxFactoredSpeedLimit(){
        if(StringUtils.isNotEmpty(getFactoredSpeedLimit())){
            return Integer.parseInt(getFactoredSpeedLimit().indexOf("-")!=-1 ? getFactoredSpeedLimit().split("-")[1] : getFactoredSpeedLimit().replace("<","").replace(">",""));
        }
        return -1;
    }

    public String getAboveSpeedLimit(){
        if (StringUtils.isEmpty(getFactoredSpeedLimit())){
            return "-";
        }
        int maxFactSpeed = getMaxFactoredSpeedLimit();
        return getFactoredSpeed()>maxFactSpeed ? (getGtOrLtSymbolIfPresent()+String.valueOf(getFactoredSpeed() - maxFactSpeed)) : "-";
    }

    public float getAboveSpeedLimitValue(){
        if (StringUtils.isEmpty(getFactoredSpeedLimit())){
            return -1;
        }
        int maxFactSpeed = getMaxFactoredSpeedLimit();
        return getFactoredSpeed()>maxFactSpeed ? (getFactoredSpeed() - maxFactSpeed) : -1;
    }

    public void setAboveSpeedLimit(String val){
    }

    private String getGtOrLtSymbolIfPresent(){
         if(getFactoredSpeedLimit().indexOf(">")!=-1){
             return ">";
         }else if(getFactoredSpeedLimit().indexOf("<")!=-1){
             return "<";
         }
        return "";
    }

    public String getPercentAboveSpeedLimit(){
        String aboveSpeedLimit = getAboveSpeedLimit().replace(">","").replace("<","");
        if (aboveSpeedLimit.equals("-")) return aboveSpeedLimit;
        int maxFactoredSpeedLimit = getMaxFactoredSpeedLimit();
        return getGtOrLtSymbolIfPresent() + String.valueOf(Math.round((Float.parseFloat(aboveSpeedLimit) * 100 / maxFactoredSpeedLimit)*100)/100);
    }

    public int getPercentAboveSpeedLimitValue(){
        String aboveSpeedLimit = getAboveSpeedLimit().replace(">","").replace("<","");
        if (aboveSpeedLimit.equals("-")) return -1;
        int maxFactoredSpeedLimit = getMaxFactoredSpeedLimit();
        return Math.round((Float.parseFloat(aboveSpeedLimit) * 100 / maxFactoredSpeedLimit)*100)/100;
    }

    public void setPercentAboveSpeedLimit(String val){}

    public String getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(String speedLimit) {
        this.speedLimit = speedLimit;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }


    public void setIgnitionStr(String s) {
    }

    //todo: remove this and use ignitionAcvite
    public String getIgnitionStr() {
        return isIgnitionActive() ? "ON" : "OFF";
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getTrackingDeviceId() {
        return trackingDeviceId;
    }

    public void setTrackingDeviceId(Integer trackingDeviceId) {
        this.trackingDeviceId = trackingDeviceId;
    }

    public String getVehicleDescr() {
        return vehicleDescr;
    }

    public void setVehicleDescr(String vehicleDescr) {
        this.vehicleDescr = vehicleDescr;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setAoiDescr(String aoiDescr) {
        this.aoiDescr = aoiDescr;
    }

    public String getAoiDescr() {
        return aoiDescr;
    }

    public Integer getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(Integer processingStatus) {
        this.processingStatus = processingStatus;
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

    public Integer getDistanceFromLastMsgCalc() {
        return distanceFromLastMsgCalc;
    }

    public void setDistanceFromLastMsgCalc(Integer distanceFromLastMsgCalc) {
        this.distanceFromLastMsgCalc = distanceFromLastMsgCalc;
    }

    public String getPoiDescr() {
        return poiDescr;
    }

    public void setPoiDescr(String poiDescr) {
        this.poiDescr = poiDescr;
    }

    
    public int getIgnitionInputNumber() {
        return ignitionInputNumber;
    }

    public void setIgnitionInputNumber(int ignitionInputNumber) {
        this.ignitionInputNumber = ignitionInputNumber;
    }

    public String getInput1Name() {
        return input1Name;
    }

    public void setInput1Name(String input1Name) {
        this.input1Name = input1Name;
    }

    public String getInput2Name() {
        return input2Name;
    }

    public void setInput2Name(String input2Name) {
        this.input2Name = input2Name;
    }

    public String getInput3Name() {
        return input3Name;
    }

    public void setInput3Name(String input3Name) {
        this.input3Name = input3Name;
    }

    public String getInput1Str() {
        return input1Str;
    }

    public void setInput1Str(String input1Str) {
        this.input1Str = input1Str;
    }

    public String getInput2Str() {
        return input2Str;
    }

    public void setInput2Str(String input2Str) {
        this.input2Str = input2Str;
    }

    public String getInput3Str() {
        return input3Str;
    }

    public void setInput3Str(String input3Str) {
        this.input3Str = input3Str;
    }

    public Integer getBoxTypeId() {
        return boxTypeId;
    }

    public void setBoxTypeId(Integer boxTypeId) {
        this.boxTypeId = boxTypeId;
    }

    public Integer getTrakm8MessageType() {
        return trakm8MessageType;
    }

    public void setTrakm8MessageType(Integer trakm8MessageType) {
        this.trakm8MessageType = trakm8MessageType;
    }

    public String getTrakm8MessageTypeDescr() {
        if(trakm8MessageType==null) return "N/A";
        switch (trakm8MessageType){
            case 1:
                return "GPS";
            case 2:
                return "Journey Start";
            case 3:
                return "Journey End";
            case 4:
                return "Idle Start";
            case 5:
                return "Idle End";
            case 6:
                return "Journey Idle";
            case 7:
                return "Excessive Idle Start";
            case 8:
                return "System Event";
            case 9:
                return "Over-speed start";
            case 10:
                return "Over-speed end";
            case 11:
                return "Excessive Accel Event";
            case 12:
                return "Excessive Decel Event";
            case 13:
                return "Geofence In Event";
            case 14:
                return "Geofence Out Event";
            case 15:
                return "Panic";
            case 16:
                return "Poll";
            case 17:
                return "Tag In";
            case 18:
                return "Tag Out";
            case 19:
                return "Digital Input Goes High";
            case 20:
                return "Digital Input Goes Low";
            case 21:
                return "Journey Type Change";
            case 22:
                return "Pending Fault Code Detected";
            case 23:
                return "Stored Fault Code Detected";
            case 24:
                return "CAN PID Over Range";
            case 25:
                return "CAN PID Under Range";
            case 26:
                return "CAN PID In Range";
            case 27:
                return "CAN DER Over Range";
            case 28:
                return "CAN DER Under Range";
            case 29:
                return "CAN DER In Range";
            case 30:
                return "Tamper";
            case 31:
                return "Theft Attempt";
            case 32:
                return "Power Loss";
            case 33:
                return "Power Restore";
            case 34:
                return "Shield Breach";
            case 35:
                return "Alarm Activated";
            case 36:
                return "Alarm Deactivated";
            case 37:
                return "Alarm Clear";
            case 38:
                return "Ignition On";
            case 39:
                return "Ignition Off";
            case 40:
                return "Heartbeat";
            case 41:
                return "Alarm Periodic";
            case 42:
                return "Alarm Distance";
            case 43:
                return "CAN PTO On";
            case 44:
                return "CAN PTO Off";
            case 45:
                return "CAN Idle End";
            case 46:
                return "CAN Idle Start";
            case 47:
                return "Temperature Tag out of bounds";
            case 48:
                return "Temperature Tag in bounds";
            case 49:
                return "Temperature Tag Received";
            case 50:
                return "Sudden Stop";
            case 51:
                return "RPM Over Threshold";
            case 52:
                return "GEO alert";
            case 53:
                return "Set ODO";
            case 54:
                return "Throttle over threshold";
            default:
                return "Undefined";
        }
    }

}
