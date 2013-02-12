package com.sp.dto;

import com.sp.util.Util;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by Alexander
 */
public class FastSmsInboundDto {
    private final static Logger LOGGER = Logger.getLogger(FastSmsInboundDto.class);
    //Status,Date,From,To,Message,
    private String status;
    private Date deliveredDate;
    private String deliveredDateStr;
    private String fromNumber;
    private String toNumber;
    private String body;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = Util.parseDateByPattern(deliveredDate, "HH:mm dd/MM/yyyy");
        this.deliveredDateStr = deliveredDate;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDeliveredDateStr() {
        return deliveredDateStr;
    }

}
