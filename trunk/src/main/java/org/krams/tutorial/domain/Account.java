package org.krams.tutorial.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "account")
public class Account extends BaseAccount {
    @Column(name = "start_date")
    private Date startDate;

    private String notes;

    @Column(name = "primary_email")
    private String primaryEmail;

    @Column(name = "secondary_email")
    private String secondaryEmail;

    @Column(name = "is_tfl_disruptions_allowed")
    private boolean tflDisruptionsAllowed;

    @Column(name = "is_tfl_camera_allowed")
    private boolean tflCameraAllowed;

    @Column(name = "is_set_account_image")
    private boolean setAccountImage;

    @Column(name = "refresh_sm_seconds")
    private int refreshSmSeconds;

    @Column(name = "is_countdown_refresh_sm_allowed")
    private boolean countdownRefreshSmAllowed;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "reseller_id")
    private Reseller reseller;

    @Column(name = "account_logo_suspended")
    private boolean accountLogoSuspended;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Column(name = "timestamp", insertable = false, updatable = false)
    private Date timestamp;

    @Column(name = "is_driving_time_panel_allowed")
    private boolean drivingTimePanelAllowed;

    @Column(name = "driving_time_idling_seconds")
    private int drivingTimeIdlingSeconds;

    @Column(name = "driving_time_idling_seconds_allowed")
    private boolean drivingTimeIdlingSecondsAllowed;

    @Column(name = "is_ios_application_allowed")
    private boolean iOSApplicationAllowed;

    @Column(name = "is_seven_digital_postcode_allowed")
    private boolean isSevenDigitPostcodeAllowed;

    public void copyTo(Account copy) {
        super.copyTo(copy);
        copy.startDate = startDate;
        copy.notes = notes;
        copy.primaryEmail = primaryEmail;
        copy.secondaryEmail = secondaryEmail;
        copy.reseller = reseller;
        copy.currency = currency;
        copy.setAccountImage = setAccountImage;
        copy.accountLogoSuspended = accountLogoSuspended;
        copy.timestamp = timestamp;
        copy.refreshSmSeconds = refreshSmSeconds;
        copy.countdownRefreshSmAllowed = countdownRefreshSmAllowed;
        copy.tflCameraAllowed = tflCameraAllowed;
        copy.tflDisruptionsAllowed = tflDisruptionsAllowed;
        copy.drivingTimePanelAllowed = drivingTimePanelAllowed;
        copy.drivingTimeIdlingSeconds = drivingTimeIdlingSeconds;
        copy.drivingTimeIdlingSecondsAllowed = drivingTimeIdlingSecondsAllowed;
        copy.iOSApplicationAllowed = iOSApplicationAllowed;
        copy.isSevenDigitPostcodeAllowed = isSevenDigitPostcodeAllowed;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    public void setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

    public Reseller getReseller() {
        return reseller;
    }

    public void setReseller(Reseller reseller) {
        this.reseller = reseller;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setSetAccountImage(boolean setAccountImage) {
        this.setAccountImage = setAccountImage;
    }

    public boolean isSetAccountImage() {
        return setAccountImage;
    }

    public boolean isAccountLogoSuspended() {
        return accountLogoSuspended;
    }

    public void setAccountLogoSuspended(boolean accountLogoSuspended) {
        this.accountLogoSuspended = accountLogoSuspended;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getRefreshSmSeconds() {
        return refreshSmSeconds;
    }

    public void setRefreshSmSeconds(int refreshSmSeconds) {
        this.refreshSmSeconds = refreshSmSeconds;
    }

    public boolean isCountdownRefreshSmAllowed() {
        return countdownRefreshSmAllowed;
    }

    public void setCountdownRefreshSmAllowed(boolean countdownRefreshSmAllowed) {
        this.countdownRefreshSmAllowed = countdownRefreshSmAllowed;
    }

    public boolean isTflDisruptionsAllowed() {
        return tflDisruptionsAllowed;
    }

    public void setTflDisruptionsAllowed(boolean tflDisruptionsAllowed) {
        this.tflDisruptionsAllowed = tflDisruptionsAllowed;
    }

    public boolean isDrivingTimePanelAllowed() {
        return drivingTimePanelAllowed;
    }

    public void setDrivingTimePanelAllowed(boolean drivingTimePanelAllowed) {
        this.drivingTimePanelAllowed = drivingTimePanelAllowed;
    }

    public int getDrivingTimeIdlingSeconds() {
        return drivingTimeIdlingSeconds;
    }

    public void setDrivingTimeIdlingSeconds(int drivingTimeIdlingSeconds) {
        this.drivingTimeIdlingSeconds = drivingTimeIdlingSeconds;
    }

    public boolean isDrivingTimeIdlingSecondsAllowed() {
        return drivingTimeIdlingSecondsAllowed;
    }

    public void setDrivingTimeIdlingSecondsAllowed(boolean drivingTimeIdlingSecondsAllowed) {
        this.drivingTimeIdlingSecondsAllowed = drivingTimeIdlingSecondsAllowed;
    }

    public boolean isTflCameraAllowed() {
        return tflCameraAllowed;
    }

    public void setTflCameraAllowed(boolean tflCameraAllowed) {
        this.tflCameraAllowed = tflCameraAllowed;
    }

    public boolean isiOSApplicationAllowed() {
        return iOSApplicationAllowed;
    }

    public void setiOSApplicationAllowed(boolean iOSApplicationAllowed) {
        this.iOSApplicationAllowed = iOSApplicationAllowed;
    }

    public boolean isSevenDigitPostcodeAllowed() {
        return isSevenDigitPostcodeAllowed;
    }

    public void setSevenDigitPostcodeAllowed(boolean sevenDigitPostcodeAllowed) {
        isSevenDigitPostcodeAllowed = sevenDigitPostcodeAllowed;
    }
}
