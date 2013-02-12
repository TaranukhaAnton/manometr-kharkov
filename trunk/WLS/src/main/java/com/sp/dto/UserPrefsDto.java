package com.sp.dto;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 09.11.12
 * Time: 15:08
 * To change this template use File | Settings | File Templates.
 */
public class UserPrefsDto {
    private boolean isAdmin;
    private String userSpeedMetrics;
    private Integer lastViewedGroupID;

    public Integer getLastViewedGroupID() {
        return lastViewedGroupID;
    }

    public void setLastViewedGroupID(Integer lastViewedGroupID) {
        this.lastViewedGroupID = lastViewedGroupID;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    public UserPrefsDto() {
    }

    public String getUserSpeedMetrics() {
        return userSpeedMetrics;
    }

    public void setUserSpeedMetrics(String userSpeedMetrics) {
        this.userSpeedMetrics = userSpeedMetrics;
    }

    public UserPrefsDto(boolean admin, String userSpeedMetrics, Integer lastViewedGroupID) {
        this.isAdmin = admin;
        this.userSpeedMetrics = userSpeedMetrics;
        this.lastViewedGroupID = lastViewedGroupID;
    }
}
