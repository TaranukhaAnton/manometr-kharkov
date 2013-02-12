package com.sp.model;

import com.sp.util.Util;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 17.06.2009
 * Time: 16:52:51
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONE)
@Table(name = "schedule_mail_alert")
public class ScheduleMailAlert extends EnumModel {

    @Column(name = "is_entered")
    private boolean isEntered;

    @Column(name = "is_sent")
    private boolean sent;

    @Column(name = "poi_aoi_name")
    private String poiAoiName;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "rec_date")
    private Date recDate;

    @Column(name = "poi_id")
    private Integer poiId;

    @Column(name = "aoi_id")
    private Integer aoiId;

    @Column(name = "item_type")
    private String itemType;

    @ManyToOne
    @JoinColumn(name = "alert_id")
    private Alert alert;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private LightVehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "phone_id", nullable = true)
    private Phone phone;

    @Column(name = "error_msg")
    private String errorMsg;

    @Column(name = "incoming_log_id")
    private Integer incomingLogId;

    @Transient
    private String regularDriver;

    @Transient
    private String entryExit;

    @Transient
    private String isGeofence;

    public void copyTo(ScheduleMailAlert copy) {
        super.copyTo(copy);
        copy.isEntered = isEntered();
        copy.sent = sent;
        copy.poiAoiName = poiAoiName;
        copy.createdDate = createdDate;
        copy.recDate = recDate;
        copy.itemType = itemType;
        alert.setAlertPhones(null);
        copy.alert = alert;
        copy.vehicle = vehicle;
        copy.phone = phone;
        copy.errorMsg = errorMsg;
        copy.incomingLogId = incomingLogId;
        copy.poiId = poiId;
        copy.aoiId = aoiId;
        copy.regularDriver = vehicle.getRegDriverFullName();
        copy.entryExit = entryOrExit();
        copy.setGeofence(getGeofence());
    }


    private String entryOrExit() {
        if (isEntered) {
            return "Entry";
        } else {
            return "Exit";
        }
    }

    public boolean isEntered() {
        return isEntered;
    }

    public void setEntered(boolean entered) {
        isEntered = entered;
    }

    public String getPoiAoiName() {
        return poiAoiName;
    }

    public void setPoiAoiName(String poiAoiName) {
        this.poiAoiName = poiAoiName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public LightVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(LightVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public String getDate() {
        if (recDate != null) {
            return Util.getDaysFromDate(recDate);
        }
        return null;
    }

    // for flex serialization
    public void setDate(String date) {
    }

    public String getTime() {
        if (recDate != null) {
            return new SimpleDateFormat("HH:mm").format(recDate);
        }
        return null;
    }

    // for flex serialization
    public void setTime(String time) {
    }

    public String getRegularDriver() {
        return regularDriver;
    }

    public void setRegularDriver(String regularDriver) {
        this.regularDriver = regularDriver;
    }

    public String getEntryExit() {
        return entryExit;
    }

    public void setEntryExit(String entryExit) {
        this.entryExit = entryExit;
    }

    public String getRecipientList() {
        StringBuilder result = new StringBuilder();
        result.append(" " + alert.getSecurityUser().getFirstName() + " " + alert.getSecurityUser().getLastName() + " (Email)");
        for (AlertRecipient recipient : alert.getAlertRecipients()) {
            result.append(", " + recipient.getFirstName() + " " + recipient.getLastName() + " (Email)");
        }
        return result.toString();
    }

    //for flex serialization
    public void setRecipientList(String recipientList) {
    }

    public String getGeofence() {
        if (alert.getAlertRecipients().size() == 0) {
            isGeofence = "No";
        } else {
            isGeofence = "Yes";
        }
        return isGeofence;
    }

    public void setGeofence(String geofence) {
        isGeofence = geofence;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public Date getRecDate() {
        return recDate;
    }

    public void setRecDate(Date recDate) {
        this.recDate = recDate;
    }

    public Integer getIncomingLogId() {
        return incomingLogId;
    }

    public void setIncomingLogId(Integer incomingLogId) {
        this.incomingLogId = incomingLogId;
    }

    public Integer getPoiId() {
        return poiId;
    }

    public void setPoiId(Integer poiId) {
        this.poiId = poiId;
    }

    public Integer getAoiId() {
        return aoiId;
    }

    public void setAoiId(Integer aoiId) {
        this.aoiId = aoiId;
    }

    @Override
    public String toString() {
        return "ScheduleMailAlert{" +
                "isEntered=" + isEntered +
                ", sent=" + sent +
                ", poiAoiName='" + poiAoiName + '\'' +
                ", createdDate=" + createdDate +
                ", recDate=" + recDate +
                ", itemType='" + itemType + '\'' +
                ", alert=" + alert +
                ", vehicle=" + vehicle +
                ", phone=" + phone +
                ", errorMsg='" + errorMsg + '\'' +
                ", incomingLogId=" + incomingLogId +
                ", regularDriver='" + regularDriver + '\'' +
                ", entryExit='" + entryExit + '\'' +
                ", isGeofence='" + isGeofence + '\'' +
                '}';
    }
}
