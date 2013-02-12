package com.sp.model;

import com.sp.util.Util;
import org.apache.commons.lang.ArrayUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * User: andrew
 * Date: 07.09.2010
 */
@Entity
@Table(name = "raw_incoming_log")
public class RawIncomingLogRecord extends BaseModel {
    @Column(name = "box_type")
    private String boxType;
    private byte[] data;
    private String imei;

    @Column(insertable = false, updatable = false)
    private Date timestamp;

    public String getDataStr() {
        return Util.getHexString(data); 
    }

    public void copyTo(RawIncomingLogRecord copy) {
        super.copyTo(copy);
        copy.boxType = boxType;
        copy.data = data;
        copy.imei = imei;
        copy.timestamp = timestamp;
    }

    public String toString() {
        return "RawIncomingLogRecord{" +
                "id=" + getId() +
                ", boxType=" + getBoxType() +
                ", data=" + ArrayUtils.toString(getData()) +
                ", imei=" + getImei() +
                ", timestamp=" + getTimestamp();
    }

    public String getBoxType() {
        return boxType;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
