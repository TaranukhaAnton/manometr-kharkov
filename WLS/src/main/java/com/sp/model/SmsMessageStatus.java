package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Alexander
 */
@Entity
@Table(name = "sms_message_status")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class SmsMessageStatus extends BaseModel{

    @Column(name = "fast_sms_messageId")
    private int fastSmsMessageId;

    @Column(name = "sms_message_id")
    private int smsMessageId;

    private String status;

    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    public void copyTo(SmsMessageStatus copy){
        super.copyTo(copy);
        copy.smsMessageId = smsMessageId;
        copy.fastSmsMessageId = fastSmsMessageId;
        copy.status = status;
        copy.lastUpdateDate = lastUpdateDate;
    }

    public int getFastSmsMessageId() {
        return fastSmsMessageId;
    }

    public void setFastSmsMessageId(int fastSmsMessageId) {
        this.fastSmsMessageId = fastSmsMessageId;
    }

    public int getSmsMessageId() {
        return smsMessageId;
    }

    public void setSmsMessageId(int smsMessageId) {
        this.smsMessageId = smsMessageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


}
