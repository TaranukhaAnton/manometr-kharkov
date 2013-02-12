package com.sp.dto.flex;

import com.sp.dto.FastSmsInboundDto;
import com.sp.model.IncomingLogRecord;
import com.sp.model.SmsMessage;
import com.sp.model.Vehicle;

import java.util.List;
import java.util.Map;

/**
 * Created by Alexander
 */
public class SmsMessageDto {

    private Integer id;
    private String vehicleReg;
    private String userFullName;
    private String accountDescr;
    private int accountId;
    private String parnterDescr;
    private String body;
    private String destinationNumber;
    private String vehicleLocation;
    private String vehiclePostcode;
    private long sentDate;
    private Long deliveredDate;
    private String status;
    private int vehicleId;
    private List<Integer> vehicleIdList;
    private String fastSmsMessageId;
    private String  subMessagesStatus;
    private String type;

    public SmsMessageDto(SmsMessage s) {
        id = s.getId();
        vehicleReg = s.getVehicle().getRegistrationNumber();
        userFullName = s.getSecurityUser().getFirstName() + " " + s.getSecurityUser().getLastName();
        accountDescr = s.getAccount().getDescr();
        accountId = s.getAccount().getId();
        parnterDescr = s.getAccount().getReseller().getDescr();
        body = s.getBody();
        destinationNumber = s.getDestinationNumber();
        vehicleLocation = s.getVehicleLocation();
        vehiclePostcode = s.getVehiclePostcode();
        sentDate = s.getSentDate().getTime();
        deliveredDate = s.getDeliveredDate()!=null ? s.getDeliveredDate().getTime() : null;
        status = s.getStatus();
        vehicleId = s.getVehicle().getId();
        fastSmsMessageId = s.getFastSmsMessageIds();
        subMessagesStatus = s.getSubMessagesStatus();
        type = "OUT";
    }

    public SmsMessageDto(FastSmsInboundDto dto, Map<String,Vehicle> cabPhoneVehicleMap, IncomingLogRecord record){
        type = "IN";
        Vehicle curVeh = cabPhoneVehicleMap.get(dto.getFromNumber());
        vehicleReg = curVeh.getRegistrationNumber();
        userFullName = dto.getFromNumber();
        body = dto.getBody();
        vehicleLocation = record.getStreetName();
        vehiclePostcode = record.getPostcode();
        deliveredDate = dto.getDeliveredDate().getTime();
        status = "RECEIVED";
        vehicleId = curVeh.getId();
    }

    public SmsMessageDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVehicleReg() {
        return vehicleReg;
    }

    public void setVehicleReg(String vehicleReg) {
        this.vehicleReg = vehicleReg;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getAccountDescr() {
        return accountDescr;
    }

    public void setAccountDescr(String accountDescr) {
        this.accountDescr = accountDescr;
    }

    public String getParnterDescr() {
        return parnterDescr;
    }

    public void setParnterDescr(String parnterDescr) {
        this.parnterDescr = parnterDescr;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(String destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    public String getVehicleLocation() {
        return vehicleLocation;
    }

    public void setVehicleLocation(String vehicleLocation) {
        this.vehicleLocation = vehicleLocation;
    }

    public String getVehiclePostcode() {
        return vehiclePostcode;
    }

    public void setVehiclePostcode(String vehiclePostcode) {
        this.vehiclePostcode = vehiclePostcode;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public long getSentDate() {
        return sentDate;
    }

    public void setSentDate(long sentDate) {
        this.sentDate = sentDate;
    }

    public Long getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Long deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getFastSmsMessageId() {
        return fastSmsMessageId;
    }

    public void setFastSmsMessageId(String fastSmsMessageId) {
        this.fastSmsMessageId = fastSmsMessageId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public List<Integer> getVehicleIdList() {
        return vehicleIdList;
    }

    public void setVehicleIdList(List<Integer> vehicleIdList) {
        this.vehicleIdList = vehicleIdList;
    }

    public String getSubMessagesStatus() {
        return subMessagesStatus;
    }

    public void setSubMessagesStatus(String subMessagesStatus) {
        this.subMessagesStatus = subMessagesStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
