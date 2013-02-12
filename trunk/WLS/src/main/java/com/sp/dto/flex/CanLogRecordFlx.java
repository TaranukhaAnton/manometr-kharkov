package com.sp.dto.flex;

import com.sp.util.Constants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * DTO class which represents CanLogRecord in Flex compablity format
 */
public class CanLogRecordFlx {
    private int id;

    private Date recDate;

    private int unitNumber;

    private Short transmissionReason;

    private Integer triggeredSensorIndex;

    private Integer incomingLogRecordId;

    private Date createdDate;

    private Double lat;

    private Double lon;

    private Map<String, String> sensorValues = new HashMap<String, String>();

    private Map<Integer, String> namesMap = new HashMap<Integer, String>();

    private String sensor0;

    private String sensor1;

    private String sensor2;

    private String sensor3;

    private String sensor4;

    private String sensor5;

    private String sensor6;

    private String sensor7;

    private String sensor8;

    private String sensor9;

    private String sensor10;

    private String sensor11;

    private String sensor12;

    private String sensor13;

    private String sensor14;

    private String sensor15;

    private String sensor16;

    private String sensor17;

    private String sensor18;

    private String sensor19;

    private String sensor20;

    private String sensor21;

    private String sensor22;

    private String sensor23;

    private String sensor24;

