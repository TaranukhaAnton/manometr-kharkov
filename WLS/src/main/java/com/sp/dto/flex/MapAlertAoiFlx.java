package com.sp.dto.flex;

public class MapAlertAoiFlx {

    private int alertId;
    private int aoiId;
    private boolean entry;
    private boolean exit;

    public int getAlertId() {
        return alertId;
    }

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    public int getAoiId() {
        return aoiId;
    }

    public void setAoiId(int poiId) {
        this.aoiId = poiId;
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
