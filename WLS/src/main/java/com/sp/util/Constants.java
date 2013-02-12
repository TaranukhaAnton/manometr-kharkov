package com.sp.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 25.03.2009
 * Time: 18:38:30
 * To change this template use File | Settings | File Templates.
 */
public class Constants {
    public static final short CAN_TRANS_REASON_IGNITION_ON = 69;
    public static final short CAN_TRANS_REASON_IGNITION_OFF = 53;

    public static enum IgnitionStatus { None, On, Off, Idling, Unplugged}
    public static enum OBJECT_TYPE_NAME_SELECTED {vehicle,phone,poi,assets,handheld,aoi}
    public static enum NODES_TYPE_NAME {PhoneView,PoiGroup,AssetsView,HandheldView,UnitView,AoiView,Account,Vehicle}
    public static enum DAYS_OF_WEEK {Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday}
    public static enum GEOZONES_TYPES {poi,aoi}
    public static enum TotalTypes {None, Sum, Avg}
    public static enum ImmobilisationResult {Failed,Success,InPogress}
    public static List<String> DAYS_OF_WEEK_LIST = new ArrayList<String>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

    public static enum IncidentType { Camera,Disruption}

    public static List<Integer> FM2_BOX_TYPE_ID_LIST = new ArrayList<Integer>(Arrays.asList(7,16));
    public static List<Integer> FM4_BOX_TYPE_ID_LIST = new ArrayList<Integer>(Arrays.asList(17,18));
    public static List<Integer> FM_BOX_TYPE_ID_LIST = new ArrayList<Integer>(Arrays.asList(7,16,17,18));
    public static int FM4200_BOX_TYPE_ID = 18;
    public static int FM4100_BOX_TYPE_ID = 17;
    public static int FM11_BOX_TYPE_ID = 19;
    public static int CALAMP_3000_BOX_TYPE_ID = 20;
    public static int CALAMP_2610_BOX_TYPE_ID = 21;
    public static int CALAMP_2000_BOX_TYPE_ID = 22;
    public static int T6_LITE_BOX_TYPE_ID = 3;
    public static int T6_PREMIUM_BOX_TYPE_ID = 4;
    public static int T8_LITE_BOX_TYPE_ID = 5;
    public static int T8_PREMIUM_BOX_TYPE_ID = 6;
    public static int AT100_BOX_TYPE_ID = 1;
    public static int CELLOCATOR_BOX_TYPE_ID = 2;
    public static int HH_DT1_BOX_TYPE_ID = 3;
    public static int HH_GH1202_BOX_TYPE_ID = 1;
    public static int HH_GH3000_BOX_TYPE_ID = 2;


    public static final String[] WEEK_DAYS_LONG = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public static enum ALL_BAR_ITEMS_TYPES {nameSelectionBarItem,poiSelectionBarItem,vehicleSelectionBarItem,phoneSelectionBarItem,recipientSelectionBarItem}
    public static enum ALL_MAPPING_PAGE_TYPES {FLEET,DUAL,MAP} 
    public static enum ALL_PAGE_TYPES_LEFT_MENU_ACTION {map,list,nothing}
    public static enum LEFT_MENU_TREE_TYPE {notExpired,all}
    public static final int SNAIL_TRAIL_TABLE_ROWS_COUNT = 11;
    public static final int DIAGNOSTIC_HEALTH_TABLE_ROWS_COUNT = 20;
    public static final int TODAYS_JOURNEY_TABLE_ROWS_COUNT = 5;
    public static final int TJ_SNAIL_TRAIL_TABLE_ROWS_COUNT = 11;    
    public static final int MILLISECOND_IN_WEEK = 1000 * 60 * 60 * 24 * 7;
    public static final String TEST_IMEI_PREFIX = "test_imei_";
    public static final String SAFETY_IMEI_PREFIX = "safety_";
    public static final String PERMISSION_TREE = "permissionTree";
//    public static final String BOX_TYPE_FM_2200_DESCR = "FM2200";
//    public static final String BOX_TYPE_FM_2100_DESCR = "FM2100";


    public static final String IGNITION_STATUS_VISIBILITY = "Ignition Status Visibility";
    public static final String TODAYS_JOURNEY_REPORT_SUMMARY = "Today's Journey Report Summary";
    public static final String TODAYS_JOURNEY_FULL_DAY = "Today's Journeys - Full Day";
    public static final String TODAYS_COST = "Today's Cost";
    public static final String COST_PER_MILE = "Cost Per Mile";
    public static final String COST_PER_HOUR = "Cost Per Hour";
    public static final String ALERT_MANAGER = "Geo-fence Alert";
    public static final String UNIT_HEALTH_CHECK = "Unit Health Check";
    public static final String TIME_ON_SITE_REP_PERM_DESCR = "Time On Site Report";
    public static final String TIME_ON_SITE_FLEX_REP_PERM_DESCR = "Time On Site Flex Report";
    public static final String LATE_START_PERM_DESCR = "Late Start Report";
    public static final String LATE_START_FLEX_PERM_DESCR = "Late Start Flex Report";
    public static final String ANCILLARY_FLEX_REPORT = "Ancillary Flex Report";

