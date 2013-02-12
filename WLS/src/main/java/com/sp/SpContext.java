package com.sp;

import com.sp.model.BaseTrackingDevice;
import com.sp.model.BaseVehicle;
import com.sp.model.UserPrefs;
import com.sp.security.UserDetailsInfo;

/**
 * User: andrew
 * Date: 13.04.2010
 */
public class SpContext {

    private static UserPrefs userPrefs;

    public static UserDetailsInfo getUserDetailsInfo() {
        return null;
    }

    public static UserPrefs getUserPrefs() {
        return userPrefs;
    }

    public void init() {


    }

    public static Long getCachedImmobilizationNumberByVehicleId(int id) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static BaseTrackingDevice getCachedDeviceByVehicleId(int id) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static BaseVehicle getCachedVehicleById(int vehicleId) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}
