package org.krams.tutorial.domain;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "reseller")
public class Reseller extends BaseReseller {

    private static final long serialVersionUID = 8114891998063113292L;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "sms_alert_numbers")
    private String smsAlertNumbers;

    @Column(name = "fast_sms_username")
    private String fastSmsUername;

    @Column(name = "is_virtual_pagenation_allowed")
    private boolean virtualPagenationAllowed;

    @Column(name = "is_handheld_clustering_allowed")
    private boolean handheldClusteringAllowed;

    @Column(name = "is_seven_digital_postcode_allowed")
    private boolean sevenDigitalPostcodeAllowed;

    @Column(name = "is_geo_fence_confidence_msgs_count_enter_allowed")
    private boolean geoFenceConfidenceMsgsCountEnterAllowed;

    @Column(name = "is_geo_fence_confidence_msgs_count_exit_allowed")
    private boolean geoFenceConfidenceMsgsCountExitAllowed;

    @Column(name = "is_geo_fence_confidence_msgs_override_ign_off_allowed")
    private boolean geoFenceConfidenceMsgsOverrideIgnOffAllowed;

    @Column(name = "geo_fence_confidence_msgs_count_enter")
    private int geoFenceConfidenceMsgsCountEnter;

    @Column(name = "geo_fence_confidence_msgs_count_exit")
    private int geoFenceConfidenceMsgsCountExit;

    @Column(name = "is_refresh_sm_rate_from_10_allowed")
    private boolean refreshSmRateFrom10Allowed;

    @Column(name = "is_preferences_under_admin_section")
    private boolean preferencesUnderAdminSection;

    @Column(name = "is_retain_user_selection_poi_allowed")
    private boolean retainUserSelectionAllowedPoi;

    @Column(name = "is_retain_user_selection_aoi_allowed")
    private boolean retainUserSelectionAllowedAoi;

    @Column(name = "is_show_stop_time_alternate_journey_allowed")
    private boolean showStopTimeAlternateJourneyAllowed;

    @Column(name = "is_zero_todays_cost_midnight")
    private boolean zeroTodaysCostAtMidnight;

    @Column(name = "is_back_to_the_future_modified")
    private boolean backToTheFutureModifiedAllowed;

    @Column(name = "is_last_gasp_allowed")
    private boolean lastGaspAllowed;

    @Column(name = "is_action_menu_ordering_allowed")
    private boolean actionMenuOrderingAllowed;

    @Column(name = "is_refresh_whole_browser_allowed")
    private boolean refreshWholeBrowserAllowed;

    @Column(name = "is_location_based_data_regardless_confidence")
    private boolean locationBasedDataRegardlessConfidence;

    @Column(name = "is_hide_warning_asterisks")
    private boolean hideWarningAsterisks;

    @Column(name = "is_tfl_disruptions_allowed")
    private boolean tflDisruptionsAllowed;

    @Column(name = "is_tfl_camera_allowed")
    private boolean tflCameraAllowed;

    @Column(name = "is_countdown_sm_refresh_allowed")
    private boolean countdownSmRefreshAllowed;

    @Column(name = "action_menu_ordering")
    private String actionMenuOrdering;

    @Column(name = "is_report_ordering_allowed")
    private boolean reportOrderingAllowed;

    @Column(name = "is_back_to_the_future_allowed")
    private boolean backToTheFutureAllowed;

    @Column(name = "is_poi_area_coloured")
    private boolean poiAreaColoured;

    @Column(name = "is_aoi_area_coloured")
    private boolean aoiAreaColoured;

    @Column(name = "is_left_menu_performance_boosted")
    private boolean leftMenuPerformanceBoosted;

    @Column(name = "is_help_menu_allowed")
    private boolean helpMenuAllowed;

    @Column(name = "user_manual_link_under_help_menu")
    private String userManualLinkUnderHelpMenu;

    @Column(name = "fast_sms_password")
    private String fastSmsPassword;

    @Column(name = "fast_sms_source_address")
    private String fastSmsSourceAddress;

    @Column(name = "contact_us_phone")
    private String contactUsPhone;

    @Column(name = "contact_us_fax")
    private String contactUsFax;

    @Column(name = "contact_us_email")
    private String contactUsEmail;

    @Column(name = "contact_us_web")
    private String contactUsWeb;

    @Column(name = "geoserver_rg_excepted_accounts")
    private String geoserverRGexceptedAccounts;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "meaningful_address_fix_allowed")
    private boolean meaningfulAddressFixAllowed;

    @Column(name = "is_geoserver_rg_title_case")
    private boolean geoserverRgTitleCase;

    private String notes;

    @Column(name = "primary_email")
    private String primaryEmail;

    @Column(name = "secondary_email")
    private String secondaryEmail;

    @Column(name = "suggestion_email")
    private String suggestionEmail;

    @Column(name = "billing_contact_name")
    private String billingContactName;

    @Column(name = "billing_contact_email")
    private String billingContactEmail;

    @Column(name = "billing_contact_phone")
    private String billingContactPhone;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "address_line3")
    private String addressLine3;

    @Column(name = "address_line4")
    private String addressLine4;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "country")
    private String country;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "map_source_id")
    private MapSource mapSource;

    @Column(name = "map_key")
    private String mapKey;

    @Column(name = "map_password")
    private String mapPassword;

    @Column(name = "url_code_prefix")
    private String urlCodePrefix;

    @Column(name = "rebranding_folder")
    private String rebrandingFolder;

    @Column(name = "is_deleted")
    private boolean deleted;

    @Column(name = "is_left_panel_display_din_allowed_fm2x")
    private boolean leftPanelDisplayDinAllowedFm2x;

    @Column(name = "is_left_panel_display_din_allowed_fm4x")
    private boolean leftPanelDisplayDinAllowedFm4x;

    @Column(name = "is_postcode_search_allowed")
    private boolean postcodeSearchAllowed;

    @Column(name = "is_selection_menu_group_name_visible")
    private boolean selectionMenuGroupNameVisible;

    @Column(name = "is_zoom_on_vehicle_allowed")
    private boolean zoomOnVehicleAllowed;

    @Column(name = "is_show_t6_t8_message_type_allowed")
    private boolean showT6T8MessageTypeAllowed;

    @Column(name = "is_retain_journey_end_allowed")
    private boolean retainJourneyEndAllowed;

    @Column(name = "is_show_hide_pois_feature_allowed")
    private boolean showHidePoisFeatureAllowed;

    @Column(name = "is_show_hide_aois_feature_allowed")
    private boolean showHideAoisFeatureAllowed;

    @Column(name = "is_flex_menu")
    private boolean flexMenu;

    @Column(name = "is_flex_mapping")
    private boolean flexMapping;

    @Column(name = "is_flex_login")
    private boolean flexLogin;

    @Column(name = "is_flex_forgotten_password")
    private boolean flexForgottenPassword;

    @Column(name = "is_flex_scheduled_jobs")
    private boolean flexScheduledJobs;

    @Column(name = "is_not_preselected_scheduled_recurent_pattern")
    private boolean notPreselectedScheduledRecurentPattern;

    @Column(name = "is_dashboard_allowed")
    private boolean dashboardAllowed;

    @Column(name = "is_hover_over_vehicle_allowed")
    private boolean hoverOverVehicleAllowed;

    @Column(name = "is_hover_over_vehicle_allowed_proximity")
    private boolean hoverOverVehicleAllowedProximity;

    @Column(name = "is_hover_over_vehicle_allowed_activity")
    private boolean hoverOverVehicleAllowedActivity;

    @Column(name = "is_flex_geofence")
    private boolean flexGeofence;

    @Column(name = "is_hide_map_tools_allowed")
    private boolean hideMapToolsAllowed;

    @Column(name = "is_flex_suggestion")
    private boolean flexSuggestion;

    @Column(name = "is_map_view_plus_visible")
    private boolean mapViewPlusVisible;

    @Column(name = "disable_timed_logout")
    private boolean disableTimedLogout;

    @Column(name = "is_flex_manage_poi")
    private boolean flexManagePoi;

    @Column(name = "is_flex_manage_aoi")
    private boolean flexManageAoi;

    @Column(name = "is_flex_preferences")
    private boolean flexPreferences;

    @Column(name = "is_flex_proximity")
    private boolean flexProximity;

    @Column(name = "is_flex_snail_trail_enhanced")
    private boolean flexSnailTrailEnhanced;

    @Column(name = "is_snail_trail_play_allowed")
    private boolean snailTrailPlayAllowed;

    @Column(name = "is_snail_trail_side_by_side")
    private boolean snailTrailSideBySide;

    @Column(name = "is_pause_map_allowed")
    private boolean pauseMapAllowed;

    @Column(name = "is_todays_events_allowed")
    private boolean todaysEventsAllowed;

    @Column(name = "account_logo_switch_default_delay")
    private int accountLogoSwitchDefaultDelay;

    @Column(name = "max_default_zoom_level")
    private int maxDefaultZoomLevel;

    @Column(name = "is_snail_trail_fullscreen")
    private boolean snailTrailFullscreen;

    @Column(name = "is_snail_trail_display_all_pins") 
    private boolean snailTrailDisplayAllPins;


    @Column(name = "ignition_status_icons_version")
    private Integer ignitionStatusIconsVersion;

    @Column(name = "is_fleet_id_over_icons")
    private boolean fleetIdOverIcons;

    @Column(name = "is_glow_icons")
    private boolean glowIcons;

    @Column(name = "is_calculated_orientation_allowed")
    private boolean calculatedOrientationAllowed;

    @Column(name = "timestamp", insertable = false, updatable = false)
    private Date timestamp;

    @Column(name = "is_activity_view_visible")
    private boolean activityViewVisible;

    @Column(name = "incoming_log_processor_service_id")
    private String incomingLogProcessorServiceId;

    @Column(name = "is_hide_short_journeys_allowed")
    private boolean hideShortJourneysAllowed;

    @Column(name = "reduce_pin_size_on_zoom_out")
    private boolean reducePinSizeOnZoomOut;

    @Column(name = "is_proximity_allowed")
    private boolean proximityAllowed;

    @Column(name = "is_preferences_allowed")
    private boolean preferencesAllowed;

    @Column(name = "replace_driver_name_with_reg_num")
    private boolean replaceDriverNameWithRegNum;

    @Column(name = "allow_show_expanded_selection_menu")
    private boolean allowShowExpandedSelectionMenu;

    @Column(name = "use_geoserver_rg")
    private boolean useGeoserverRg;

    @Column(name = "language_id")
    @Enumerated(EnumType.STRING)
    private LanguageId languageId;

    @Column(name = "show_user_manual")
    private boolean showUserManual;

    @Column(name = "user_manual_link")
    private String userManualLink = "";

    @Column(name = "is_show_support_email")
    private boolean showSupportEmail;

    @Column(name = "support_email")
    private String supportEmail = "";

    @Column(name = "is_details_view_visible")
    private boolean detailsViewVisible;

    @Column(name = "sticky_roads")
    private boolean stickyRoads;

    @Column(name = "hide_red_pin")
    private boolean hideRedPin;

    @Column(name = "multi_color_pin_poi")
    private boolean multiColorPinsPoi;

    @Column(name = "multi_color_pin_aoi")
    private boolean multiColorPinsAoi;

    @Column(name = "freeze_sm_menu")
    private boolean freezeSmMenu;

    @Column(name = "is_prevailing_speed_limits_allowed")
    private boolean prevailingSpeedLimitsAllowed;

    @Column(name = "sticky_roads_disable_on_ign_off")
    private boolean stickyRoadsDisableOnIgnOff;

    @Column(name = "sticky_roads_disable_by_account")
    private boolean stickyRoadsDisableByAccount;

    @Column(name = "is_poi_aoi_list_order_allowed")
    private boolean poiAoiListOrderAllowed;

    @Column(name = "is_always_display_driver_name")
    private boolean alwaysDisplayDriverName;

    @Column(name = "is_use_dots_on_map")
    private boolean useDotsOnMap;

    @Column(name = "is_show_veh_tooltip_allowed")
    private boolean showVehTooltipAllowed;

    @Column(name = "password_reminder_host")
    private String passwordReminderHost;

    @Column(name = "password_reminder_subject")
    private String passwordReminderSubject;

    @Column(name = "password_reminder_from_address")
    private String passwordReminderFromAddress;

    @Column(name = "password_reminder_smtp_username")
    private String passwordReminderSmtpUsername;

    @Column(name = "password_reminder_smtp_password")
    private String passwordReminderSmtpPassword;

    @Column(name = "password_reminder_smtp_port")
    private Integer passwordReminderSmtpPort;

    @Column(name = "scheduled_report_host")
    private String reportHost;

    @Column(name = "scheduled_report_subject")
    private String reportSubject;

    @Column(name = "scheduled_report_from_address")
    private String reportFromAddress;

    @Column(name = "scheduled_report_smtp_username")
    private String reportSmtpUsername;

    @Column(name = "scheduled_report_smtp_password")
    private String reportSmtpPassword;

    @Column(name = "scheduled_report_smtp_port")
    private Integer reportSmtpPort;

    @Column(name = "geo_fence_alert_host")
    private String geoFenceAlertHost;

    @Column(name = "geo_fence_alert_subject")
    private String geoFenceAlertSubject;

    @Column(name = "geo_fence_alert_from_address")
    private String geoFenceAlertFromAddress;

    @Column(name = "geo_fence_alert_smtp_username")
    private String geoFenceAlertSmtpUsername;

    @Column(name = "geo_fence_alert_smtp_password")
    private String geoFenceAlertSmtpPassword;

    @Column(name = "geo_fence_alert_smtp_port")
    private Integer geoFenceAlertSmtpPort;

    @Column(name = "suggestion_host")
    private String suggestionHost;

    @Column(name = "suggestion_subject")
    private String suggestionSubject;

    @Column(name = "suggestion_from_address")
    private String suggestionFromAddress;

    @Column(name = "suggestion_smtp_username")
    private String suggestionSmtpUsername;

    @Column(name = "suggestion_smtp_password")
    private String suggestionSmtpPassword;

    @Column(name = "suggestion_smtp_port")
    private Integer suggestionSmtpPort;

    @Column(name = "prefix_sm_location_with_nr")
    private boolean prefixSmLocationWithNr;

    @Column(name = "draw_arrow_from_veh_icon_to_road")
    private boolean drawArrowFromVehIconToRoad;

    @Column(name = "is_one_wire_thermometer_allowed")
    private boolean  oneWireThermometerAllowed;

    @Column(name = "is_one_wire_ibutton_allowed")
    private boolean  oneWireIButtonAllowed;

    @Column(name = "is_green_driving_allowed")
    private boolean  greenDrivingAllowed;

    @Column(name = "is_left_panel_display_din_allowed_fm11")
    private boolean leftPanelDisplayDinAllowedFm11;

    @Column(name = "is_left_panel_display_din_allowed_t6")
    private boolean leftPanelDisplayDinAllowedT6;

    @Column(name = "is_green_driving_snail_trail")
    private boolean greenDrivingSnailTrail;

    @Column(name = "is_display_query_date_range_on_reports")
    private boolean displayQueryDateRangeOnReports;

    @Column(name = "is_hover_over_aoi_allowed")
    private boolean hoverOverAoiAllowed;

    @Column(name = "is_hover_over_poi_allowed")
    private boolean hoverOverPoiAllowed;

    @Column(name = "is_choose_poi_area_colour")
    private boolean choosePoiAreaColour;

    @Column(name = "is_choose_aoi_area_colour")
    private boolean chooseAoiAreaColour;

    @Column(name = "is_satellite_drift_overwrite")
    private boolean satelliteDriftOverwrite;

    @Column(name = "is_split_show_poi_aoi_checkbox")
    private boolean splitShowPoiAoiCheckbox;

    @Column(name = "is_show_poi_area_in_poi_manager")
    private boolean showPoiAreaInPoiManager;

    @Column(name = "is_driver_id_permitted")
    private boolean driverIdPermitted;

    @Column(name = "is_driving_time_panel_allowed")
    private boolean drivingTimePanelAllowed;

    @Column(name = "is_retain_driver_name_after_ignition_off")
    private boolean retainDriverNameAfterIgnitionOff;

    @Column(name = "is_vin_search_allowed")
    private boolean vinSearchAllowed;

    @Column(name = "is_use_ip_filter")
    private boolean useIpFilter;

    @Column(name = "is_ios_application_allowed")
    private boolean iOSApplicationAllowed;

    @Column(name = "is_network_vehicles_allowed")
    private boolean networkVehiclesAllowed;

    public void copyTo(Reseller copy) {
        super.copyTo(copy);
        copy.contactNo = contactNo;
        copy.startDate = startDate;
        copy.notes = notes;
        copy.primaryEmail = primaryEmail;
        copy.meaningfulAddressFixAllowed = meaningfulAddressFixAllowed;
        copy.hideShortJourneysAllowed = hideShortJourneysAllowed;
        copy.secondaryEmail = secondaryEmail;
        copy.billingContactName = billingContactName;
        copy.billingContactEmail = billingContactEmail;
        copy.billingContactPhone = billingContactPhone;
        copy.companyName = companyName;
        copy.addressLine1 = addressLine1;
        copy.addressLine2 = addressLine2;
        copy.addressLine3 = addressLine3;
        copy.addressLine4 = addressLine4;
        copy.leftPanelDisplayDinAllowedT6 = leftPanelDisplayDinAllowedT6;
        copy.postcode = postcode;
        copy.locationBasedDataRegardlessConfidence = locationBasedDataRegardlessConfidence;
        copy.hideWarningAsterisks = hideWarningAsterisks;
        copy.ignitionStatusIconsVersion = ignitionStatusIconsVersion;
        copy.helpMenuAllowed = helpMenuAllowed;
        copy.smsAlertNumbers = smsAlertNumbers;
        copy.fastSmsPassword = fastSmsPassword;
        copy.fastSmsUername = fastSmsUername;
        copy.geoFenceConfidenceMsgsCountEnterAllowed = geoFenceConfidenceMsgsCountEnterAllowed;
        copy.geoFenceConfidenceMsgsCountExitAllowed = geoFenceConfidenceMsgsCountExitAllowed;
        copy.geoFenceConfidenceMsgsOverrideIgnOffAllowed = geoFenceConfidenceMsgsOverrideIgnOffAllowed;
        copy.geoFenceConfidenceMsgsCountEnter = geoFenceConfidenceMsgsCountEnter;
        copy.geoFenceConfidenceMsgsCountExit = geoFenceConfidenceMsgsCountExit;
        copy.fastSmsSourceAddress = fastSmsSourceAddress;
        copy.country = country;
        copy.actionMenuOrdering = actionMenuOrdering;
        copy.actionMenuOrderingAllowed = actionMenuOrderingAllowed;
        copy.poiAreaColoured = poiAreaColoured;
        copy.retainJourneyEndAllowed = retainJourneyEndAllowed;
        copy.urlCodePrefix = urlCodePrefix;
        copy.rebrandingFolder = rebrandingFolder;
        copy.mapSource = mapSource;
        copy.deleted = deleted;
        copy.sevenDigitalPostcodeAllowed = sevenDigitalPostcodeAllowed;
        copy.proximityAllowed = proximityAllowed;
        copy.preferencesAllowed = preferencesAllowed;
        copy.calculatedOrientationAllowed = calculatedOrientationAllowed;
        copy.zoomOnVehicleAllowed = zoomOnVehicleAllowed;
        copy.postcodeSearchAllowed = postcodeSearchAllowed;
        copy.showHidePoisFeatureAllowed = showHidePoisFeatureAllowed;
        copy.showHideAoisFeatureAllowed = showHideAoisFeatureAllowed;
        copy.flexMenu = flexMenu;
        copy.flexLogin = flexLogin;
        copy.hoverOverVehicleAllowedActivity = hoverOverVehicleAllowedActivity;
        copy.hoverOverVehicleAllowedProximity = hoverOverVehicleAllowedProximity;
        copy.flexForgottenPassword = flexForgottenPassword;
        copy.flexScheduledJobs = flexScheduledJobs;
        copy.flexMapping = flexMapping;
        copy.userManualLinkUnderHelpMenu = userManualLinkUnderHelpMenu;
        copy.dashboardAllowed = dashboardAllowed;
        copy.todaysEventsAllowed = todaysEventsAllowed;
        copy.flexGeofence = flexGeofence;
        copy.prevailingSpeedLimitsAllowed = prevailingSpeedLimitsAllowed;
        copy.hoverOverVehicleAllowed = hoverOverVehicleAllowed;
        copy.flexSuggestion = flexSuggestion;
        copy.suggestionEmail = suggestionEmail;
        copy.disableTimedLogout = disableTimedLogout;
        copy.flexManagePoi = flexManagePoi;
        copy.flexManageAoi = flexManageAoi;
        copy.mapKey = mapKey;
        copy.mapPassword = mapPassword;
        copy.flexPreferences = flexPreferences;
        copy.backToTheFutureAllowed = backToTheFutureAllowed;
        copy.reportOrderingAllowed = reportOrderingAllowed;
        copy.flexProximity = flexProximity;
        copy.mapViewPlusVisible = mapViewPlusVisible;
        copy.accountLogoSwitchDefaultDelay = accountLogoSwitchDefaultDelay;
        copy.flexSnailTrailEnhanced = flexSnailTrailEnhanced;
        copy.hideMapToolsAllowed = hideMapToolsAllowed;
        copy.snailTrailSideBySide = snailTrailSideBySide;
        copy.snailTrailPlayAllowed = snailTrailPlayAllowed;
        copy.pauseMapAllowed = pauseMapAllowed;
        copy.snailTrailFullscreen = snailTrailFullscreen;
        copy.snailTrailDisplayAllPins = snailTrailDisplayAllPins;
        copy.timestamp = timestamp;
        copy.fleetIdOverIcons = fleetIdOverIcons;
        copy.glowIcons = glowIcons;
        copy.refreshWholeBrowserAllowed = refreshWholeBrowserAllowed;
        copy.countdownSmRefreshAllowed = countdownSmRefreshAllowed;
        copy.preferencesUnderAdminSection = preferencesUnderAdminSection;
        copy.activityViewVisible = activityViewVisible;
        copy.incomingLogProcessorServiceId = incomingLogProcessorServiceId;
        copy.virtualPagenationAllowed = virtualPagenationAllowed;
        copy.reducePinSizeOnZoomOut = reducePinSizeOnZoomOut;
        copy.replaceDriverNameWithRegNum = replaceDriverNameWithRegNum;
        copy.leftPanelDisplayDinAllowedFm2x = leftPanelDisplayDinAllowedFm2x;
        copy.leftPanelDisplayDinAllowedFm4x = leftPanelDisplayDinAllowedFm4x;
        copy.selectionMenuGroupNameVisible = selectionMenuGroupNameVisible;
        copy.allowShowExpandedSelectionMenu = allowShowExpandedSelectionMenu;
        copy.useGeoserverRg = useGeoserverRg;
        copy.languageId = languageId;
        copy.showUserManual = showUserManual;
        copy.userManualLink = userManualLink;
        copy.notPreselectedScheduledRecurentPattern = notPreselectedScheduledRecurentPattern;
        copy.geoserverRgTitleCase = geoserverRgTitleCase;
        copy.geoserverRGexceptedAccounts = geoserverRGexceptedAccounts;
        copy.showSupportEmail = showSupportEmail;
        copy.leftMenuPerformanceBoosted = leftMenuPerformanceBoosted;
        copy.supportEmail = supportEmail;
        copy.lastGaspAllowed = lastGaspAllowed;
        copy.contactUsEmail = contactUsEmail;
        copy.contactUsFax = contactUsFax;
        copy.contactUsPhone = contactUsPhone;
        copy.contactUsWeb = contactUsWeb;
        copy.showT6T8MessageTypeAllowed = showT6T8MessageTypeAllowed;
        copy.detailsViewVisible = detailsViewVisible;
        copy.hideRedPin = hideRedPin;
        copy.refreshSmRateFrom10Allowed = refreshSmRateFrom10Allowed;
        copy.stickyRoads = stickyRoads;
        copy.multiColorPinsPoi = multiColorPinsPoi;
        copy.multiColorPinsAoi = multiColorPinsAoi;
        copy.freezeSmMenu = freezeSmMenu;
        copy.retainUserSelectionAllowedAoi = retainUserSelectionAllowedAoi;
        copy.retainUserSelectionAllowedPoi = retainUserSelectionAllowedPoi;
        copy.stickyRoadsDisableOnIgnOff = stickyRoadsDisableOnIgnOff;
        copy.stickyRoadsDisableByAccount = stickyRoadsDisableByAccount;
        copy.poiAoiListOrderAllowed = poiAoiListOrderAllowed;
        copy.alwaysDisplayDriverName = alwaysDisplayDriverName;
        copy.useDotsOnMap = useDotsOnMap;
        copy.showVehTooltipAllowed = showVehTooltipAllowed;
        copy.passwordReminderHost  = passwordReminderHost;
        copy.passwordReminderFromAddress = passwordReminderFromAddress;
        copy.passwordReminderSmtpPassword = passwordReminderSmtpPassword;
        copy.passwordReminderSmtpPort = passwordReminderSmtpPort;
        copy.passwordReminderSmtpUsername = passwordReminderSmtpUsername;
        copy.passwordReminderSubject = passwordReminderSubject;
        copy.reportHost = reportHost;
        copy.reportFromAddress = reportFromAddress;
        copy.reportSmtpPassword = reportSmtpPassword;
        copy.showStopTimeAlternateJourneyAllowed = showStopTimeAlternateJourneyAllowed;
        copy.reportSmtpPort = reportSmtpPort;
        copy.reportSmtpUsername = reportSmtpUsername;
        copy.reportSubject = reportSubject;
        copy.backToTheFutureModifiedAllowed = backToTheFutureModifiedAllowed;
        copy.tflCameraAllowed = tflCameraAllowed;
        copy.tflDisruptionsAllowed = tflDisruptionsAllowed;
        copy.geoFenceAlertHost = geoFenceAlertHost;
        copy.geoFenceAlertFromAddress = geoFenceAlertFromAddress;
        copy.geoFenceAlertSmtpPassword = geoFenceAlertSmtpPassword;
        copy.geoFenceAlertSmtpPort = geoFenceAlertSmtpPort;
        copy.geoFenceAlertSmtpUsername = geoFenceAlertSmtpUsername;
        copy.geoFenceAlertSubject = geoFenceAlertSubject;
        copy.suggestionHost = suggestionHost;
        copy.suggestionFromAddress = suggestionFromAddress;
        copy.suggestionSmtpPassword = suggestionSmtpPassword;
        copy.suggestionSmtpPort = suggestionSmtpPort;
        copy.maxDefaultZoomLevel = maxDefaultZoomLevel;
        copy.suggestionSmtpUsername = suggestionSmtpUsername;
        copy.suggestionSubject = suggestionSubject;
        copy.drivingTimePanelAllowed = drivingTimePanelAllowed;
        copy.prefixSmLocationWithNr = prefixSmLocationWithNr;
        copy.drawArrowFromVehIconToRoad = drawArrowFromVehIconToRoad;
        copy.oneWireIButtonAllowed = oneWireIButtonAllowed;
        copy.oneWireThermometerAllowed = oneWireThermometerAllowed;
        copy.greenDrivingAllowed = greenDrivingAllowed;
        copy.leftPanelDisplayDinAllowedFm11 = leftPanelDisplayDinAllowedFm11;
        copy.greenDrivingSnailTrail = greenDrivingSnailTrail;
        copy.displayQueryDateRangeOnReports = displayQueryDateRangeOnReports;
        copy.aoiAreaColoured = aoiAreaColoured;
        copy.hoverOverAoiAllowed = hoverOverAoiAllowed;
        copy.hoverOverPoiAllowed = hoverOverPoiAllowed;
        copy.choosePoiAreaColour = choosePoiAreaColour;
        copy.chooseAoiAreaColour = chooseAoiAreaColour;
        copy.satelliteDriftOverwrite = satelliteDriftOverwrite;
        copy.zeroTodaysCostAtMidnight = zeroTodaysCostAtMidnight;
        copy.handheldClusteringAllowed = handheldClusteringAllowed;
        copy.splitShowPoiAoiCheckbox = splitShowPoiAoiCheckbox;
        copy.showPoiAreaInPoiManager = showPoiAreaInPoiManager;
        copy.driverIdPermitted = driverIdPermitted;
        copy.retainDriverNameAfterIgnitionOff = retainDriverNameAfterIgnitionOff;
        copy.vinSearchAllowed = vinSearchAllowed;
        copy.useIpFilter=useIpFilter;
        copy.iOSApplicationAllowed = iOSApplicationAllowed;
        copy.networkVehiclesAllowed = networkVehiclesAllowed;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getBillingContactName() {
        return billingContactName;
    }

    public void setBillingContactName(String billingContactName) {
        this.billingContactName = billingContactName;
    }

    public String getBillingContactEmail() {
        return billingContactEmail;
    }

    public void setBillingContactEmail(String billingContactEmail) {
        this.billingContactEmail = billingContactEmail;
    }

    public String getBillingContactPhone() {
        return billingContactPhone;
    }

    public void setBillingContactPhone(String billingContactPhone) {
        this.billingContactPhone = billingContactPhone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isFlexMapping() {
        return flexMapping;
    }

    public void setFlexMapping(boolean flexMapping) {
        this.flexMapping = flexMapping;
    }

    public boolean isHoverOverVehicleAllowed() {
        return hoverOverVehicleAllowed;
    }

    public void setHoverOverVehicleAllowed(boolean hoverOverVehicleAllowed) {
        this.hoverOverVehicleAllowed = hoverOverVehicleAllowed;
    }

    public String getMapPassword() {
        return mapPassword;
    }

    public void setMapPassword(String mapPassword) {
        this.mapPassword = mapPassword;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getIncomingLogProcessorServiceId() {
        return incomingLogProcessorServiceId;
    }

    public void setIncomingLogProcessorServiceId(String incomingLogProcessorServiceId) {
        this.incomingLogProcessorServiceId = incomingLogProcessorServiceId;
    }

    public boolean isUseGeoserverRg() {
        return useGeoserverRg;
    }

    public void setUseGeoserverRg(boolean useGeoserverRg) {
        this.useGeoserverRg = useGeoserverRg;
    }

    public LanguageId getLanguageId() {
        return languageId;
    }

    public void setLanguageId(LanguageId languageId) {
        this.languageId = languageId;
    }

    public static enum ROLES {
        ROLE_IS_ZOOM_ON_VEHICLE_ALLOWED
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    public void setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public MapSource getMapSource() {
        return mapSource;
    }

    public void setMapSource(MapSource mapSource) {
        this.mapSource = mapSource;
    }

    public boolean isZoomOnVehicleAllowed() {
        return zoomOnVehicleAllowed;
    }

    public void setZoomOnVehicleAllowed(boolean zoomOnVehicleAllowed) {
        this.zoomOnVehicleAllowed = zoomOnVehicleAllowed;
    }

    public boolean isShowHidePoisFeatureAllowed() {
        return showHidePoisFeatureAllowed;
    }

    public void setShowHidePoisFeatureAllowed(boolean showHidePoisFeatureAllowed) {
        this.showHidePoisFeatureAllowed = showHidePoisFeatureAllowed;
    }

    public boolean isShowHideAoisFeatureAllowed() {
        return showHideAoisFeatureAllowed;
    }

    public void setShowHideAoisFeatureAllowed(boolean showHideAoisFeatureAllowed) {
        this.showHideAoisFeatureAllowed = showHideAoisFeatureAllowed;
    }


    public boolean isFlexMenu() {
        return flexMenu;
    }

    public void setFlexMenu(boolean flexMenu) {
        this.flexMenu = flexMenu;
    }

    public boolean isFlexLogin() {
        return flexLogin;
    }

    public void setFlexLogin(boolean flexLogin) {
        this.flexLogin = flexLogin;
    }

    public boolean isFlexForgottenPassword() {
        return flexForgottenPassword;
    }

    public void setFlexForgottenPassword(boolean flexForgottenPassword) {
        this.flexForgottenPassword = flexForgottenPassword;
    }

    public boolean isFlexScheduledJobs() {
        return flexScheduledJobs;
    }

    public void setFlexScheduledJobs(boolean flexScheduledJobs) {
        this.flexScheduledJobs = flexScheduledJobs;
    }

    public boolean isDashboardAllowed() {
        return dashboardAllowed;
    }

    public void setDashboardAllowed(boolean dashboardAllowed) {
        this.dashboardAllowed = dashboardAllowed;
    }

    public boolean isFlexGeofence() {
        return flexGeofence;
    }

    public void setFlexGeofence(boolean flexGeofence) {
        this.flexGeofence = flexGeofence;
    }

    public boolean isFlexSuggestion() {
        return flexSuggestion;
    }

    public void setFlexSuggestion(boolean flexSuggestion) {
        this.flexSuggestion = flexSuggestion;
    }

    public String getSuggestionEmail() {
        return suggestionEmail;
    }

    public void setSuggestionEmail(String suggestionEmail) {
        this.suggestionEmail = suggestionEmail;
    }

    public boolean isDisableTimedLogout() {
        return disableTimedLogout;
    }

    public void setDisableTimedLogout(boolean disableTimedLogout) {
        this.disableTimedLogout = disableTimedLogout;
    }

    public boolean isFlexManagePoi() {
        return flexManagePoi;
    }

    public void setFlexManagePoi(boolean flexManagePoi) {
        this.flexManagePoi = flexManagePoi;
    }

    public boolean isFlexManageAoi() {
        return flexManageAoi;
    }

    public void setFlexManageAoi(boolean flexManageAoi) {
        this.flexManageAoi = flexManageAoi;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public boolean isFlexPreferences() {
        return flexPreferences;
    }

    public void setFlexPreferences(boolean flexPreferences) {
        this.flexPreferences = flexPreferences;
    }

    public boolean isFlexProximity() {
        return flexProximity;
    }

    public void setFlexProximity(boolean flexProximity) {
        this.flexProximity = flexProximity;
    }

    public int getAccountLogoSwitchDefaultDelay() {
        return accountLogoSwitchDefaultDelay;
    }

    public void setAccountLogoSwitchDefaultDelay(int accountLogoSwitchDefaultDelay) {
        this.accountLogoSwitchDefaultDelay = accountLogoSwitchDefaultDelay;
    }

    public boolean isFlexSnailTrailEnhanced() {
        return flexSnailTrailEnhanced;
    }

    public void setFlexSnailTrailEnhanced(boolean flexSnailTrailEnhanced) {
        this.flexSnailTrailEnhanced = flexSnailTrailEnhanced;
    }

    public boolean isMapViewPlusVisible() {
        return mapViewPlusVisible;
    }

    public void setMapViewPlusVisible(boolean mapViewPlusVisible) {
        this.mapViewPlusVisible = mapViewPlusVisible;
    }

    public boolean isSnailTrailPlayAllowed() {
        return snailTrailPlayAllowed;
    }

    public void setSnailTrailPlayAllowed(boolean snailTrailPlayAllowed) {
        this.snailTrailPlayAllowed = snailTrailPlayAllowed;
    }

    public boolean isHideMapToolsAllowed() {
        return hideMapToolsAllowed;
    }

    public void setHideMapToolsAllowed(boolean hideMapToolsAllowed) {
        this.hideMapToolsAllowed = hideMapToolsAllowed;
    }

    public boolean isSnailTrailSideBySide() {
        return snailTrailSideBySide;
    }

    public void setSnailTrailSideBySide(boolean snailTrailSideBySide) {
        this.snailTrailSideBySide = snailTrailSideBySide;
    }


    public boolean isPauseMapAllowed() {
        return pauseMapAllowed;
    }

    public void setPauseMapAllowed(boolean pauseMapAllowed) {
        this.pauseMapAllowed = pauseMapAllowed;
    }

    public String getUrlCodePrefix() {
         return urlCodePrefix;
     }

     public void setUrlCodePrefix(String urlCodePrefix) {
         this.urlCodePrefix = urlCodePrefix;
     }

     public String getRebrandingFolder() {
         return rebrandingFolder;
     }

     public void setRebrandingFolder(String rebrandingFolder) {
         this.rebrandingFolder = rebrandingFolder;
     }

    public boolean isTodaysEventsAllowed() {
        return todaysEventsAllowed;
    }

    public void setTodaysEventsAllowed(boolean todaysEventsAllowed) {
        this.todaysEventsAllowed = todaysEventsAllowed;
    }

    public boolean isSnailTrailFullscreen() {
        return snailTrailFullscreen;
    }

    public void setSnailTrailFullscreen(boolean snailTrailFullscreen) {
        this.snailTrailFullscreen = snailTrailFullscreen;
    }

    public boolean isSnailTrailDisplayAllPins() {
        return snailTrailDisplayAllPins;
    }

    public void setSnailTrailDisplayAllPins(boolean snailTrailDisplayAllPins) {
        this.snailTrailDisplayAllPins = snailTrailDisplayAllPins;
    }

    public boolean isFleetIdOverIcons() {
        return fleetIdOverIcons;
    }

    public void setFleetIdOverIcons(boolean fleetIdOverIcons) {
        this.fleetIdOverIcons = fleetIdOverIcons;
    }

    public boolean isGlowIcons() {
        return glowIcons;
    }

    public void setGlowIcons(boolean glowIcons) {
        this.glowIcons = glowIcons;
    }

    public boolean isPostcodeSearchAllowed() {
        return postcodeSearchAllowed;
    }

    public void setPostcodeSearchAllowed(boolean postcodeSearchAllowed) {
        this.postcodeSearchAllowed = postcodeSearchAllowed;
    }

    public boolean isRetainJourneyEndAllowed() {
        return retainJourneyEndAllowed;
    }

    public void setRetainJourneyEndAllowed(boolean retainJourneyEndAllowed) {
        this.retainJourneyEndAllowed = retainJourneyEndAllowed;
    }

    public boolean isCalculatedOrientationAllowed() {
        return calculatedOrientationAllowed;
    }

    public void setCalculatedOrientationAllowed(boolean calculatedOrientationAllowed) {
        this.calculatedOrientationAllowed = calculatedOrientationAllowed;
    }

    public boolean isActivityViewVisible() {
        return activityViewVisible;
    }

    public void setActivityViewVisible(boolean activityViewVisible) {
        this.activityViewVisible = activityViewVisible;
    }

    public boolean isReducePinSizeOnZoomOut() {
        return reducePinSizeOnZoomOut;
    }

    public void setReducePinSizeOnZoomOut(boolean reducePinSizeOnZoomOut) {
        this.reducePinSizeOnZoomOut = reducePinSizeOnZoomOut;
    }

    public boolean isReplaceDriverNameWithRegNum() {
        return replaceDriverNameWithRegNum;
    }

    public void setReplaceDriverNameWithRegNum(boolean replaceDriverNameWithRegNum) {
        this.replaceDriverNameWithRegNum = replaceDriverNameWithRegNum;
    }

    public boolean isLeftPanelDisplayDinAllowedFm2x() {
        return leftPanelDisplayDinAllowedFm2x;
    }

    public void setLeftPanelDisplayDinAllowedFm2x(boolean leftPanelDisplayDinAllowedFm2x) {
        this.leftPanelDisplayDinAllowedFm2x = leftPanelDisplayDinAllowedFm2x;
    }

    public boolean isHideShortJourneysAllowed() {
        return hideShortJourneysAllowed;
    }

    public void setHideShortJourneysAllowed(boolean hideShortJourneysAllowed) {
        this.hideShortJourneysAllowed = hideShortJourneysAllowed;
    }

    public boolean isLeftPanelDisplayDinAllowedFm4x() {
        return leftPanelDisplayDinAllowedFm4x;
    }

    public void setLeftPanelDisplayDinAllowedFm4x(boolean leftPanelDisplayDinAllowedFm4x) {
        this.leftPanelDisplayDinAllowedFm4x = leftPanelDisplayDinAllowedFm4x;
    }

    public boolean isSelectionMenuGroupNameVisible() {
        return selectionMenuGroupNameVisible;
    }

    public void setSelectionMenuGroupNameVisible(boolean selectionMenuGroupNameVisible) {
        this.selectionMenuGroupNameVisible = selectionMenuGroupNameVisible;
    }

    public String getSmsAlertNumbers() {
        return smsAlertNumbers;
    }

    public void setSmsAlertNumbers(String smsAlertNumbers) {
        this.smsAlertNumbers = smsAlertNumbers;
    }

    public String getFastSmsUername() {
        return fastSmsUername;
    }

    public void setFastSmsUername(String fastSmsUername) {
        this.fastSmsUername = fastSmsUername;
    }

    public String getFastSmsPassword() {
        return fastSmsPassword;
    }

    public void setFastSmsPassword(String fastSmsPassword) {
        this.fastSmsPassword = fastSmsPassword;
    }

    public String getFastSmsSourceAddress() {
        return fastSmsSourceAddress;
    }

    public void setFastSmsSourceAddress(String fastSmsSourceAddress) {
        this.fastSmsSourceAddress = fastSmsSourceAddress;
    }

    public boolean isAllowShowExpandedSelectionMenu() {
        return allowShowExpandedSelectionMenu;
    }

    public void setAllowShowExpandedSelectionMenu(boolean allowShowExpandedSelectionMenu) {
        this.allowShowExpandedSelectionMenu = allowShowExpandedSelectionMenu;
    }

    public boolean isShowUserManual() {
        return showUserManual;
    }

    public void setShowUserManual(boolean showUserManual) {
        this.showUserManual = showUserManual;
    }

    public String getUserManualLink() {
        if (userManualLink == null || userManualLink.length() == 0) {
            return "";
        }
        if (userManualLink.startsWith("http://")) {
            return userManualLink;
        } else {
            return "http://" + userManualLink;
        }

    }

    public void setUserManualLink(String userManualLink) {
        this.userManualLink = userManualLink;
    }

    public boolean isShowSupportEmail() {
        return showSupportEmail;
    }

    public void setShowSupportEmail(boolean showSupportEmail) {
        this.showSupportEmail = showSupportEmail;
    }

    public boolean isLeftMenuPerformanceBoosted() {
        return leftMenuPerformanceBoosted;
    }

    public void setLeftMenuPerformanceBoosted(boolean leftMenuPerformanceBoosted) {
        this.leftMenuPerformanceBoosted = leftMenuPerformanceBoosted;
    }


    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public boolean isReportOrderingAllowed() {
        return reportOrderingAllowed;
    }

    public void setReportOrderingAllowed(boolean reportOrderingAllowed) {
        this.reportOrderingAllowed = reportOrderingAllowed;
    }

    public boolean isHelpMenuAllowed() {
        return helpMenuAllowed;
    }

    public void setHelpMenuAllowed(boolean helpMenuAllowed) {
        this.helpMenuAllowed = helpMenuAllowed;
    }

    public String getUserManualLinkUnderHelpMenu() {
        return userManualLinkUnderHelpMenu;
    }

    public void setUserManualLinkUnderHelpMenu(String userManualLinkUnderHelpMenu) {
        this.userManualLinkUnderHelpMenu = userManualLinkUnderHelpMenu;
    }

    public String getContactUsPhone() {
        return contactUsPhone;
    }

    public void setContactUsPhone(String contactUsPhone) {
        this.contactUsPhone = contactUsPhone;
    }

    public String getContactUsFax() {
        return contactUsFax;
    }

    public void setContactUsFax(String contactUsFax) {
        this.contactUsFax = contactUsFax;
    }

    public String getContactUsEmail() {
        return contactUsEmail;
    }

    public void setContactUsEmail(String contactUsEmail) {
        this.contactUsEmail = contactUsEmail;
    }

    public String getContactUsWeb() {
        return contactUsWeb;
    }

    public void setContactUsWeb(String contactUsWeb) {
        this.contactUsWeb = contactUsWeb;
    }

    public boolean isHoverOverVehicleAllowedProximity() {
        return hoverOverVehicleAllowedProximity;
    }

    public void setHoverOverVehicleAllowedProximity(boolean hoverOverVehicleAllowedProximity) {
        this.hoverOverVehicleAllowedProximity = hoverOverVehicleAllowedProximity;
    }

    public boolean isHoverOverVehicleAllowedActivity() {
        return hoverOverVehicleAllowedActivity;
    }

    public void setHoverOverVehicleAllowedActivity(boolean hoverOverVehicleAllowedActivity) {
        this.hoverOverVehicleAllowedActivity = hoverOverVehicleAllowedActivity;
    }


    public boolean isDetailsViewVisible() {
        return detailsViewVisible;
    }

    public void setDetailsViewVisible(boolean detailsViewVisible) {
        this.detailsViewVisible = detailsViewVisible;
    }

    public boolean isStickyRoads() {
        return stickyRoads;
    }

    public void setStickyRoads(boolean stickyRoads) {
        this.stickyRoads = stickyRoads;
    }

    public boolean isMeaningfulAddressFixAllowed() {
        return meaningfulAddressFixAllowed;
    }

    public void setMeaningfulAddressFixAllowed(boolean meaningfulAddressFixAllowed) {
        this.meaningfulAddressFixAllowed = meaningfulAddressFixAllowed;
    }


    public boolean isHideRedPin() {
        return hideRedPin;
    }

    public void setHideRedPin(boolean hideRedPin) {
        this.hideRedPin = hideRedPin;
    }

    public boolean isVirtualPagenationAllowed() {
        return virtualPagenationAllowed;
    }

    public void setVirtualPagenationAllowed(boolean virtualPagenationAllowed) {
        this.virtualPagenationAllowed = virtualPagenationAllowed;
    }

    public boolean isMultiColorPinsPoi() {
        return multiColorPinsPoi;
    }

    public void setMultiColorPinsPoi(boolean multiColorPinsPoi) {
        this.multiColorPinsPoi = multiColorPinsPoi;
    }

    public boolean isMultiColorPinsAoi() {
        return multiColorPinsAoi;
    }

    public void setMultiColorPinsAoi(boolean multiColorPinsAoi) {
        this.multiColorPinsAoi = multiColorPinsAoi;
    }

    public boolean isFreezeSmMenu() {
        return freezeSmMenu;
    }

    public void setFreezeSmMenu(boolean freezeSmMenu) {
        this.freezeSmMenu = freezeSmMenu;
    }

    public Integer getIgnitionStatusIconsVersion() {
        return ignitionStatusIconsVersion;
    }

    public void setIgnitionStatusIconsVersion(Integer ignitionStatusIconsVersion) {
        this.ignitionStatusIconsVersion = ignitionStatusIconsVersion;
    }

    public boolean isStickyRoadsDisableOnIgnOff() {
        return stickyRoadsDisableOnIgnOff;
    }

    public void setStickyRoadsDisableOnIgnOff(boolean stickyRoadsDisableOnIgnOff) {
        this.stickyRoadsDisableOnIgnOff = stickyRoadsDisableOnIgnOff;
    }

    public boolean isPrevailingSpeedLimitsAllowed() {
        return prevailingSpeedLimitsAllowed;
    }

    public void setPrevailingSpeedLimitsAllowed(boolean prevailingSpeedLimitsAllowed) {
        this.prevailingSpeedLimitsAllowed = prevailingSpeedLimitsAllowed;
    }


    public boolean isStickyRoadsDisableByAccount() {
        return stickyRoadsDisableByAccount;
    }

    public void setStickyRoadsDisableByAccount(boolean stickyRoadsDisableByAccount) {
        this.stickyRoadsDisableByAccount = stickyRoadsDisableByAccount;
    }

    public boolean isPoiAoiListOrderAllowed() {
        return poiAoiListOrderAllowed;
    }

    public void setPoiAoiListOrderAllowed(boolean poiAoiListOrderAllowed) {
        this.poiAoiListOrderAllowed = poiAoiListOrderAllowed;
    }

    public boolean isAlwaysDisplayDriverName() {
        return alwaysDisplayDriverName;
    }

    public void setAlwaysDisplayDriverName(boolean alwaysDisplayDriverName) {
        this.alwaysDisplayDriverName = alwaysDisplayDriverName;
    }

    public boolean isUseDotsOnMap() {
        return useDotsOnMap;
    }

    public void setUseDotsOnMap(boolean useDotsOnMap) {
        this.useDotsOnMap = useDotsOnMap;
    }

    public boolean isShowVehTooltipAllowed() {
        return showVehTooltipAllowed;
    }

    public void setShowVehTooltipAllowed(boolean showVehTooltipAllowed) {
        this.showVehTooltipAllowed = showVehTooltipAllowed;
    }

    public String getPasswordReminderSubject() {
        return passwordReminderSubject;
    }

    public void setPasswordReminderSubject(String passwordReminderSubject) {
        this.passwordReminderSubject = passwordReminderSubject;
    }

    public String getPasswordReminderFromAddress() {
        return passwordReminderFromAddress;
    }

    public void setPasswordReminderFromAddress(String passwordReminderFromAddress) {
        this.passwordReminderFromAddress = passwordReminderFromAddress;
    }

    public String getPasswordReminderSmtpUsername() {
        return passwordReminderSmtpUsername;
    }

    public void setPasswordReminderSmtpUsername(String passwordReminderSmtpUsername) {
        this.passwordReminderSmtpUsername = passwordReminderSmtpUsername;
    }

    public String getPasswordReminderSmtpPassword() {
        return passwordReminderSmtpPassword;
    }

    public void setPasswordReminderSmtpPassword(String passwordReminderSmtpPassword) {
        this.passwordReminderSmtpPassword = passwordReminderSmtpPassword;
    }

    public Integer getPasswordReminderSmtpPort() {
        return passwordReminderSmtpPort;
    }

    public void setPasswordReminderSmtpPort(Integer passwordReminderSmtpPort) {
        this.passwordReminderSmtpPort = passwordReminderSmtpPort;
    }

    public String getReportSubject() {
        return reportSubject;
    }

    public void setReportSubject(String reportSubject) {
        this.reportSubject = reportSubject;
    }

    public String getReportFromAddress() {
        return reportFromAddress;
    }

    public void setReportFromAddress(String reportFromAddress) {
        this.reportFromAddress = reportFromAddress;
    }

    public String getReportSmtpUsername() {
        return reportSmtpUsername;
    }

    public void setReportSmtpUsername(String reportSmtpUsername) {
        this.reportSmtpUsername = reportSmtpUsername;
    }

    public String getReportSmtpPassword() {
        return reportSmtpPassword;
    }

    public void setReportSmtpPassword(String reportSmtpPassword) {
        this.reportSmtpPassword = reportSmtpPassword;
    }

    public Integer getReportSmtpPort() {
        return reportSmtpPort;
    }

    public void setReportSmtpPort(Integer reportSmtpPort) {
        this.reportSmtpPort = reportSmtpPort;
    }

    public String getGeoFenceAlertSubject() {
        return geoFenceAlertSubject;
    }

    public void setGeoFenceAlertSubject(String geoFenceAlertSubject) {
        this.geoFenceAlertSubject = geoFenceAlertSubject;
    }

    public String getGeoFenceAlertFromAddress() {
        return geoFenceAlertFromAddress;
    }

    public void setGeoFenceAlertFromAddress(String geoFenceAlertFromAddress) {
        this.geoFenceAlertFromAddress = geoFenceAlertFromAddress;
    }

    public String getGeoFenceAlertSmtpUsername() {
        return geoFenceAlertSmtpUsername;
    }

    public void setGeoFenceAlertSmtpUsername(String geoFenceAlertSmtpUsername) {
        this.geoFenceAlertSmtpUsername = geoFenceAlertSmtpUsername;
    }

    public String getGeoFenceAlertSmtpPassword() {
        return geoFenceAlertSmtpPassword;
    }

    public void setGeoFenceAlertSmtpPassword(String geoFenceAlertSmtpPassword) {
        this.geoFenceAlertSmtpPassword = geoFenceAlertSmtpPassword;
    }

    public Integer getGeoFenceAlertSmtpPort() {
        return geoFenceAlertSmtpPort;
    }

    public void setGeoFenceAlertSmtpPort(Integer geoFenceAlertSmtpPort) {
        this.geoFenceAlertSmtpPort = geoFenceAlertSmtpPort;
    }

    public String getSuggestionSubject() {
        return suggestionSubject;
    }

    public void setSuggestionSubject(String suggestionSubject) {
        this.suggestionSubject = suggestionSubject;
    }

    public String getSuggestionFromAddress() {
        return suggestionFromAddress;
    }

    public void setSuggestionFromAddress(String suggestionFromAddress) {
        this.suggestionFromAddress = suggestionFromAddress;
    }

    public String getSuggestionSmtpUsername() {
        return suggestionSmtpUsername;
    }

    public void setSuggestionSmtpUsername(String suggestionSmtpUsername) {
        this.suggestionSmtpUsername = suggestionSmtpUsername;
    }

    public String getSuggestionSmtpPassword() {
        return suggestionSmtpPassword;
    }

    public void setSuggestionSmtpPassword(String suggestionSmtpPassword) {
        this.suggestionSmtpPassword = suggestionSmtpPassword;
    }

    public Integer getSuggestionSmtpPort() {
        return suggestionSmtpPort;
    }

    public void setSuggestionSmtpPort(Integer suggestionSmtpPort) {
        this.suggestionSmtpPort = suggestionSmtpPort;
    }

    public String getPasswordReminderHost() {
        return passwordReminderHost;
    }

    public void setPasswordReminderHost(String passwordReminderHost) {
        this.passwordReminderHost = passwordReminderHost;
    }

    public String getReportHost() {
        return reportHost;
    }

    public void setReportHost(String reportHost) {
        this.reportHost = reportHost;
    }

    public String getGeoFenceAlertHost() {
        return geoFenceAlertHost;
    }

    public void setGeoFenceAlertHost(String geoFenceAlertHost) {
        this.geoFenceAlertHost = geoFenceAlertHost;
    }

    public String getSuggestionHost() {
        return suggestionHost;
    }

    public void setSuggestionHost(String suggestionHost) {
        this.suggestionHost = suggestionHost;
    }

    public boolean isPoiAreaColoured() {
        return poiAreaColoured;
    }

    public void setPoiAreaColoured(boolean poiAreaColoured) {
        this.poiAreaColoured = poiAreaColoured;
    }

    public String getGeoserverRGexceptedAccounts() {
        return geoserverRGexceptedAccounts;
    }

    public void setGeoserverRGexceptedAccounts(String geoserverRGexceptedAccounts) {
        this.geoserverRGexceptedAccounts = geoserverRGexceptedAccounts;
    }

    public boolean isBackToTheFutureAllowed() {
        return backToTheFutureAllowed;
    }

    public void setBackToTheFutureAllowed(boolean backToTheFutureAllowed) {
        this.backToTheFutureAllowed = backToTheFutureAllowed;
    }

    public boolean isPrefixSmLocationWithNr() {
        return prefixSmLocationWithNr;
    }

    public void setPrefixSmLocationWithNr(boolean prefixSmLocationWithNr) {
        this.prefixSmLocationWithNr = prefixSmLocationWithNr;
    }

    public boolean isPreferencesUnderAdminSection() {
        return preferencesUnderAdminSection;
    }

    public void setPreferencesUnderAdminSection(boolean preferencesUnderAdminSection) {
        this.preferencesUnderAdminSection = preferencesUnderAdminSection;
    }

    public boolean isGeoserverRgTitleCase() {
        return geoserverRgTitleCase;
    }

    public void setGeoserverRgTitleCase(boolean geoserverRgTitleCase) {
        this.geoserverRgTitleCase = geoserverRgTitleCase;
    }

    public boolean isShowT6T8MessageTypeAllowed() {
        return showT6T8MessageTypeAllowed;
    }

    public void setShowT6T8MessageTypeAllowed(boolean showT6T8MessageTypeAllowed) {
        this.showT6T8MessageTypeAllowed = showT6T8MessageTypeAllowed;
    }

    public boolean isDrawArrowFromVehIconToRoad() {
        return drawArrowFromVehIconToRoad;
    }

    public void setDrawArrowFromVehIconToRoad(boolean drawArrowFromVehIconToRoad) {
        this.drawArrowFromVehIconToRoad = drawArrowFromVehIconToRoad;
    }

    public boolean isOneWireThermometerAllowed() {
        return oneWireThermometerAllowed;
    }

    public void setOneWireThermometerAllowed(boolean oneWireThermometerAllowed) {
        this.oneWireThermometerAllowed = oneWireThermometerAllowed;
    }

    public boolean isOneWireIButtonAllowed() {
        return oneWireIButtonAllowed;
    }

    public void setOneWireIButtonAllowed(boolean oneWireIButtonAllowed) {
        this.oneWireIButtonAllowed = oneWireIButtonAllowed;
    }

    public boolean isGreenDrivingAllowed() {
        return greenDrivingAllowed;
    }

    public void setGreenDrivingAllowed(boolean greenDrivingAllowed) {
        this.greenDrivingAllowed = greenDrivingAllowed;
    }

    public boolean isLeftPanelDisplayDinAllowedFm11() {
        return leftPanelDisplayDinAllowedFm11;
    }

    public void setLeftPanelDisplayDinAllowedFm11(boolean leftPanelDisplayDinAllowedFm11) {
        this.leftPanelDisplayDinAllowedFm11 = leftPanelDisplayDinAllowedFm11;
    }

    public boolean isProximityAllowed() {
        return proximityAllowed;
    }

    public void setProximityAllowed(boolean proximityAllowed) {
        this.proximityAllowed = proximityAllowed;
    }

    public boolean isPreferencesAllowed() {
        return preferencesAllowed;
    }

    public void setPreferencesAllowed(boolean preferencesAllowed) {
        this.preferencesAllowed = preferencesAllowed;
    }

    public boolean isLeftPanelDisplayDinAllowedT6() {
       return leftPanelDisplayDinAllowedT6;
   }

   public void setLeftPanelDisplayDinAllowedT6(boolean leftPanelDisplayDinAllowedT6) {
       this.leftPanelDisplayDinAllowedT6 = leftPanelDisplayDinAllowedT6;
   }

   public boolean isGreenDrivingSnailTrail() {
        return greenDrivingSnailTrail;
   }

   public void setGreenDrivingSnailTrail(boolean greenDrivingSnailTrail) {
        this.greenDrivingSnailTrail = greenDrivingSnailTrail;
   }

    public int getMaxDefaultZoomLevel() {
        return maxDefaultZoomLevel;
    }

    public void setMaxDefaultZoomLevel(int maxDefaultZoomLevel) {
        this.maxDefaultZoomLevel = maxDefaultZoomLevel;
    }

    public boolean isNotPreselectedScheduledRecurentPattern() {
        return notPreselectedScheduledRecurentPattern;
    }

    public void setNotPreselectedScheduledRecurentPattern(boolean notPreselectedScheduledRecurentPattern) {
        this.notPreselectedScheduledRecurentPattern = notPreselectedScheduledRecurentPattern;
    }

    public boolean isLastGaspAllowed() {
        return lastGaspAllowed;
    }

    public void setLastGaspAllowed(boolean lastGaspAllowed) {
        this.lastGaspAllowed = lastGaspAllowed;
    }

    public boolean isActionMenuOrderingAllowed() {
        return actionMenuOrderingAllowed;
    }

    public void setActionMenuOrderingAllowed(boolean actionMenuOrderingAllowed) {
        this.actionMenuOrderingAllowed = actionMenuOrderingAllowed;
    }

    public boolean isCountdownSmRefreshAllowed() {
        return countdownSmRefreshAllowed;
    }

    public void setCountdownSmRefreshAllowed(boolean countdownSmRefreshAllowed) {
        this.countdownSmRefreshAllowed = countdownSmRefreshAllowed;
    }

    public String getActionMenuOrdering() {
        return actionMenuOrdering;
    }

    public void setActionMenuOrdering(String actionMenuOrdering) {
        this.actionMenuOrdering = actionMenuOrdering;
    }

    public boolean isDisplayQueryDateRangeOnReports() {
        return displayQueryDateRangeOnReports;
    }

    public void setDisplayQueryDateRangeOnReports(boolean displayQueryDateRangeOnReports) {
        this.displayQueryDateRangeOnReports = displayQueryDateRangeOnReports;
    }

    public boolean isAoiAreaColoured() {
        return aoiAreaColoured;
    }

    public void setAoiAreaColoured(boolean aoiAreaColoured) {
        this.aoiAreaColoured = aoiAreaColoured;
    }

    public boolean isHoverOverAoiAllowed() {
        return hoverOverAoiAllowed;
    }

    public void setHoverOverAoiAllowed(boolean hoverOverAoiAllowed) {
        this.hoverOverAoiAllowed = hoverOverAoiAllowed;
    }

    public boolean isHoverOverPoiAllowed() {
        return hoverOverPoiAllowed;
    }

    public void setHoverOverPoiAllowed(boolean hoverOverPoiAllowed) {
        this.hoverOverPoiAllowed = hoverOverPoiAllowed;
    }

    public boolean isChoosePoiAreaColour() {
        return choosePoiAreaColour;
    }

    public void setChoosePoiAreaColour(boolean choosePoiAreaColour) {
        this.choosePoiAreaColour = choosePoiAreaColour;
    }

    public boolean isTflDisruptionsAllowed() {
        return tflDisruptionsAllowed;
    }

    public void setTflDisruptionsAllowed(boolean tflDisruptionsAllowed) {
        this.tflDisruptionsAllowed = tflDisruptionsAllowed;
    }

    public boolean isTflCameraAllowed() {
        return tflCameraAllowed;
    }

    public void setTflCameraAllowed(boolean tflCameraAllowed) {
        this.tflCameraAllowed = tflCameraAllowed;
    }

    public boolean isRefreshWholeBrowserAllowed() {
        return refreshWholeBrowserAllowed;
    }

    public void setRefreshWholeBrowserAllowed(boolean refreshWholeBrowserAllowed) {
        this.refreshWholeBrowserAllowed = refreshWholeBrowserAllowed;
    }

    public boolean isSevenDigitalPostcodeAllowed() {
        return sevenDigitalPostcodeAllowed;
    }

    public void setSevenDigitalPostcodeAllowed(boolean sevenDigitalPostcodeAllowed) {
        this.sevenDigitalPostcodeAllowed = sevenDigitalPostcodeAllowed;
    }

    public boolean isLocationBasedDataRegardlessConfidence() {
        return locationBasedDataRegardlessConfidence;
    }

    public void setLocationBasedDataRegardlessConfidence(boolean locationBasedDataRegardlessConfidence) {
        this.locationBasedDataRegardlessConfidence = locationBasedDataRegardlessConfidence;
    }

    public boolean isHideWarningAsterisks() {
        return hideWarningAsterisks;
    }

    public void setHideWarningAsterisks(boolean hideWarningAsterisks) {
        this.hideWarningAsterisks = hideWarningAsterisks;
    }

    public boolean isChooseAoiAreaColour() {
        return chooseAoiAreaColour;
    }

    public void setChooseAoiAreaColour(boolean chooseAoiAreaColour) {
        this.chooseAoiAreaColour = chooseAoiAreaColour;
    }

    public boolean isZeroTodaysCostAtMidnight() {
        return zeroTodaysCostAtMidnight;
    }

    public void setZeroTodaysCostAtMidnight(boolean zeroTodaysCostAtMidnight) {
        this.zeroTodaysCostAtMidnight = zeroTodaysCostAtMidnight;
    }

    public boolean isSatelliteDriftOverwrite() {
        return satelliteDriftOverwrite;
    }

    public void setSatelliteDriftOverwrite(boolean satelliteDriftOverwrite) {
        this.satelliteDriftOverwrite = satelliteDriftOverwrite;
    }

    public boolean isBackToTheFutureModifiedAllowed() {
        return backToTheFutureModifiedAllowed;
    }

    public void setBackToTheFutureModifiedAllowed(boolean backToTheFutureModifiedAllowed) {
        this.backToTheFutureModifiedAllowed = backToTheFutureModifiedAllowed;
    }

    public boolean isHandheldClusteringAllowed() {
        return handheldClusteringAllowed;
    }

    public void setHandheldClusteringAllowed(boolean handheldClusteringAllowed) {
        this.handheldClusteringAllowed = handheldClusteringAllowed;
    }

    public boolean isSplitShowPoiAoiCheckbox() {
        return splitShowPoiAoiCheckbox;
    }

    public void setSplitShowPoiAoiCheckbox(boolean splitShowPoiAoiCheckbox) {
        this.splitShowPoiAoiCheckbox = splitShowPoiAoiCheckbox;
    }


    public boolean isShowPoiAreaInPoiManager() {
        return showPoiAreaInPoiManager;
    }

    public void setShowPoiAreaInPoiManager(boolean showPoiAreaInPoiManager) {
        this.showPoiAreaInPoiManager = showPoiAreaInPoiManager;
    }

    public boolean isRetainUserSelectionAllowedPoi() {
        return retainUserSelectionAllowedPoi;
    }

    public void setRetainUserSelectionAllowedPoi(boolean retainUserSelectionPoiAllowed) {
        this.retainUserSelectionAllowedPoi = retainUserSelectionPoiAllowed;
    }

    public boolean isShowStopTimeAlternateJourneyAllowed() {
        return showStopTimeAlternateJourneyAllowed;
    }

    public void setShowStopTimeAlternateJourneyAllowed(boolean showStopTimeAlternateJourneyAllowed) {
        this.showStopTimeAlternateJourneyAllowed = showStopTimeAlternateJourneyAllowed;
    }

    public boolean isRetainUserSelectionAllowedAoi() {
        return retainUserSelectionAllowedAoi;
    }

    public void setRetainUserSelectionAllowedAoi(boolean retainUserSelectionAoiAllowed) {
        this.retainUserSelectionAllowedAoi = retainUserSelectionAoiAllowed;
    }


    public boolean isRefreshSmRateFrom10Allowed() {
        return refreshSmRateFrom10Allowed;
    }

    public void setRefreshSmRateFrom10Allowed(boolean refreshSmRateFrom10Allowed) {
        this.refreshSmRateFrom10Allowed = refreshSmRateFrom10Allowed;
    }

    public boolean isDriverIdPermitted() {
        return driverIdPermitted;
    }

    public void setDriverIdPermitted(boolean driverIdPermitted) {
        this.driverIdPermitted = driverIdPermitted;
    }

    public boolean isGeoFenceConfidenceMsgsCountEnterAllowed() {
        return geoFenceConfidenceMsgsCountEnterAllowed;
    }

    public void setGeoFenceConfidenceMsgsCountEnterAllowed(boolean geoFenceConfidenceMsgsCountEnterAllowed) {
        this.geoFenceConfidenceMsgsCountEnterAllowed = geoFenceConfidenceMsgsCountEnterAllowed;
    }

    public boolean isGeoFenceConfidenceMsgsCountExitAllowed() {
        return geoFenceConfidenceMsgsCountExitAllowed;
    }

    public void setGeoFenceConfidenceMsgsCountExitAllowed(boolean geoFenceConfidenceMsgsCountExitAllowed) {
        this.geoFenceConfidenceMsgsCountExitAllowed = geoFenceConfidenceMsgsCountExitAllowed;
    }

    public boolean isGeoFenceConfidenceMsgsOverrideIgnOffAllowed() {
        return geoFenceConfidenceMsgsOverrideIgnOffAllowed;
    }

    public void setGeoFenceConfidenceMsgsOverrideIgnOffAllowed(boolean geoFenceConfidenceMsgsOverrideIgnOffAllowed) {
        this.geoFenceConfidenceMsgsOverrideIgnOffAllowed = geoFenceConfidenceMsgsOverrideIgnOffAllowed;
    }

    public int getGeoFenceConfidenceMsgsCountEnter() {
        return geoFenceConfidenceMsgsCountEnter;
    }

    public void setGeoFenceConfidenceMsgsCountEnter(int geoFenceConfidenceMsgsCountEnter) {
        this.geoFenceConfidenceMsgsCountEnter = geoFenceConfidenceMsgsCountEnter;
    }

    public int getGeoFenceConfidenceMsgsCountExit() {
        return geoFenceConfidenceMsgsCountExit;
    }

    public void setGeoFenceConfidenceMsgsCountExit(int geoFenceConfidenceMsgsCountExit) {
        this.geoFenceConfidenceMsgsCountExit = geoFenceConfidenceMsgsCountExit;
    }

    public boolean isDrivingTimePanelAllowed() {
        return drivingTimePanelAllowed;
    }

    public void setDrivingTimePanelAllowed(boolean drivingTimePanelAllowed) {
        this.drivingTimePanelAllowed = drivingTimePanelAllowed;
    }

    public boolean isRetainDriverNameAfterIgnitionOff() {
        return retainDriverNameAfterIgnitionOff;
    }

    public void setRetainDriverNameAfterIgnitionOff(boolean retainDriverNameAfterIgnitionOff) {
        this.retainDriverNameAfterIgnitionOff = retainDriverNameAfterIgnitionOff;
    }

    public boolean isVinSearchAllowed() {
        return vinSearchAllowed;
    }

    public void setVinSearchAllowed(boolean vinSearchAllowed) {
        this.vinSearchAllowed = vinSearchAllowed;
    }

    public boolean isUseIpFilter() {
        return useIpFilter;
    }

    public void setUseIpFilter(boolean useIpFilter) {
        this.useIpFilter = useIpFilter;
    }

    public boolean isiOSApplicationAllowed() {
        return iOSApplicationAllowed;
    }

    public void setiOSApplicationAllowed(boolean iOSApplicationAllowed) {
        this.iOSApplicationAllowed = iOSApplicationAllowed;
    }

    public boolean isNetworkVehiclesAllowed() {
        return networkVehiclesAllowed;
    }

    public void setNetworkVehiclesAllowed(boolean networkVehiclesAllowed) {
        this.networkVehiclesAllowed = networkVehiclesAllowed;
    }
}