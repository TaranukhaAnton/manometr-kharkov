package com.sp.dto;

import com.sp.model.BaseModel;
import com.sp.model.Handheld;
import com.sp.util.Util;

/**
 * Created by Alexander
 */


public class HandheldDto extends BaseModel {

       private String curAddress;
       private boolean selected;
       private float factoredSpeed;
       private long renewalDate;
       private double lat;
       private double lon;
       private boolean expanded;
       private String clientDescr;
       private String imageFileName;
       private String directionOfTravel;
       private String imei;
       private int hhBoxTypeId;


       public HandheldDto() {}

       public HandheldDto(Handheld handheld) {
           setId(handheld.getId());
           curAddress = handheld.getCurAddress();
           selected = handheld.isSelected();
           factoredSpeed = handheld.getFactoredSpeed();
           renewalDate = Util.getDaylightTimeUK(handheld.getRenewalDate(), handheld).getTime();
           lat = handheld.getLat();
           lon = handheld.getLon();
           expanded = handheld.isExpanded();
           clientDescr = handheld.getClientDescr();
           imageFileName = handheld.getType().getImageFileName();
           directionOfTravel = handheld.getDirectionOfTravelUpperCase();
           imei = handheld.getImei();
           hhBoxTypeId = handheld.getBoxType().getId();
       }

       public String getTypeDescr() {
           return "Handheld";
       }

        public int getActiveDinsCount() {
            return 0;
        }

        public void setActiveDinsCount(int i) {
        }
        
       public String getCurAddress() {
            return curAddress;
        }

        public void setCurAddress(String curAddress) {
            this.curAddress = curAddress;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public float getFactoredSpeed() {
            return factoredSpeed;
        }

        public void setFactoredSpeed(float factoredSpeed) {
            this.factoredSpeed = factoredSpeed;
        }

        public long getRenewalDate() { 
            return renewalDate;
        }

        public void setRenewalDate(long renewalDate) {
            this.renewalDate = renewalDate;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public boolean isExpanded() {
            return expanded;
        }

        public void setExpanded(boolean expanded) {
            this.expanded = expanded;
        }

        public String getClientDescr() {
            return clientDescr;
        }

        public void setClientDescr(String clientDescr) {
            this.clientDescr = clientDescr;
        }

        public String getImageFileName() {
            return imageFileName;
        }

        public void setImageFileName(String imageFileName) {
            this.imageFileName = imageFileName;
        }

        public String getDirectionOfTravel() {
            return directionOfTravel;
        }

        public void setDirectionOfTravel(String directionOfTravelUpperCase) {
            this.directionOfTravel = directionOfTravelUpperCase;
        }

        public String getDirectionOfTravelUpperCase() {
            return directionOfTravel.toUpperCase();
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

    public int getHhBoxTypeId() {
        return hhBoxTypeId;
    }

    public void setHhBoxTypeId(int hhBoxTypeId) {
        this.hhBoxTypeId = hhBoxTypeId;
    }
}
