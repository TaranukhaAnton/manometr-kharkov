package com.sp.dto;

import com.sp.model.BaseTrackingDevice;

/**
 * User: andrey
 *
 * Maps node IDs to map-source and helps to understand which map-source should be used for which node ID
 */
public class DeviceMapSource extends BaseMapSource{

    private boolean stickyRoads;
    private boolean meaningfulAddressFixAllowed;
    private boolean geoserverRgTitleCase;
    private boolean useGeoserverRG;
    private BaseTrackingDevice trackingDevice;
    private boolean sevenDigitPostcodeForReseller;
    private int geoFenceConfidenceMsgsCountEnter;
    private int geoFenceConfidenceMsgsCountExit;
    private boolean geoFenceConfidenceMsgsOverrideIgnOffAllowed;

    public void setTrackingDevice(BaseTrackingDevice trackingDevice) {
        this.trackingDevice = trackingDevice;
    }

    public BaseTrackingDevice getTrackingDevice() {
        return trackingDevice;
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

    public boolean isGeoserverRgTitleCase() {
        return geoserverRgTitleCase;
    }

    public void setGeoserverRgTitleCase(boolean geoserverRgTitleCase) {
        this.geoserverRgTitleCase = geoserverRgTitleCase;
    }

    public boolean isUseGeoserverRG() {
        return useGeoserverRG;
    }

    public void setUseGeoserverRG(boolean useGeoserverRG) {
        this.useGeoserverRG = useGeoserverRG;
    }

    public boolean isSevenDigitPostcodeForReseller() {
        return sevenDigitPostcodeForReseller;
    }

    public void setSevenDigitPostcodeForReseller(boolean sevenDigitPostcodeForReseller) {
        this.sevenDigitPostcodeForReseller = sevenDigitPostcodeForReseller;
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

    public boolean isGeoFenceConfidenceMsgsOverrideIgnOffAllowed() {
        return geoFenceConfidenceMsgsOverrideIgnOffAllowed;
    }

    public void setGeoFenceConfidenceMsgsOverrideIgnOffAllowed(boolean geoFenceConfidenceMsgsOverrideIgnOffAllowed) {
        this.geoFenceConfidenceMsgsOverrideIgnOffAllowed = geoFenceConfidenceMsgsOverrideIgnOffAllowed;
    }

}
