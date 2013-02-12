package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: andrey
 */
@Entity
@Table(name = "imei")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class TrackingDevice extends BaseTrackingDevice {
    @Column(insertable = false, updatable = false)
    private int code;

    @Column(name = "reseller_id")
    private int resellerId;

    private String version;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },
            fetch = FetchType.LAZY)
	@JoinColumn(name = "license_id")
	private License license;

	private String notes;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE },
            fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@Column(name = "gts_response_text")
	private String gtsResponseText;
	@Column(name = "gts_response_date")
	private Date gtsResponseDate;

	@Column(name = "device_response_text")
	private String deviceResponseText;
	@Column(name = "device_response_date")
	private Date deviceResponseDate;

	@Column(name = "io_status_response_text")
	private String ioStatusResponseText;
	@Column(name = "io_status_response_date")
	private Date ioStatusResponseDate;
	@Column(name = "info_response_date")
	private Date infoResponseDate;
	@Column(name = "info_response_text")
	private String infoResponseText;
	@Column(name = "last_config_datetime_response_date")
	private Date lastConfigDatetimeResponseDate;
	@Column(name = "last_config_datetime_response_text")
	private String lastConfigDatetimeResponseText;
	@Column(name = "version_response_date")
	private Date versionResponseDate;
	@Column(name = "version_response_text")
	private String versionResponseText;
	@Column(name = "time_based_acquisition_response_date")
	private Date timeBasedAcquisitionResponseDate;
	@Column(name = "time_based_acquisition_response_text")
	private String timeBasedAcquisitionResponseText;

	@Column(name = "digital_heigh_color")
	private int digitalHeighColor;

	@Column(name = "digital_low_color")
	private int digitalLowColor;

	@Column(name = "is_can_enabled")
	private boolean canEnabled;




    @Transient
	private List<CanSensorName> canSensorNames;


    public void copyTo(TrackingDevice copy) {
		super.copyTo(copy);
        copy.code = code;
        copy.resellerId = resellerId;
        copy.version = version;

        if (license != null) {
            License licenseCopy = new License();
            license.copyTo(licenseCopy);
            copy.license = licenseCopy;
        } else {
            copy.license = null;
        }
        copy.notes = notes;
        if (owner != null) {
            Owner ownerCopy = new Owner();
            owner.copyTo(ownerCopy);
            copy.owner = ownerCopy;
        } else {
            copy.owner = null;
        }
		copy.gtsResponseDate = this.gtsResponseDate;
        copy.gtsResponseText = this.gtsResponseText;
        copy.deviceResponseDate = this.deviceResponseDate;
        copy.deviceResponseText = this.deviceResponseText;
        copy.ioStatusResponseDate = this.ioStatusResponseDate;
        copy.ioStatusResponseText = this.ioStatusResponseText;
        copy.infoResponseDate = this.infoResponseDate;
        copy.infoResponseText = this.infoResponseText;
        copy.versionResponseDate = this.versionResponseDate;
        copy.versionResponseText = this.versionResponseText;
        copy.timeBasedAcquisitionResponseDate = this.timeBasedAcquisitionResponseDate;
        copy.timeBasedAcquisitionResponseText = this.timeBasedAcquisitionResponseText;
        copy.lastConfigDatetimeResponseDate = this.lastConfigDatetimeResponseDate;
        copy.lastConfigDatetimeResponseText = this.lastConfigDatetimeResponseText;

		copy.canEnabled = canEnabled;
		copy.digitalHeighColor = digitalHeighColor;
       
		copy.digitalLowColor = digitalLowColor;
      
	}

	public int getDigitalHeighColor() {
		return digitalHeighColor;
	}

	public void setDigitalHeighColor(int digitalHeighColor) {
		this.digitalHeighColor = digitalHeighColor;
	}

	public int getDigitalLowColor() {
		return digitalLowColor;
	}

	public void setDigitalLowColor(int digitalLowColor) {
		this.digitalLowColor = digitalLowColor;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public String getGtsResponseText() {
		return gtsResponseText;
	}

	public void setGtsResponseText(String gtsResponseText) {
		this.gtsResponseText = gtsResponseText;
	}

	public Date getGtsResponseDate() {
		return gtsResponseDate;
	}

	public void setGtsResponseDate(Date gtsResponseDate) {
		this.gtsResponseDate = gtsResponseDate;
	}

	public String getDeviceResponseText() {
		return deviceResponseText;
	}

	public void setDeviceResponseText(String deviceResponseText) {
		this.deviceResponseText = deviceResponseText;
	}

	public Date getDeviceResponseDate() {
		return deviceResponseDate;
	}

	public void setDeviceResponseDate(Date deviceResponseDate) {
		this.deviceResponseDate = deviceResponseDate;
	}

	public String getIoStatusResponseText() {
		return ioStatusResponseText;
	}

	public void setIoStatusResponseText(String ioStatusResponseText) {
		this.ioStatusResponseText = ioStatusResponseText;
	}

	public Date getIoStatusResponseDate() {
		return ioStatusResponseDate;
	}

	public void setIoStatusResponseDate(Date ioStatusResponseDate) {
		this.ioStatusResponseDate = ioStatusResponseDate;
	}

	public Date getInfoResponseDate() {
		return infoResponseDate;
	}

	public void setInfoResponseDate(Date infoResponseDate) {
		this.infoResponseDate = infoResponseDate;
	}

	public String getInfoResponseText() {
		return infoResponseText;
	}

	public void setInfoResponseText(String infoResponseText) {
		this.infoResponseText = infoResponseText;
	}

	public Date getLastConfigDatetimeResponseDate() {
		return lastConfigDatetimeResponseDate;
	}

	public void setLastConfigDatetimeResponseDate(
			Date lastConfigDatetimeResponseDate) {
		this.lastConfigDatetimeResponseDate = lastConfigDatetimeResponseDate;
	}

	public String getLastConfigDatetimeResponseText() {
		return lastConfigDatetimeResponseText;
	}

	public void setLastConfigDatetimeResponseText(
			String lastConfigDatetimeResponseText) {
		this.lastConfigDatetimeResponseText = lastConfigDatetimeResponseText;
	}

	public Date getVersionResponseDate() {
		return versionResponseDate;
	}

	public void setVersionResponseDate(Date versionResponseDate) {
		this.versionResponseDate = versionResponseDate;
	}

	public String getVersionResponseText() {
		return versionResponseText;
	}

	public void setVersionResponseText(String versionResponseText) {
		this.versionResponseText = versionResponseText;
	}

	public Date getTimeBasedAcquisitionResponseDate() {
		return timeBasedAcquisitionResponseDate;
	}

	public void setTimeBasedAcquisitionResponseDate(
			Date timeBasedAcquisitionResponseDate) {
		this.timeBasedAcquisitionResponseDate = timeBasedAcquisitionResponseDate;
	}

	public String getTimeBasedAcquisitionResponseText() {
		return timeBasedAcquisitionResponseText;
	}

	public void setTimeBasedAcquisitionResponseText(
			String timeBasedAcquisitionResponseText) {
		this.timeBasedAcquisitionResponseText = timeBasedAcquisitionResponseText;
    }

  	public boolean isCanEnabled() {
		return canEnabled;
	}

	public void setCanEnabled(boolean canEnabled) {
		this.canEnabled = canEnabled;
	}

	public List<CanSensorName> getCanSensorNames() {
		return canSensorNames;
	}

	public void setCanSensorNames(List<CanSensorName> canSensorNames) {
		this.canSensorNames = canSensorNames;
	}

    public int getResellerId() {
        return resellerId;
    }

    public void setResellerId(int resellerId) {
        this.resellerId = resellerId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
