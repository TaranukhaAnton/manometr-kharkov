package com.sp.dto.flex;


import java.util.List;


public class AlertFlx {

    private int id;

    private String descr;
    private boolean deleted;

    private boolean sendToSelf;

    private List<Integer> alertVehicles;

    private List<Integer> alertPhones;

    public List<AlertRecipientFlx> alertRecipients;

    public boolean isSendToSelf() {
        return sendToSelf;
    }

    public void setSendToSelf(boolean sendToSelf) {
        this.sendToSelf = sendToSelf;
    }

    public List<Integer> getAlertVehicles() {
        return alertVehicles;
    }

    public void setAlertVehicles(List<Integer> alertVehicles) {
        this.alertVehicles = alertVehicles;
    }

    public List<Integer> getAlertPhones() {
        return alertPhones;
    }

    public void setAlertPhones(List<Integer> alertPhones) {
        this.alertPhones = alertPhones;
    }

    public List<AlertRecipientFlx> getAlertRecipients() {
        return alertRecipients;
    }

    public void setAlertRecipients(List<AlertRecipientFlx> alertRecipients) {
        this.alertRecipients = alertRecipients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    
}
