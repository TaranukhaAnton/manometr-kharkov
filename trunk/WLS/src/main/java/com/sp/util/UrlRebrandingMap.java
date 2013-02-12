package com.sp.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class UrlRebrandingMap implements Map {
    private String rebrandingFolder;
    private Set<String> files;
    private String alertTemplate;
    private String reportTemplate;
    private String immediateReportTemplate;



    public UrlRebrandingMap(String rebrandingFolder, Set<String> files) {
        this(rebrandingFolder, files, null, null,null);
    }

    public UrlRebrandingMap(String rebrandingFolder,
                            Set<String> files,
                            String alertTemplate,
                            String reportTemplate,
                            String immediateReportTemplate) {
        this.rebrandingFolder = rebrandingFolder;
        this.files = files;
        this.alertTemplate = alertTemplate;
        this.reportTemplate = reportTemplate;
        this.immediateReportTemplate = immediateReportTemplate;
    }


    public UrlRebrandingMap() {
    }

    public int size() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
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
        return (files != null && files.contains(key) ? rebrandingFolder + "/" + key : key);
    }

    public Object put(Object key, Object value) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object remove(Object key) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void putAll(Map t) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void clear() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set keySet() {
        return files;
    }

    public Collection values() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set entrySet() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

  
    public String getAlertTemplate() {
        return alertTemplate;
    }

    public String getReportTemplate() {
        return reportTemplate;
    }

    public String getImmediateReportTemplate() {
        return immediateReportTemplate;
    }

}
