package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "alert")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class Alert extends EnumModel {

    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "modified_date", insertable = false)
    private Date modifiedDate;

    @Column(name = "is_deleted")
    private boolean deleted;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", updatable = false)
    private SecurityUser securityUser;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "modifier_id", insertable = false)
    private SecurityUser modifier;

    @Column(name = "send_to_self")
    private boolean sendToSelf;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Vehicle.class,
            fetch = FetchType.EAGER
    )
    @JoinTable(name = "map_alert_vehicle",
            joinColumns = @JoinColumn(name = "alert_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    private List<Vehicle> alertVehicles;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Phone.class,
            fetch = FetchType.LAZY
    )
    @JoinTable(name = "map_alert_phone",
            joinColumns = @JoinColumn(name = "alert_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    private List<Phone> alertPhones;


    @OneToMany(mappedBy = "alert", fetch = FetchType.LAZY)
    public List<AlertRecipient> alertRecipients;

    @Transient
    private boolean deleteAllowed;


    public List<Phone> getAlertPhones() {
        return alertPhones;
    }

    public void setAlertPhones(List<Phone> alertPhones) {
        this.alertPhones = alertPhones;
    }

    public boolean isSendToSelf() {
        return sendToSelf;
    }

    public void setSendToSelf(boolean sendToSelf) {
        this.sendToSelf = sendToSelf;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<Vehicle> getAlertVehicles() {
        return alertVehicles;
    }

    public void setAlertVehicles(List<Vehicle> alertVehicles) {
        this.alertVehicles = alertVehicles;
    }

    public List<AlertRecipient> getAlertRecipients() {
        return alertRecipients;
    }

    public void setAlertRecipients(List<AlertRecipient> alertRecipients) {
        this.alertRecipients = alertRecipients;
    }

    public void copyTo(Alert copy) {
        super.copyTo(copy);
        copy.createdDate = createdDate;
        copy.modifiedDate = modifiedDate;
        copy.deleted = deleted;
        copy.sendToSelf = sendToSelf;
        copy.securityUser = securityUser;
        copy.modifier = modifier;
        copy.alertVehicles = alertVehicles;
        copy.deleteAllowed = deleteAllowed;
//      Hibernate does not allow using of two Collections with FetchType.EAGER, so they have FetchType.LAZY
        if (alertPhones != null) {
            List<Phone> phones = new ArrayList<Phone>(alertPhones);
            copy.alertPhones = phones;
        }
        if (alertRecipients != null) {
            List<AlertRecipient> recipients = new ArrayList<AlertRecipient>(alertRecipients);
            copy.alertRecipients = recipients;
        }
    }

    public SecurityUser getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public SecurityUser getModifier() {
        return modifier;
    }

    public void setModifier(SecurityUser modifier) {
        this.modifier = modifier;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isDeleteAllowed() {
        return deleteAllowed;
    }

    public void setDeleteAllowed(boolean deleteAllowed) {
        this.deleteAllowed = deleteAllowed;
    }


}