    public static final String ROLE_GEO_FENCE_VEHICLE = "Geo-fence Unit";
    public static final String ROLE_GEO_FENCE_PHONE = "Geo-fence Phone";

    public static final String PERM_ACTION_READ = "Read";
    public static final String PERM_ACTION_WRITE = "Write";
    public static final String PERM_ACTION_DELETE_ARCHIVE = "Delete/Archive";
    public static final String PERM_ACTION_CREATE = "Create";
    public static final String PERM_ACTION_EDIT = "Edit";
    public static final String PERM_ACTION_MANAGE = "Manage";
    public static final String PERM_ACTION_SCHEDULE = "Schedule";
    public static final String PERM_ACTION_SEND_NOW = "SendNow";


    public static final double KM_IN_METER = 0.001;
    public static final double MILES_IN_METER = 0.00062137;
    public static final double SPEED_MS_TO_KMPH = 3.6;
    public static final int PENCES_IN_POUND = 100;
    public static final int DEFAULT_SCHEDULE_ID = -1;
    public static final long UNDEFINED_ITEM_ID = -1;
    public static final String PHONE_PERM_DESCR = "Phone";
    public static final String IMEI_PERM_DESCR = "Tracking Device";
    public static final String PHONE_GROUPS_PERM_DESCR = "Phone-Groups";
    public static final String PHONE_ACCOUNTS_PERM_DESCR = "Phone-Accounts";
    public static final String HANDHELD_PERM_DESCR = "Handheld";
    public static final String HANDHELD_GROUPS_PERM_DESCR = "Handheld Groups";
    public static final String HANDHELD_ACCOUNTS_PERM_DESCR = "Handheld Accounts";
    public static final String UNIT_PERM_DESCR = "Unit";
    public static final String UNIT_GROUPS_PERM_DESCR = "Unit-Groups";
    public static final String UNIT_ACCOUNTS_PERM_DESCR = "Unit-Accounts";
    public static final String EVENT_REPORT_PERM_DESCR = "Event Report";
    public static final String TIME_SHEET_REPORT_FLEX_PERM_DESCR = "Time Sheet Report Flex";
    
    public static final String LICENCES_PERM_DESCR = "Licences";
    public static final String SIMS_PERM_DESCR = "SIMs";
    public static final String VEHICLE_TRACKING_DEVICES_PERM_DESCR = "Vehicle-TrackingDevices";
    public static final String MAINTAINS_PERM_DESCR = "Maintains";
    public static final String ANCILLARY_DEVICE_PERM_DESCR = "Ancillary Device";
    public static final String ACCOUNT_PERM_DESCR = "Account";
    public static final String POI_PERM_DESCR = "Poi";
    public static final String AOI_PERM_DESCR = "Aoi";
    public static final String INFORMATION_PERM_DESCR = "Information";
    public static final String SHOW_HIDE_POIS_FEATURE_PERM_DESCR = "Show/Hide POIs Feature";
    public static final String POI_FLEX_PERM_DESCR = "Poi Flex";
    public static final String AOI_FLEX_PERM_DESCR = "Aoi Flex";
    public static final String STEALTH_MODE_PREM_DESCR = "Stealth Mode";
    public static final String REP_CAN_EVENT_REPORT = "Can Event Report";

    public static final String MAP_TYPE_MSVE = "MSVE";
    public static final String MAP_TYPE_GOOGLEMAPS = "GOOGLEMAPS";
    public static final String MAP_TYPE_GEOSERVER = "GEOSERVER";

    public static final String REPORT_POSTFIX = " Report";
    public static final String FLEX_REPORT_POSTFIX = " Flex Report";

    public static enum ReportType  // rep rype = permission name
    {
    	MonthlyDevice("Monthly Device"), TimeSheet("Time Sheet"), Mileage("Mileage"), TrackingMaster("Tracking Master"),
        Journey("Journey"), JourneyCost("Journey Cost"), JourneyDetailed("Journey Detailed"),
        Overspeed("Overspeed"), BusinessPrivate("Business/Private"), TimeOnSite("Time On Site"), Idling("Idling"),
        LateStart("Late Start"), Ancillary("Ancillary"), DigitalInput("Digital Input"),
        DigitalInputOnly("Digital Input Only"),Utilisation("Utilisation"),EmailReports("Email Reports"),
        Daily("Daily"),NonJourney("Non Journey"),DiagnosticHistory("Diagnostic History"), DriverBehaviour("Driver Behaviour"),
        Speeding("Speeding"),Out_Of_Hours("Out_Of_Hours"),Fleet("Fleet"),LeagueTable("League Table"),DetailedReport("Detailed Report"),NetworkVehicles("NetworkVehicles");
        private final String name;
        private static List<ReportType> lockedVehiclesReport=null;