    public CanLogRecordFlx(int id) {
        this.id = id;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(int unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Integer getTriggeredSensorIndex() {
        return triggeredSensorIndex;
    }

    public void setTriggeredSensorIndex(Integer triggeredSensorIndex) {
        this.triggeredSensorIndex = triggeredSensorIndex;
    }

    public Integer getIncomingLogRecordId() {
        return incomingLogRecordId;
    }

    public void setIncomingLogRecordId(Integer incomingLogRecordId) {
        this.incomingLogRecordId = incomingLogRecordId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getSensor0() {
        return sensor0;
    }

    public void setSensor0(String sensor0) {
        this.sensor0 = sensor0;
    }

    public String getSensor1() {
        return sensor1;
    }

    public void setSensor1(String sensor1) {
        this.sensor1 = sensor1;
    }

    public String getSensor2() {
        return sensor2;
    }

    public void setSensor2(String sensor2) {
        this.sensor2 = sensor2;
    }

    public String getSensor3() {
        return sensor3;
    }

    public void setSensor3(String sensor3) {
        this.sensor3 = sensor3;
    }

    public String getSensor4() {
        return sensor4;
    }

    public void setSensor4(String sensor4) {
        this.sensor4 = sensor4;
    }

    public Map<String, String> getSensorValues() {
        return sensorValues;
    }

    public void setSensorValues(Map<String, String> sensorValues) {
        this.sensorValues = sensorValues;
    }

    public String getSensor5() {
        return sensor5;
    }

    public void setSensor5(String sensor5) {
        this.sensor5 = sensor5;
    }

    public String getSensor6() {
        return sensor6;
    }

    public void setSensor6(String sensor6) {
        this.sensor6 = sensor6;
    }

    public String getSensor7() {
        return sensor7;
    }

    public void setSensor7(String sensor7) {
        this.sensor7 = sensor7;
    }

    public String getSensor8() {
        return sensor8;
    }

    public void setSensor8(String sensor8) {
        this.sensor8 = sensor8;
    }

    public String getSensor9() {
        return sensor9;
    }

    public void setSensor9(String sensor9) {
        this.sensor9 = sensor9;
    }

    public String getSensor10() {
        return sensor10;
    }

    public void setSensor10(String sensor10) {
        this.sensor10 = sensor10;
    }

    public String getSensor11() {
        return sensor11;
    }

    public void setSensor11(String sensor11) {
        this.sensor11 = sensor11;
    }

    public String getSensor12() {
        return sensor12;
    }

    public void setSensor12(String sensor12) {
        this.sensor12 = sensor12;
    }

    public String getSensor13() {
        return sensor13;
    }

    public void setSensor13(String sensor13) {
        this.sensor13 = sensor13;
    }

    public String getSensor14() {
        return sensor14;
    }

    public void setSensor14(String sensor14) {
        this.sensor14 = sensor14;
    }

    public String getSensor15() {
        return sensor15;
    }

    public void setSensor15(String sensor15) {
        this.sensor15 = sensor15;
    }

    public String getSensor16() {
        return sensor16;
    }

    public void setSensor16(String sensor16) {
        this.sensor16 = sensor16;
    }

    public String getSensor17() {
        return sensor17;
    }

    public void setSensor17(String sensor17) {
        this.sensor17 = sensor17;
    }

    public String getSensor18() {
        return sensor18;
    }

    public void setSensor18(String sensor18) {
        this.sensor18 = sensor18;
    }

    public String getSensor19() {
        return sensor19;
    }

    public void setSensor19(String sensor19) {
        this.sensor19 = sensor19;
    }

    public String getSensor20() {
        return sensor20;
    }

    public void setSensor20(String sensor20) {
        this.sensor20 = sensor20;
    }

    public String getSensor21() {
        return sensor21;
    }

    public void setSensor21(String sensor21) {
        this.sensor21 = sensor21;
    }

    public String getSensor22() {
        return sensor22;
    }

    public void setSensor22(String sensor22) {
        this.sensor22 = sensor22;
    }

    public String getSensor23() {
        return sensor23;
    }

    public void setSensor23(String sensor23) {
        this.sensor23 = sensor23;
    }

    public String getSensor24() {
        return sensor24;
    }

    public void setSensor24(String sensor24) {
        this.sensor24 = sensor24;
    }


    public void fillSensors() {
        sensor0 = getValue(0);
        sensor1 = getValue(1);
        sensor2 = getValue(2);
        sensor3 = getValue(3);
        sensor4 = getValue(4);
        sensor5 = getValue(5);
        sensor6 = getValue(6);
        sensor7 = getValue(7);
        sensor8 = getValue(8);
        sensor9 = getValue(9);
        sensor10 = getValue(10);
        sensor11 = getValue(11);
        sensor12 = getValue(12);
        sensor13 = getValue(13);
        sensor14 = getValue(14);
        sensor15 = getValue(15);
        sensor16 = getValue(16);
        sensor17 = getValue(17);
        sensor18 = getValue(18);
        sensor19 = getValue(19);
        sensor20 = getValue(20);
        sensor21 = getValue(21);
        sensor22 = getValue(22);
        sensor23 = getValue(23);
        sensor24 = getValue(24);
        sensorValues = null;
    }

    private String getValue(int i) {
        return sensorValues.get(namesMap.get(i));
    }

    public Short getTransmissionReason() {
        return transmissionReason;
    }

    //flex serialization
    public void setTransmissionReasonDescr(String str) {
    }

    public String getTransmissionReasonDescr() {
        switch (transmissionReason) {
            case 32:
                return "IP changed / connection up";
            case 33:
                return "GPS Navigation Start";
            case 37:
                return "Engine Start";
            case 44:
                return "Timed Event";
            case 45:
                return "Engine Stop";
            case 47:
                return "Driving Without Authentication";
            case Constants.CAN_TRANS_REASON_IGNITION_OFF:
                return "Ignition Off Event";
            case Constants.CAN_TRANS_REASON_IGNITION_ON:
                return "Ignition On Event";
            case 203:
                return "Pre-Hibernation event";
            default:
                return null;
        }
    }

    public void setTransmissionReason(Short transmissionReason) {
        this.transmissionReason = transmissionReason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRecDate() {
        return recDate;
    }

    public void setRecDate(Date recDate) {
        this.recDate = recDate;
    }

    public Map<Integer, String> getNamesMap() {
        return namesMap;
    }

    public void setNamesMap(Map<Integer, String> namesMap) {
        this.namesMap = namesMap;
    }
}
