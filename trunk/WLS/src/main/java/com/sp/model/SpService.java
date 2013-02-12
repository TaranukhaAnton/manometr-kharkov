package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sp_services")
public class SpService extends EnumModel {

    private String notes;
    

    @Column(name = "os_process_id")
    private String osProcessId;

    @Transient
    private String status;

    public void copyTo(SpService copy) {
        super.copyTo(copy);
        copy.notes = notes;
        copy.osProcessId = osProcessId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOsProcessId() {
        return osProcessId;
    }

    public void setOsProcessId(String osProcessId) {
        this.osProcessId = osProcessId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isRunning() {
        return status == null ? null : status.trim().equalsIgnoreCase("RUNNING");
    }
}
