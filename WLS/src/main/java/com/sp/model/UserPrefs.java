package com.sp.model;

import com.sp.util.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "user_prefs")
public class UserPrefs extends BaseModel {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "distance_metric")
    private String distanceMetric = "Miles";

    @Column(name = "last_unit_view_id")
    private Integer lastUnitViewId;

    @Column(name = "naming_preferences")
    String namingPreferences;

    @Column(name = "last_phone_view_id")
    private Integer lastPhoneViewId;

    @Column(name = "late_start_rep_journey_end")
    private boolean lateStartRepJourneyEnd;

    @Column(name = "is_or_short_journeys_operation")
    private boolean orShortJourneysOperation;

    @Column(name = "last_view_name")
    private String lastViewName = Constants.OBJECT_TYPE_NAME_SELECTED.vehicle.toString();

    @Column(name = "max_sms_length")
    private int maxSmsLength;

    @Column(name = "last_handheld_view_id")
    private Integer lastHandheldViewId;

    @Column(name = "short_journeys_duration")
    private int shortJourneysDuration;

    @Column(name = "short_journeys_distance")
    private int shortJourneysDistance;

    @Column(name = "is_left_menu_preselected")
    private boolean isLeftMenuPreselected;

//    @Column(name = "is_account_logo_switched")
//    private boolean isAccountLogoSwitched;

    @Column(name = "vehicle_icon_size")
    private String vehicleIconSize = "100";

    @Column(name = "report_prefs")
    private String reportPrefs;

    @Column(name = "info_panel_left")
    private boolean infoPanelLeft;

    @Column(name = "account_logo_switch_delay")
    private int accountLogoSwitchDelay;

    @Column(name = "switch_logo_type")
    private String switchLogoType = "switch";

    @Column(name = "is_hide_fleet_id_on_icon")
    private boolean hideFleetIdOnIcon;

    @Column(name = "is_disable_icons_glow")
    private boolean disableIconsGlow;

    @Column(name = "glow_color")
    private String glowColor = "grey";

    @Column(name = "mapping_view_type")
    private String mappingViewType = "DUAL";

    @Column(name = "replace_driver_name_with_reg_num")
    private boolean replaceDriverNameWithRegNum;

    @Column(name = "selection_menu_expanded_on_logon")
    private boolean selectionMenuExpandedOnLogon;

    @Column(name = "driving_time_idling_seconds")
    private Integer drivingTimeIdlingSeconds;


    public UserPrefs() {
        maxSmsLength = 160;
    }


    public void copyTo(UserPrefs copy) {
        super.copyTo(copy);
        copy.userId = userId;
        copy.distanceMetric = distanceMetric;
        copy.lastUnitViewId = lastUnitViewId;
        copy.lastPhoneViewId = lastPhoneViewId;
        copy.lateStartRepJourneyEnd = lateStartRepJourneyEnd;
        copy.lastViewName = lastViewName;
        copy.lastHandheldViewId = lastHandheldViewId;
        copy.isLeftMenuPreselected = isLeftMenuPreselected;
        copy.vehicleIconSize = vehicleIconSize;
        copy.reportPrefs = reportPrefs;
        copy.namingPreferences = namingPreferences;
//        copy.isAccountLogoSwitched = isAccountLogoSwitched;
        copy.switchLogoType = switchLogoType;
        copy.infoPanelLeft = infoPanelLeft;
        copy.shortJourneysDistance = shortJourneysDistance;
        copy.shortJourneysDuration = shortJourneysDuration;
        copy.accountLogoSwitchDelay = accountLogoSwitchDelay;
        copy.hideFleetIdOnIcon = hideFleetIdOnIcon;
        copy.disableIconsGlow = disableIconsGlow;
        copy.glowColor = glowColor;
        copy.orShortJourneysOperation = orShortJourneysOperation;
        copy.mappingViewType = mappingViewType;
        copy.replaceDriverNameWithRegNum = replaceDriverNameWithRegNum;
        copy.selectionMenuExpandedOnLogon = selectionMenuExpandedOnLogon;
        copy.drivingTimeIdlingSeconds = drivingTimeIdlingSeconds;
        copy.maxSmsLength = maxSmsLength;
    }

     public Integer getLastHandheldViewId() {
        return lastHandheldViewId;
    }

    public void setLastHandheldViewId(Integer lastHandheldViewId) {
        this.lastHandheldViewId = lastHandheldViewId;
    }

    public String getLastViewName() {
        return lastViewName;
    }

    public void setLastViewName(String lastViewName) {
        this.lastViewName = lastViewName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDistanceMetric() {
        return distanceMetric;
    }

    public void setDistanceMetric(String distanceMetric) {
        this.distanceMetric = distanceMetric;
    }

    public float getDistanceMetricFactor() {
        return "KM".equals(distanceMetric) ? 1 : Constants.KM_TO_MILES;
    }

    // for flex serialization
    public void setDistanceMetricFactor(float distanceMetricFactor) {

    }

    public String toOptionText() {
        return null;
    }

    public Integer getLastUnitViewId() {
        return lastUnitViewId;
    }

    public void setLastUnitViewId(Integer lastUnitViewId) {
        this.lastUnitViewId = lastUnitViewId;
    }

    public boolean isLateStartRepJourneyEnd() {
        return lateStartRepJourneyEnd;
    }

    public void setLateStartRepJourneyEnd(boolean lateStartRepJourneyEnd) {
        this.lateStartRepJourneyEnd = lateStartRepJourneyEnd;
    }

    public Integer getLastPhoneViewId() {
        return lastPhoneViewId;
    }

    public void setLastPhoneViewId(Integer lastPhoneViewId) {
        this.lastPhoneViewId = lastPhoneViewId;
    }

    public boolean isLeftMenuPreselected() {
        return isLeftMenuPreselected;
    }

    public void setLeftMenuPreselected(boolean leftMenuPreselected) {
        isLeftMenuPreselected = leftMenuPreselected;
    }


    public String getVehicleIconSize() {
        return vehicleIconSize;
    }

    public void setVehicleIconSize(String vehicleIconSize) {
        this.vehicleIconSize = vehicleIconSize;
    }

//    public boolean isAccountLogoSwitched() {
//        return isAccountLogoSwitched;
//    }
//
//    public void setAccountLogoSwitched(boolean accountLogoSwitched) {
//        isAccountLogoSwitched = accountLogoSwitched;
//    }


    public String getReportPrefs() {
        return reportPrefs;
    }

    public void setReportPrefs(String reportPrefs) {
        this.reportPrefs = reportPrefs;
    }


    public boolean isInfoPanelLeft() {
        return infoPanelLeft;
    }

    public void setInfoPanelLeft(boolean infoPanelLeft) {
        this.infoPanelLeft = infoPanelLeft;
    }

    public int getAccountLogoSwitchDelay() {
        return accountLogoSwitchDelay;
    }

    public void setAccountLogoSwitchDelay(int accountLogoSwitchDelay) {
        this.accountLogoSwitchDelay = accountLogoSwitchDelay;
    }

    public String getSwitchLogoType() {
        return switchLogoType;
    }

    public void setSwitchLogoType(String switchLogoType) {
        this.switchLogoType = switchLogoType;
    }

    public boolean isHideFleetIdOnIcon() {
        return hideFleetIdOnIcon;
    }

    public void setHideFleetIdOnIcon(boolean hideFleetIdOnIcon) {
        this.hideFleetIdOnIcon = hideFleetIdOnIcon;
    }

    public String getGlowColor() {
        return glowColor;
    }

    public void setGlowColor(String glowColor) {
        this.glowColor = glowColor;
    }

    public boolean isDisableIconsGlow() {
        return disableIconsGlow;
    }

    public void setDisableIconsGlow(boolean disableIconsGlow) {
        this.disableIconsGlow = disableIconsGlow;
    }

    public String getMappingViewType() {
        return mappingViewType;
    }

    public void setMappingViewType(String mappingViewType) {
        this.mappingViewType = mappingViewType;
    }

    public int getShortJourneysDuration() {
        return shortJourneysDuration;
    }

    public void setShortJourneysDuration(int shortJourneysDuration) {
        this.shortJourneysDuration = shortJourneysDuration;
    }

    public int getShortJourneysDistance() {
        return shortJourneysDistance;
    }

    public void setShortJourneysDistance(int shortJourneysDistance) {
        this.shortJourneysDistance = shortJourneysDistance;
    }

    public boolean isOrShortJourneysOperation() {
        return orShortJourneysOperation;
    }

    public void setOrShortJourneysOperation(boolean orShortJourneysOperation) {
        this.orShortJourneysOperation = orShortJourneysOperation;
    }


    public boolean isReplaceDriverNameWithRegNum() {
        return replaceDriverNameWithRegNum;
    }

    public void setReplaceDriverNameWithRegNum(boolean replaceDriverNameWithRegNum) {
        this.replaceDriverNameWithRegNum = replaceDriverNameWithRegNum;
    }

    public boolean isSelectionMenuExpandedOnLogon() {
        return selectionMenuExpandedOnLogon;
    }

    public void setSelectionMenuExpandedOnLogon(boolean selectionMenuExpandedOnLogon) {
        this.selectionMenuExpandedOnLogon = selectionMenuExpandedOnLogon;
    }

    public String getNamingPreferences() {
        return namingPreferences;
    }

    public void setNamingPreferences(String namingPreferences) {
        this.namingPreferences = namingPreferences;
    }

    public Integer getDrivingTimeIdlingSeconds() {
        return drivingTimeIdlingSeconds;
    }

    public void setDrivingTimeIdlingSeconds(Integer drivingTimeIdlingSeconds) {
        this.drivingTimeIdlingSeconds = drivingTimeIdlingSeconds;
    }

    public int getMaxSmsLength() {
        return maxSmsLength;
    }

    public void setMaxSmsLength(int maxSmsLength) {
        this.maxSmsLength = maxSmsLength;
    }

}
