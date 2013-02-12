package com.sp.dto;

/**
 * Created by Alexander
 */
public class BaseMapSource {

    private int accountId;

    private String mapKey;

    private String mapPassword;

    private String mapSourceDescr;
    
    private boolean excludeEntityTypes;

    private boolean hideWarningAsterisks;

    public String getMapSourceDescr() {
        return mapSourceDescr;
    }

    public void setMapSourceDescr(String mapSourceDescr) {
        this.mapSourceDescr = mapSourceDescr;
    }

    public String getMapPassword() {
        return mapPassword;
    }

    public void setMapPassword(String mapPassword) {
        this.mapPassword = mapPassword;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public boolean isExcludeEntityTypes() {
        return excludeEntityTypes;
    }

    public void setExcludeEntityTypes(boolean excludeEntityTypes) {
        this.excludeEntityTypes = excludeEntityTypes;
    }

    public boolean isHideWarningAsterisks() {
        return hideWarningAsterisks;
    }

    public void setHideWarningAsterisks(boolean hideWarningAsterisks) {
        this.hideWarningAsterisks = hideWarningAsterisks;
    }

}