        static{
            lockedVehiclesReport = new ArrayList<ReportType>(2);
            lockedVehiclesReport.add(Speeding);
            lockedVehiclesReport.add(DriverBehaviour);
        }

        public String getPermission() {
            return name;
        }

        public String toString(){
            return name.replaceAll(" ","").replaceAll("/","");
        }

        public String toStringWithUnderscore(){
            return name.replaceAll(" ","_");
        }

        private ReportType(String name) {
            this.name = name;
        }

        public static List<ReportType> lockedVehiclesReportList(){
            return lockedVehiclesReport;
        }

    }

    public static enum RepFormat {
        HTML, PDF, XLS
    }
    
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_POWER_USER = "ROLE_POWER_USER";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_DASHBOARD_ALLOWED = "ROLE_DASHBOARD_ALLOWED";
    public static final String ROLE_SCHEDULED_REPORTS_READ = "ROLE_SCHEDULED_REPORTS_READ";
    public static final String ROLE_SEND_NOW_REPORTS_READ = "ROLE_SEND_NOW_REPORTS_READ";
    public static final String ROLE_CAN_EVENT_REPORT_CONFIGURATOR_ALLOWED = "ROLE_CAN_EVENT_REPORT_CONFIGURATOR_ALLOWED";
    public static final String ROLE_REPLICATION_MANAGE = "ROLE_REPLICATION_MANAGE";
    public static final String ROLE_NETWORK_MOVEMENTS = "ROLE_NETWORK_MOVEMENTS_VIEW_/_EDIT";
    public static final String ROLE_NETWORK_VEHICLES = "ROLE_NETWORK_VEHICLES_VIEW_/_EDIT";
    public static final String ROLE_NETWORK_LOCATIONS = "ROLE_NETWORK_LOCATIONS_VIEW_/_EDIT";
    public static final String PERM_DESCR_TIME_FILTER_JOURNEY = "Time Filter Journey Report";
    public static final String PERM_DESCR_TIME_FILTER_JOURNEY_COST = "Time Filter Journey Cost Report";
    
    public static final String ALERT_ONLY_ROLE_CAN_DO_IT = "Only admins and power users can do this";
    public static final String UNASSIGNED_GROUP = "Unassigned";
    public static final float KM_TO_MILES = 0.621371192f;
    public static final int CLOSEST_STREET_RADIUS = 100;
    public static final int CLOSEST_STREET_WITH_NAME_RADIUS = 500;
    //public static final int ADDED_HOURS_FOR_BST = 0;
    public static final int CLUSTERING_DISTANCE_BETWEEN_HH_PINS = 100;
    public static final String SMS_MESSAGING_HISTORY = "SMS Messaging History";
    public static final String VIEW_INBOUND_MESSAGES = "View Inbound Messages";
    public static final String EMAIL_REPORTS = "Email Reports";

    public static enum CAN_SENSORS_NAME {
        Odo("Odo"), Speed("Speed"), Fuel_Used("Fuel Used"), Fuel_Level("Fuel Level"),
        Engine_RPM("Engine RPM"), Brake("Brake"),
        Engine_Coolant_Temperature("Engine Coolant Temperature"), Clutch("Clutch"), Cruise_Control("Cruise Control"),
        Gas("Accel. Pedal Position"), Axle_Weight("Axle Weight"), Engine_Hours("Engine Hours"),
        Service_Distance("Service Distance"), TCO01("TCO01"), Overspeed("Overspeed"), Direction_Indicator("Direction Indicator"),
        Axle_Location("Axle Location"), Tyre_Location("Tyre Location"), Vehicle_ID("Vehicle ID"), SW_Version("SW Version"),
        Drive_Recognise("Drive Recognise"), Driver_1_Card("Driver 1 Card"), Driver_2_Card("Driver 2 Card"),
        Driv_1_Working_State("Driv. 1 Working State"), Driv_2_Working_State("Driv. 2 Working State"),
        Driv_1_Time_Rel_States("Driv. 1 time rel states"), Driv_2_Time_Rel_States("Driv. 2 time rel states"),
        Custom_Sensor_Name("Custom Sensor Name");
        private final String name;

        public String toString() {
            return name;
        }
        private CAN_SENSORS_NAME(String name) {
            this.name = name;
        }
    }

    public static enum COUNTRY {
        Ireland("Ireland"), GreatBritain("Great Britain");

        private final String name;

        public String toString() {
            return name;
        }
        private COUNTRY(String name) {
            this.name = name;
        }
    }

    public static enum CAN_SENSOR_FUNCTION {
        None, Count, Sum, Max, Min, Avg 
    }

    public static final int CAN_SENSOR_COUNT = 25;
    public static final SimpleDateFormat DATE_ONLY_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat DATE_ONLY_FORMAT_YEAR_STARTED = new SimpleDateFormat("yyyy.MM.dd");
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
    public static final String DRIVER_PERM = "Driver";

    public enum BoxTypes {
        avl,
        at100,
        t6
    }
}
