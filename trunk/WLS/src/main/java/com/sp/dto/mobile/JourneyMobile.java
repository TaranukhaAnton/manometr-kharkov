package com.sp.dto.mobile;

import com.sp.model.Journey;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 18.10.12
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
public class JourneyMobile {

    private Date startDate;
    private Date endDate;
    private String startPOI;
    private String endPOI;
    private String startAdress;
    private String endAdress;
    private float distane;
    private long duration;
    private int idle;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdle() {
        return idle;
    }

    public void setIdle(int idle) {
        this.idle = idle;
    }

    public JourneyMobile(Journey journey) {
        journey.calculateFields();
        this.startDate = journey.getStartDate();
        this.endDate = journey.getEndDate();
        this.startPOI = journey.getStartAoiPoiDescr();
        this.endPOI = journey.getEndAoiPoiDescr();

        this.startAdress =
                (journey.getStartStreetName() == null ? "" : journey.getStartStreetName())
                        + " " +
                (journey.getStartPostcode() == null   ? "" : journey.getStartPostcode());

        this.endAdress =
                (journey.getEndStreetName() == null ? "" : journey.getEndStreetName())
                + " " +
                (journey.getEndPostcode() == null ? "" : journey.getEndPostcode());

        this.distane = journey.getDistance();
        this.duration = (journey.getEndDate().getTime() - journey.getStartDate().getTime()) / 1000;
        this.idle = journey.getIdling();
        this.id = journey.getId();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartPOI() {
        return startPOI;
    }

    public void setStartPOI(String startPOI) {
        this.startPOI = startPOI;
    }

    public String getEndPOI() {
        return endPOI;
    }

    public void setEndPOI(String endPOI) {
        this.endPOI = endPOI;
    }

    public String getStartAdress() {
        return startAdress;
    }

    public void setStartAdress(String startAdress) {
        this.startAdress = startAdress;
    }

    public String getEndAdress() {
        return endAdress;
    }

    public void setEndAdress(String endAdress) {
        this.endAdress = endAdress;
    }

    public float getDistane() {
        return distane;
    }

    public void setDistane(float distane) {
        this.distane = distane;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }



}
