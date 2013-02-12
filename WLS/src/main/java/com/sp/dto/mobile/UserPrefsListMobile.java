package com.sp.dto.mobile;

import com.sp.dto.UserPrefsDto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg
 * Date: 27.08.12
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name="vehicles")
public class UserPrefsListMobile extends MobileSerializableList {
    private UserPrefsDto userPreferences;

    public UserPrefsDto getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(UserPrefsDto userPreferences) {
        this.userPreferences = userPreferences;
    }

    public UserPrefsListMobile(UserPrefsDto userPreferences) {

        this.userPreferences = userPreferences;
    }
}
