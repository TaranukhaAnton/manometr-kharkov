package org.krams.tutorial.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table (name = "account")
public class BaseAccount extends EnumModel {
    @Column(name = "short_journeys_default_duration")
    private int shortJourneysDefaultDuration;

    @Column(name = "short_journeys_default_distance")
    private int shortJourneysDefaultDistance;

    @Column(name = "is_deleted")
    private boolean deleted;

    @Column(name = "disable_sticky_roads")
    private boolean disableStickyRoads;

    @Column(name = "is_satellite_drift_overwrite")
    private boolean satelliteDriftOverwrite;

    @Column(name = "allowed_ip_list")
    private String allowedIPs;

    public boolean isDeleted() {
        return deleted;
    }

    public void copyTo(BaseAccount copy) {
        super.copyTo(copy);
        copy.deleted = deleted;
        copy.shortJourneysDefaultDistance = shortJourneysDefaultDistance;
        copy.shortJourneysDefaultDuration = shortJourneysDefaultDuration;
        copy.disableStickyRoads = disableStickyRoads;
        copy.satelliteDriftOverwrite = satelliteDriftOverwrite;
        copy.allowedIPs = allowedIPs;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getShortJourneysDefaultDistance() {
        return shortJourneysDefaultDistance;
    }

    public void setShortJourneysDefaultDistance(int shortJourneysDefaultDistance) {
        this.shortJourneysDefaultDistance = shortJourneysDefaultDistance;
    }

    public int getShortJourneysDefaultDuration() {
        return shortJourneysDefaultDuration;
    }

    public void setShortJourneysDefaultDuration(int shortJourneysDefaultDuration) {
        this.shortJourneysDefaultDuration = shortJourneysDefaultDuration;
    }

    public boolean isDisableStickyRoads() {
        return disableStickyRoads;
    }

    public void setDisableStickyRoads(boolean disableStickyRoads) {
        this.disableStickyRoads = disableStickyRoads;
    }

    public boolean isSatelliteDriftOverwrite() {
        return satelliteDriftOverwrite;
    }

    public void setSatelliteDriftOverwrite(boolean satelliteDriftOverwrite) {
        this.satelliteDriftOverwrite = satelliteDriftOverwrite;
    }

    public String getAllowedIPs() {
        return allowedIPs;
    }

    public void setAllowedIPs(String restrictIPs) {
        this.allowedIPs = restrictIPs;
    }
}
