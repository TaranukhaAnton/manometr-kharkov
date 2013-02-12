package com.sp.util;

import com.sp.dto.VehicleDto;
import com.sp.model.*;
import org.apache.commons.lang3.text.FormattableUtils;

import javax.faces.model.SelectItem;
import java.util.*;

public class Util {

    public static String PERM_ACTION_READ ="";
    private static Properties networkVehiclesProperties;
    public static final String PERM_ACTION_WRITE = "";
    public static final String PERM_ACTION_DELETE_ARCHIVE = "";
    private static boolean flexRequest;
    private static long calampUnplaggedDelayTime;
    private static float distanceMetricFactor;
    public static final String[] WEEK_DAYS = null;

    public static Properties getNetworkVehiclesProperties() {
        return networkVehiclesProperties;
    }

    public static Collection<SelectItem> modelsToSelectItems(List<Vehicle> res) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }


    public static void checkPermission(String s, String d){}

    public static List<Integer> modelsToIds(List<VehicleDto> nowadaysVehicleList) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static String getJoinedStrs(String streetName, String postcode) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static boolean isNullOrEmptyStr(CharSequence digitalInput1Name) {
        return true;
    }

    public static boolean isUnassignedGroup(UnitView oldObj) {
        return false;  //To change body of created methods use File | Settings | File Templates.
    }

    public static boolean isModelInCollection(Vehicle vehicle, Set<Vehicle> groupVehicles) {
        return false;  //To change body of created methods use File | Settings | File Templates.
    }

    public static List<UnitView> addModelInListIfNotExists(UnitView unitView, List<UnitView> allGroupsByVehicle) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static List<UnitView> removeModelFromListIfExists(UnitView deletedUnitView, List<UnitView> allGroupsByVehicle) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static boolean isFlexRequest() {
        return flexRequest;
    }

    public static long getCalampUnplaggedDelayTime() {
        return calampUnplaggedDelayTime;
    }

    public static String joinListByComma(List<String> strings) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static Date getEndOfDay(java.sql.Date end_date) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static Date parseDateByPattern(String deliveredDate, String s) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static void setDaylightTimeUK(VehicleIncomingLogRecord incomingLogRecord) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public static String angleToDirectionOfTravel(double direction) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static float getDistanceMetricFactor() {
        return distanceMetricFactor;
    }

    public static boolean isUnassignedGroup(BaseMovableItemView baseMovableItemView) {
        return false;  //To change body of created methods use File | Settings | File Templates.
    }

    public static String getTimeDelayStrAlternate(long idling, boolean b) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static String buildAoiPoiDescr(String poiDescr, String aoiDescr) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static String decimalFormatter(double v) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static float getFactoredSpeed(int curSpeed) {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }

    public static String angleToDirectionOfTravelFull(int curDirection) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static double getDistaceInMileByKM(double v) {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }

    public static Date min(Date endDate, Date createdDate) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static Date max(Date activationDate, Date createdDate) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static String getDaysFromDate(Date motDate) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static FormattableUtils getIgnitionStatus(boolean ignitionActive, double curSpeed) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static String getTimeDelayStr(long averageTimeOnSiteInSec) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static String getTimeDelayStrAlternateGrid(long diffInSeconds) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static double getAngle(double lon, double lat, double prevLon, double prevLat) {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }

    public static String getTotalDrivingTimeByJourneyList(List<Journey> total) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static String getTotalDrivingTimeByJourneyListAlternate(List<Journey> journeyList) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static long getStoppedTimeBetweenJourneys(List<Journey> journeyList) {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }

    public static long getTotalDrivingTimeByJourneyListLong(List<Journey> journeyList) {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }

    public static long differenceInDays(Date startDate, Date endDate) {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }

    public static String constructSqlINClause(List<Integer> unitIdList) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static Date getDaylightTimeUK(Date date, BaseTrackingDevice trackingDevice) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static String getHexString(byte[] data) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static boolean isCalampDevice(int boxTypeId) {
        return false;  //To change body of created methods use File | Settings | File Templates.
    }

    public static String getTimePeriodStr(long l) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static String getTotalDurationByInputRecordList(List<DigitalInputReportRecord> recordList) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static String getTimePeriodHoursMinutesColon(long l) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static double convertHourToDecimal(String totalTime) {
        return 0;  //To change body of created methods use File | Settings | File Templates.
    }

    public static Date getDaylightTimeUK(Date date, Handheld handheld) {
        return null;
    }

    public static boolean inDaylightTimeUK(Date startDate) {
        return false;  //To change body of created methods use File | Settings | File Templates.
    }
}
