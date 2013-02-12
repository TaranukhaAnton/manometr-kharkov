package com.sp.util;


import java.util.*;

/**
 * Created by Alexander
 */
public class HeaderReportsMap implements Map {
    private static Map<String,Boolean> reportsCategoryMap;
    private static Map<String,String> reportsLocalizationKeysMap;
    static {
        // there are reports ordered by default.
        // key spelling must be the same as in the "permissions" table
        reportsLocalizationKeysMap = new LinkedHashMap<String, String>();
        reportsLocalizationKeysMap.put("Journey Flex Report","Journey");
        reportsLocalizationKeysMap.put("Journey Cost Flex Report","JourneyCost");
        reportsLocalizationKeysMap.put("Journey Detailed Flex Report","JourneyDetailed");
        reportsLocalizationKeysMap.put("Journey Report","Journey");
        reportsLocalizationKeysMap.put("Non Journey Report","NonJourney");
        reportsLocalizationKeysMap.put("Journey Cost Report","JourneyCost");
        reportsLocalizationKeysMap.put("Journey Detailed Report","JourneyDetailed");
        reportsLocalizationKeysMap.put("Business/Private Flex Report","BusinessPrivate");
        reportsLocalizationKeysMap.put("Tracking Master Flex Report","TrackingMaster");
        reportsLocalizationKeysMap.put("Late Start Flex Report","LateStart");
        reportsLocalizationKeysMap.put("Late Start Report","LateStart");
        reportsLocalizationKeysMap.put("Overspeed Flex Report","Overspeed");
        reportsLocalizationKeysMap.put("Overspeed Report","Overspeed");
        reportsLocalizationKeysMap.put("Idling Flex Report","Idling");
        reportsLocalizationKeysMap.put("Idling Report","Idling");
        reportsLocalizationKeysMap.put("Time On Site Flex Report","TimeOnSite");
        reportsLocalizationKeysMap.put("Time On Site Report","TimeOnSite");
        reportsLocalizationKeysMap.put(Constants.EMAIL_REPORTS,"emailReports");
        reportsLocalizationKeysMap.put("Event Report","Event__Report");
        reportsLocalizationKeysMap.put("Time Sheet Report Flex","Time__Sheet");
        reportsLocalizationKeysMap.put("Ancillary Flex Report","Ancillary");
        reportsLocalizationKeysMap.put("Can Event Report","CanEventReport");
        reportsLocalizationKeysMap.put("Mileage Report","Mileage");
        reportsLocalizationKeysMap.put("Digital Input Report","Digital__Input__Report");
        reportsLocalizationKeysMap.put("Diagnostic History Report","DiagnosticHistory");
        reportsLocalizationKeysMap.put("Non-Reporting Report","Non__Reporting");
        reportsLocalizationKeysMap.put("Daily Report","Daily");
        reportsLocalizationKeysMap.put("Collector Log Report","Collector__Log__Report");
        reportsLocalizationKeysMap.put("Handheld Diagnostic History Report","Handheld__DiagnosticHistory");
        reportsLocalizationKeysMap.put("Incoming Log Statistic","IncomingLogStatistic");
        reportsLocalizationKeysMap.put("Location Report", "LocationReport");
        reportsLocalizationKeysMap.put("Driver Behaviour Flex Report", "DriverBehaviour");
        reportsLocalizationKeysMap.put("Speeding Flex Report", "Speeding");
        reportsLocalizationKeysMap.put("Out_Of_Hours Flex Report", "Out_Of_Hours");
        reportsLocalizationKeysMap.put("Driver Behaviour League Table Flex Report", "LeagueTable");
        reportsLocalizationKeysMap.put("Detailed Report", "DetailedReport");

        //----------------------
        // key spelling must be the same as in the "permissions" table
        reportsCategoryMap = new HashMap<String,Boolean>();  // permission name = is jsf report
        reportsCategoryMap.put("Journey Flex Report",false);
        reportsCategoryMap.put("Journey Cost Flex Report",false);
        reportsCategoryMap.put("Journey Detailed Flex Report",false);
        reportsCategoryMap.put("Journey Report",true);
        reportsCategoryMap.put("Non Journey Report",true);
        reportsCategoryMap.put("Journey Cost Report",true);
        reportsCategoryMap.put("Journey Detailed Report",true);
        reportsCategoryMap.put("Business/Private Flex Report",false);
        reportsCategoryMap.put("Tracking Master Flex Report",false);
        reportsCategoryMap.put("Late Start Flex Report",false);
        reportsCategoryMap.put("Late Start Report",true);
        reportsCategoryMap.put("Overspeed Flex Report",false);
        reportsCategoryMap.put("Overspeed Report",true);
        reportsCategoryMap.put("Idling Flex Report",false);
        reportsCategoryMap.put("Idling Report",true);
        reportsCategoryMap.put("Time On Site Flex Report",false);
        reportsCategoryMap.put("Time On Site Report",true);
        reportsCategoryMap.put(Constants.EMAIL_REPORTS,false);
        reportsCategoryMap.put("Event Report",false);
        reportsCategoryMap.put("Time Sheet Report Flex",false);
        reportsCategoryMap.put("Ancillary Flex Report",false);
        reportsCategoryMap.put("Can Event Report",false);
        reportsCategoryMap.put("Mileage Report",false);
        reportsCategoryMap.put("Digital Input Report",false);
        reportsCategoryMap.put("Diagnostic History Report",true);
        reportsCategoryMap.put("Non-Reporting Report",true);
        reportsCategoryMap.put("Daily Report",true);
        reportsCategoryMap.put("Collector Log Report",true);
        reportsCategoryMap.put("Handheld Diagnostic History Report",true);
        reportsCategoryMap.put("Incoming Log Statistic", true);
        reportsCategoryMap.put("Location Report", false);
        reportsCategoryMap.put("Driver Behaviour Flex Report", false);
        reportsCategoryMap.put("Speeding Flex Report", false);
        reportsCategoryMap.put("Out_Of_Hours Flex Report", false);
        reportsCategoryMap.put("Driver Behaviour League Table Flex Report", false);
        reportsCategoryMap.put("Detailed Report", false);
    }

    public static Map<String, String> getReportsLocalizationKeysMap() {
        return reportsLocalizationKeysMap;
    }

    private static Map<String, Boolean> getReportsCategoryMap() {
        return reportsCategoryMap;
    }

    public int size() {
        return getReportsLocalizationKeysMap().size();
    }

    public static boolean isJsfReport(String report){
        return getReportsCategoryMap().get(report);
    }

    public static String get(String key)  {
        return "" ;
    }

    

    public boolean isEmpty() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean containsKey(Object key) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean containsValue(Object value) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object get(Object key) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public Object put(Object key, Object value) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object remove(Object key) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void putAll(Map m) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void clear() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set keySet() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection values() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set entrySet() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
