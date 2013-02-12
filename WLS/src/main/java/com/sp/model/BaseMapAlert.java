package com.sp.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * User: andrew
 * Date: 02.04.2010
 */
@MappedSuperclass
public class BaseMapAlert extends BaseModel {
    @Column(name = "alert_id")
    private int alertId;
    @Column(name = "entry")
    private boolean entry;
    @Column(name = "is_exit")
    private boolean exit;

    public void copyTo(BaseMapAlert copy) {
        super.copyTo(copy);
        copy.alertId = alertId;
        copy.entry = entry;
        copy.exit = exit;
    }

    public boolean getEntry() {
        return entry;
    }

    public void setEntry(boolean entry) {
        this.entry = entry;
    }

    public boolean getExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public int getAlertId() {
        return alertId;
    }

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }
}
