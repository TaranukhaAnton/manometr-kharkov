package com.sp.model;

import com.sp.dto.flex.SmsMessageDto;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Alexander
 */
@Entity
@Table(name = "sms_messages")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class SmsMessage extends BaseModel{

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private SecurityUser securityUser;

    private String body;

    @Column(name = "destination_number")
    private String destinationNumber;

    @Column(name = "vehicle_location")
    private String vehicleLocation;

    @Column(name = "vehicle_postcode")
    private String vehiclePostcode;


    @Column(name = "sent_date")
    private Date sentDate;

    @Column(name = "delivered_date")
    private Date deliveredDate;

    private String status;

    @Column(name = "fast_sms_message_id")
    private String fastSmsMessageIds;

    @Column(name = "sub_messages_status")
    private String  subMessagesStatus;


    public SmsMessage(SmsMessageDto smsMessageDto, Vehicle vehicle, SecurityUser securityUser, Account account) {
        Vehicle vehicleDto = new Vehicle();
        vehicle.copyTo(vehicleDto);
        setVehicle(vehicle);
        setBody(smsMessageDto.getBody());
        setSentDate(new Date(smsMessageDto.getSentDate()));
        setSecurityUser(securityUser);
        setAccount(account);
        setVehicleLocation(vehicleDto.getCurStreet());
        setVehiclePostcode(vehicleDto.getCurPostcode());
        setDestinationNumber(vehicleDto.getCabPhone());
        setStatus(smsMessageDto.getStatus());
        setFastSmsMessageIds(smsMessageDto.getFastSmsMessageId());
    }

    public SmsMessage() {
        
    }

    public void copyTo(SmsMessage copy){
        super.copyTo(copy);
        copy.vehicle = vehicle;
        copy.securityUser = securityUser;
        copy.account = account;
        copy.body = body;
        copy.vehicleLocation = vehicleLocation;
        copy.vehiclePostcode = vehiclePostcode;
        copy.sentDate = sentDate;
        copy.deliveredDate = deliveredDate;
        copy.status = status;
        copy.destinationNumber = destinationNumber;
        copy.fastSmsMessageIds = fastSmsMessageIds;
        copy.subMessagesStatus = subMessagesStatus;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public SecurityUser getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
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

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public Date getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFastSmsMessageIds() {
        return fastSmsMessageIds;
    }

    public void setFastSmsMessageIds(String fastSmsMessageIds) {
        this.fastSmsMessageIds = fastSmsMessageIds;
    }

    public String getSubMessagesStatus() {
        return subMessagesStatus;
    }

    public void setSubMessagesStatus(String subMessagesStatus) {
        this.subMessagesStatus = subMessagesStatus;
    }
}
