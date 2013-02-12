package com.sp.dto.flex;

/**
 
 */
public class MapAlertPoiFlx {

    private int alertId;
    private int poiId;
    private boolean entry;
    private boolean exit;

    public int getAlertId() {
        return alertId;
    }

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    public int getPoiId() {
        return poiId;
    }

    public void setPoiId(int poiId) {
        this.poiId = poiId;
    }

    public boolean isEntry() {
        return entry;
    }

    public void setEntry(boolean entry) {
        this.entry = entry;
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
