package com.sp.model;

import com.sp.dto.flex.SnailTrailDeviceDto;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * User: andrew
 * Date: 11.10.2010
 */
@MappedSuperclass
public class LightVehicleIncomingLogRecord extends BaseIncomingLogRecord {
    @Column(name = "is_journey_start")
    private boolean journeyStart;

    @Column(name = "is_journey_end")
    private boolean journeyEnd;

    @Column(name = "direction_calc")
    private double directionCalc;

    @Column(name = "is_ignition_active")
    private boolean ignitionActive;

    @Column(name = "in_stealth_mode")
    private boolean inStealthMode;

    @Transient
    private SnailTrailDeviceDto snailTrailDeviceDto;


    private boolean input1High;
    private boolean input2High;
    private boolean input3High;
    private boolean input4High;
    private boolean input5High;
    private boolean input6High;

    public void copyTo(LightVehicleIncomingLogRecord copy) {
        super.copyTo(copy);
        copy.journeyStart = journeyStart;
        copy.journeyEnd = journeyEnd;
        copy.directionCalc = directionCalc;
        copy.ignitionActive = ignitionActive;
        copy.inStealthMode = inStealthMode;

        copy.input1High = input1High;
        copy.input2High = input2High;
        copy.input3High = input3High;
        copy.input4High = input4High;
        copy.input5High = input5High;
        copy.input6High = input6High;
    }

    public boolean isInput1High() {
        return input1High;
    }

    public void setInput1High(boolean input1High) {
        this.input1High = input1High;
    }

    public boolean isInput2High() {
        return input2High;
    }

    public void setInput2High(boolean input2High) {
        this.input2High = input2High;
    }

    public boolean isInput3High() {
        return input3High;
    }

    public void setInput3High(boolean input3High) {
        this.input3High = input3High;
    }

    public boolean isInput4High() {
        return input4High;
    }

    public void setInput4High(boolean input4High) {
        this.input4High = input4High;
    }

    public boolean isInput5High() {
        return input5High;
    }

    public void setInput5High(boolean input5High) {
        this.input5High = input5High;
    }

    public boolean isInput6High() {
        return input6High;
    }

    public void setInput6High(boolean input6High) {
        this.input6High = input6High;
    }

    public double getDirectionCalc() {
        return directionCalc;
    }

    public void setDirectionCalc(double directionCalc) {
        this.directionCalc = directionCalc;
    }

    public boolean isIgnitionActive() {
        return ignitionActive;
    }

    public void setIgnitionActive(boolean ignitionActive) {
        this.ignitionActive = ignitionActive;
    }

    public boolean isInStealthMode() {
        return inStealthMode;
    }

    public void setInStealthMode(boolean inStealthMode) {
        this.inStealthMode = inStealthMode;
    }

    public boolean isJourneyEnd() {
        return journeyEnd;
    }

    public void setJourneyEnd(boolean journeyEnd) {
        this.journeyEnd = journeyEnd;
    }

    public boolean isJourneyStart() {
        return journeyStart;
    }

    public void setJourneyStart(boolean journeyStart) {
        this.journeyStart = journeyStart;
    }

    public SnailTrailDeviceDto getSnailTrailDeviceDto() {
        return snailTrailDeviceDto;
    }

    public void setSnailTrailDeviceDto(SnailTrailDeviceDto snailTrailDeviceDto) {
        this.snailTrailDeviceDto = snailTrailDeviceDto;
    }
}
