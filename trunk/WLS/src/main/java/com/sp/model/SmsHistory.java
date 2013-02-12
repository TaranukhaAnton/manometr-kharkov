package com.sp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: paveliva
 * Date: 12.08.2009
 * Time: 10:34:53
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "sms_history")
public class SmsHistory extends BaseModel {

    public enum StatusForSelection {
        Undeliverable, Delivered, Sent, Pending, Command_rejected
    }
    
    @Column(name = "message_id")
    private String messageId;

    @Column(name = "source")
    private String source;

    @Column(name = "destination")
    private String destination;

    @Column(name = "body")
    private String body;

    @Column(name = "status")
    private String status;

    @Column(name = "item_type")
    private String itemType;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private SecurityUser user;

    public void copyTo(SmsHistory copy) {
        super.copyTo(copy);
        this.setBody(copy.getBody());
        this.setCreatedDate(copy.getCreatedDate());
        this.setDestination(copy.getDestination());
        this.setItemType(copy.getItemType());
        this.setMessageId(copy.getMessageId());
        this.setSource(copy.getSource());
        this.setStatus(copy.getStatus());
        this.setUser(copy.getUser());
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public SecurityUser getUser() {
        return user;
    }

    public void setUser(SecurityUser user) {
        this.user = user;
    }
}
