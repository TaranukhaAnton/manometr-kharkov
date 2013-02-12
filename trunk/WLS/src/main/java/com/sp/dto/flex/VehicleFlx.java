package com.sp.dto.flex;

import com.sp.model.Journey;
import com.sp.model.Vehicle;
import com.sp.util.Util;
import org.apache.commons.lang.WordUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: paveliva
 * Date: 20.08.2009
 * Time: 13:23:18
 * To change this template use File | Settings | File Templates.
 */
public class VehicleFlx {
    private Integer id;
    private String registrationNumber;
    private String typeImgFileName;

    private String typeDescr;
    private String fleetId;
    private String cabPhone = "n/a";
    private String regularDriver;
    private int pencePerMile;
    private String vin;
    private String description;
    private int currentSpeed;
    private String currentDirection;
    private String ignitionStatus;
    private Date lastMessage;
    private String lastLocation;

    private String drivingTime;
    private int numberOfStops = 0;
    private String stopTime;
    private String longestJourneyTime;
    private int longestJourneyDistance;
    private String avgJourneyTime;
    private float avgJourneyDistance;

    private int drivingDistance;
    private int numberOfJourneys;
    private List<Journey> periodJourney;

    public VehicleFlx(Vehicle v, List<Journey> js) {
        this.id = v.getId();
        this.registrationNumber = v.getRegistrationNumber();
        this.typeImgFileName = v.getType().getImageFileName();
        this.currentSpeed = v.getCurSpeed();
        this.currentDirection = v.getDirectionOfTravelUpperCase();
        this.ignitionStatus = WordUtils.capitalize(v.getIgnitionLabel().toLowerCase());
        this.lastMessage = v.getRenewalDate();
        this.lastLocation = v.getCurStreet();
        this.vin = v.getVin();
        this.description = v.getDescr();
        this.typeDescr = v.getType().getDescr();
        this.fleetId = v.getFleetId();
        if (v.getCabPhone() != null && !v.getCabPhone().isEmpty()) {
            this.cabPhone = v.getCabPhone();
        }
        this.regularDriver = v.getRegDriverFullName();
        this.pencePerMile = v.getCostPerMile();

        this.numberOfJourneys = js.size();

        long aDrivingTime = 0;
        long aStopTime = 0;
        long aLongestJourneyTime = 0;
        float aLongestJourneyDistance = 0;

        float distanceTotal = 0;
        long jTime = 0;
        Journey previousJourney = null;
        for (Journey j : js) {
            distanceTotal += j.getFactoredDistance();
            if (aLongestJourneyDistance < j.getFactoredDistance()) {
                aLongestJourneyDistance = j.getFactoredDistance();
            }
            jTime = ((j.getEndDate() == null) ?
                    new Date().getTime() : j.getEndDate().getTime())
                    - j.getStartDate().getTime(); //time of journey
            //2. Add Journey Time to Stop Time where distance = 0
            if (j.getFactoredDistance() == 0) {
                aStopTime += jTime;
            }            
            aDrivingTime += jTime;
            if (aLongestJourneyTime < jTime) {
                aLongestJourneyTime = jTime;
            }
            //1. Do not count journey as Stops where distance of journey = 0;
            if (j.getEndLogId() != null
                    && j.getEndLogId() > 0
                    && j.getFactoredDistance() == 0) {//journey ended
                this.numberOfStops++;
            }
            // "Stop Time" is the time between two  journeys
            // (Journey End of previous journey and Journey Start of next journey).
            if (previousJourney != null
                    && previousJourney.getEndLogId() != null
                    && previousJourney.getEndLogId() > 0
                    && previousJourney.getEndDate() != null) {
                aStopTime += (j.getStartDate().getTime() - previousJourney.getEndDate().getTime());
            }
            previousJourney = j;
        }
        this.drivingDistance = Math.round(distanceTotal);
        this.avgJourneyDistance = (numberOfJourneys > 0)?distanceTotal / numberOfJourneys:0;
        this.avgJourneyTime = Util.getTimePeriodStr((numberOfJourneys > 0) ? (Math.round((double) aDrivingTime / numberOfJourneys)) : 0);
        this.longestJourneyDistance = Math.round(aLongestJourneyDistance);
        this.longestJourneyTime = Util.getTimePeriodStr(aLongestJourneyTime);
        this.drivingTime = Util.getTimePeriodStr(aDrivingTime);
        this.stopTime = Util.getTimePeriodStr(aStopTime);
        this.periodJourney = js;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getTypeImgFileName() {
        return typeImgFileName;
    }

    public void setTypeImgFileName(String imgFileName) {
        this.typeImgFileName = imgFileName;
    }

    public int getDrivingDistance() {
        return drivingDistance;
    }

    public void setDrivingDistance(int drivingDistance) {
        this.drivingDistance = drivingDistance;
    }

    public int getNumberOfJourneys() {
        return numberOfJourneys;
    }

    public void setNumberOfJourneys(int numberOfJourneys) {
        this.numberOfJourneys = numberOfJourneys;
    }

    public List<Journey> getPeriodJourney() {
        return periodJourney;
    }

    public void setPeriodJourney(List<Journey> periodJourney) {
        this.periodJourney = periodJourney;
    }

    public float getAvgJourneyDistance() {
        return avgJourneyDistance;
    }

    public void setAvgJourneyDistance(float avgJourneyDistance) {
        this.avgJourneyDistance = avgJourneyDistance;
    }

    public String getTypeDescr() {
        return typeDescr;
    }

    public void setTypeDescr(String typeDescr) {
        this.typeDescr = typeDescr;
    }

    public String getFleetId() {
        return fleetId;
    }

    public void setFleetId(String fleetId) {
        this.fleetId = fleetId;
    }

    public String getCabPhone() {
        return cabPhone;
    }

    public void setCabPhone(String cabPhone) {
        this.cabPhone = cabPhone;
    }

    public String getRegularDriver() {
        return regularDriver;
    }

    public void setRegularDriver(String regularDriver) {
        this.regularDriver = regularDriver;
    }

    public int getPencePerMile() {
        return pencePerMile;
    }

    public void setPencePerMile(int pencePerMile) {
        this.pencePerMile = pencePerMile;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public String getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(String currentDirection) {
        this.currentDirection = currentDirection;
    }

    public String getIgnitionStatus() {
        return ignitionStatus;
    }

    public void setIgnitionStatus(String ignitionStatus) {
        this.ignitionStatus = ignitionStatus;
    }

    public Date getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Date lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }

    public String getDrivingTime() {
        return drivingTime;
    }

    public void setDrivingTime(String drivingTime) {
        this.drivingTime = drivingTime;
    }

    public int getNumberOfStops() {
        return numberOfStops;
    }

    public void setNumberOfStops(int numberOfStops) {
        this.numberOfStops = numberOfStops;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getLongestJourneyTime() {
        return longestJourneyTime;
    }

    public void setLongestJourneyTime(String longestJourneyTime) {
        this.longestJourneyTime = longestJourneyTime;
    }

    public int getLongestJourneyDistance() {
        return longestJourneyDistance;
    }

    public void setLongestJourneyDistance(int longestJourneyDistance) {
        this.longestJourneyDistance = longestJourneyDistance;
    }

    public String getAvgJourneyTime() {
        return avgJourneyTime;
    }

    public void setAvgJourneyTime(String avgJourneyTime) {
        this.avgJourneyTime = avgJourneyTime;
    }
}
