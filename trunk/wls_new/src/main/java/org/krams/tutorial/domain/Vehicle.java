package org.krams.tutorial.domain;

import org.krams.tutorial.util.Constants;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA. User: andrey
 */

@Entity
@Table(name = "unit")
public class Vehicle extends LightVehicle {
	private final static DateFormat formatter = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm");
    @Column(insertable = false, updatable = false)
    private int code;

	@Column(name = "in_stealth_mode", insertable = false, updatable = false)
    private boolean inStealthMode;

	@Column(name = "renewal_date", updatable = false)
	private Date renewalDate;

    @Column(name = "first_use_date")
	private Date firstUseDate;

    @Column(name = "last_use_date")
	private Date lastUseDate;

    private String vin;

	@Column(name = "phone_model")
	private String phoneModel;

	@Column(name = "cab_phone")
	private String cabPhone;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "status_id")
	private VehicleStatus status;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "reseller_id")
    private BaseReseller reseller;

    @Column(name = "lat", insertable = false, updatable = false)
	private double lat;

	@Column(name = "lon", insertable = false, updatable = false)
	private double lon;

	@Column(name = "is_deleted")
	private boolean deleted;

	@Column(name = "poi_descr", insertable = false, updatable = false)
	private String poiDescr;

    @Column(name = "aoi_descr", insertable = false, updatable = false)
    private String aoiDescr;

	@Column(name = "is_ignition_active")
	private boolean ignitionActive;

	@Column(name = "ignition_status_visibility")
	private boolean ignitionStatusVisibility;

    @Column(name = "is_dot_on_map")
	private boolean dotOnMap;

	@Column(name = "today_journey_distance")
	private int todayJourneyDistance;

	@Column(name = "today_journey_time")
	private int todayJourneyTime;

	@Column(name = "road_lat")
	private Double roadLat;

	@Column(name = "road_lon")
	private Double roadLon;

	@Column(name = "distance_to_road")
	private Double distanceToRoad;

	@Column(name = "client_info")
	private String clientInfo;

	@Column(name = "mot_date")
	private Date motDate;

	@Column(name = "service_due_date")
	private Date serviceDate;

	@Column(name = "road_tax_date")
	private Date roadTaxDate;

	@Column(name = "insurance_due_date")
	private Date insuranceDueDate;

	@Column(name = "start_odo")
	private Double startOdo;

	@Column(name = "start_odo_date")
	private Date startOdoDate;

	@Column(name = "gps_odo")
	private Double gpsOdo;

	@Column(name = "notes")
	private String notes;

	@Column(name = "monday_start_time_hours")
	private short mondayStartTimeHours;

	@Column(name = "monday_start_time_mins")
	private short mondayStartTimeMins;

	@Column(name = "tuesday_start_time_hours")
	private short tuesdayStartTimeHours;

	@Column(name = "tuesday_start_time_mins")
	private short tuesdayStartTimeMins;

	@Column(name = "wed_start_time_hours")
	private short wednesdayStartTimeHours;

	@Column(name = "wed_start_time_mins")
	private short wednesdayStartTimeMins;

	@Column(name = "thu_start_time_hours")
	private short thursdayStartTimeHours;

	@Column(name = "thu_start_time_mins")
	private short thursdayStartTimeMins;

	@Column(name = "fri_start_time_hours")
	private short fridayStartTimeHours;

	@Column(name = "fri_start_time_mins")
	private short fridayStartTimeMins;

	@Column(name = "sat_start_time_hours")
	private short saturdayStartTimeHours;

	@Column(name = "sat_start_time_mins")
	private short saturdayStartTimeMins;

	@Column(name = "sun_start_time_hours")
	private short sundayStartTimeHours;

	@Column(name = "sun_start_time_mins")
	private short sundayStartTimeMins;

	@Column(name = "monday_end_time_hours")
	private short mondayEndTimeHours;

	@Column(name = "monday_end_time_mins")
	private short mondayEndTimeMins;

	@Column(name = "tuesday_end_time_hours")
	private short tuesdayEndTimeHours;

	@Column(name = "tuesday_end_time_mins")
	private short tuesdayEndTimeMins;

	@Column(name = "wed_end_time_hours")
	private short wednesdayEndTimeHours;

	@Column(name = "wed_end_time_mins")
	private short wednesdayEndTimeMins;

	@Column(name = "thu_end_time_hours")
	private short thursdayEndTimeHours;

	@Column(name = "thu_end_time_mins")
	private short thursdayEndTimeMins;

	@Column(name = "fri_end_time_hours")
	private short fridayEndTimeHours;

	@Column(name = "fri_end_time_mins")
	private short fridayEndTimeMins;

	@Column(name = "sat_end_time_hours")
	private short saturdayEndTimeHours;

	@Column(name = "sat_end_time_mins")
	private short saturdayEndTimeMins;

	@Column(name = "sun_end_time_hours")
	private short sundayEndTimeHours;

	@Column(name = "sun_end_time_mins")
	private short sundayEndTimeMins;

	@Column(name = "odo_start_time_hours")
	private short odoStartTimeHours;

	@Column(name = "odo_start_time_mins")
	private short odoStartTimeMins;

	@Column(name = "monday")
	private boolean monday;
	@Column(name = "tuesday")
	private boolean tuesday;
	@Column(name = "wednesday")
	private boolean wednesday;
	@Column(name = "thursday")
	private boolean thursday;
	@Column(name = "friday")
	private boolean friday;
	@Column(name = "saturday")
	private boolean saturday;
	@Column(name = "sunday")
	private boolean sunday;

    @Column(name = "is_accel_available")
	private boolean accelAvailable;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "current_driver_id")
    private BaseSecurityUser currentDriver;

    @Column(name = "digital_input1_high")
	private boolean digitalInput1High;
	@Column(name = "digital_input1_name")
	private String digitalInput1Name;

    @Column(name = "digital_input2_high")
	private boolean digitalInput2High;
	@Column(name = "digital_input2_name")
	private String digitalInput2Name;

    @Column(name = "digital_input3_high")
	private boolean digitalInput3High;
	@Column(name = "digital_input3_name")
	private String digitalInput3Name;

    @Column(name = "digital_input4_high")
	private boolean digitalInput4High;
	@Column(name = "digital_input4_name")
	private String digitalInput4Name;

     @Column(name = "digital_input5_high")
	private boolean digitalInput5High;
	@Column(name = "digital_input5_name")
	private String digitalInput5Name;

    @Column(name = "digital_input6_high")
	private boolean digitalInput6High;
	@Column(name = "digital_input6_name")
	private String digitalInput6Name;
    
    @Column(name = "last_active_msg_id")
    private int lastActiveMsgId;

    @Column(name="is_network_active")
    private boolean networkActive;

    @Column(name="is_network_archived")
    private boolean networkArchived;

    @Transient
	private String digitalInput1Value;
    @Transient
	private String digitalInput2Value;
    @Transient
	private String digitalInput3Value;
    @Transient
	private String digitalInput4Value;
    @Transient
	private String digitalInput5Value;
    @Transient
	private String digitalInput6Value;

    @Transient
	private int digitalInput1Color;
    @Transient
	private int digitalInput2Color;
    @Transient
	private int digitalInput3Color;
    @Transient
	private int digitalInput4Color;
    @Transient
	private int digitalInput5Color;
    @Transient
	private int digitalInput6Color;

    @Transient
    private int activeDinsCount;

    @Transient
    private int lastDinNumber;
   

    @Transient
	private boolean expanded;

	@Transient
	private String clientDescr;

    public boolean isNetworkActive() {
        return networkActive;
    }

    public void setNetworkActive(boolean networkActive) {
        this.networkActive = networkActive;
    }

    public boolean isNetworkArchived() {
        return networkArchived;
    }

    public void setNetworkArchived(boolean networkArchived) {
        this.networkArchived = networkArchived;
    }

    public Long getImmobilizationNumber() {
        //todo fix

//        return SpContext.getCachedImmobilizationNumberByVehicleId(getId());
        return 0L;
    }

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public void setCostPerMileToDistance(double costPerMileToDistance) {
	}

	public void setCostPerHourToDistance(double costPerHourToDistance) {
	}

	public double getCostPerMileToDistance() {
//		double distanceMiles = Util.getDistaceInMileByKM(todayJourneyDistance * Constants.KM_IN_METER);
//		return distanceMiles * (long) getCostPerMile()	/ (long) Constants.PENCES_IN_POUND;
        //todo fix
        return 0d;
	}

	public double getCostPerHourToDistance() {
	//	double hours = todayJourneyTime / (60.0 * 60);
//		return hours * (long) getCostPerHour() / (long) Constants.PENCES_IN_POUND;
        //todo fix
        return 0d;
    }

	public BigDecimal getItemCost() {
		BigDecimal x = new BigDecimal(
				getCostPerMileToDistance() + getCostPerHourToDistance());
		x = x.setScale(2, BigDecimal.ROUND_HALF_UP);
		return x;
	}

	public void setItemCost(BigDecimal d) {
	}



	public boolean isIgnitionStatusVisibility() {
		return ignitionStatusVisibility;
	}

	public void setIgnitionStatusVisibility(boolean ignitionStatusVisibility) {
		this.ignitionStatusVisibility = ignitionStatusVisibility;
	}

	public String getDirectionOfTravelUpperCase() {
		return getDirectionOfTravel().toUpperCase();
	}

	public void setDirectionOfTravelUpperCase(String directionOfTravelUpperCase) {
	}

	public String getIgnitionColor() {
		if (getIgnitionStatus().equals(Constants.IgnitionStatus.On.toString())) {
			return "#6c9d31";
		} else if (getIgnitionStatus().equals(Constants.IgnitionStatus.Idling.toString())) {
			return "#f7931d";
		}
		return "#ff0000";
	}

	public String getIgnitionLabel() {
		if (getIgnitionStatus().equals(Constants.IgnitionStatus.On.toString())) {
			return "IGNITION ON";
		} else if (getIgnitionStatus().equals(Constants.IgnitionStatus.Idling.toString())) {
			return "IDLING";
		}
		return "IGNITION OFF";
	}

	public Date getRenewalDate() {
		return renewalDate;
	}

	public void setRenewalDate(Date renewalDate) {
		this.renewalDate = renewalDate;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getCabPhone() {
		return cabPhone;
	}

	public void setCabPhone(String cabPhone) {
		this.cabPhone = cabPhone;
	}

	public VehicleStatus getStatus() {
		return status;
	}

	public void setStatus(VehicleStatus status) {
		this.status = status;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public String getPoiDescr() {
		return poiDescr;
	}

	public void setPoiDescr(String poiDescr) {
		this.poiDescr = poiDescr;
	}

	public void setIgnitionActive(boolean ignitionActive) {
		this.ignitionActive = ignitionActive;
	}

	public boolean isIgnitionActive() {
		return this.ignitionActive;
	}



	public int getTodayJourneyDistance() {
		return todayJourneyDistance;
	}

	public void setTodayJourneyDistance(int todayJourneyDistance) {
		this.todayJourneyDistance = todayJourneyDistance;
	}

	public Double getRoadLat() {
		return roadLat;
	}

	public void setRoadLat(Double roadLat) {
		this.roadLat = roadLat;
	}

	public Double getRoadLon() {
		return roadLon;
	}

	public void setRoadLon(Double roadLon) {
		this.roadLon = roadLon;
	}

	public Double getDistanceToRoad() {
		return distanceToRoad;
	}

	public void setDistanceToRoad(Double distanceToRoad) {
		this.distanceToRoad = distanceToRoad;
	}

	public String getClientDescr() {
//		if (clientDescr == null) {
//            if (SpContext.getUserPrefs().isReplaceDriverNameWithRegNum() &&
//                    SpContext.getUserDetailsInfo().isReplaceDriverNameWithRegNum() &&
//                    getAssociatedRegularDriver() != null) {
//                clientDescr = getAssociatedRegularDriver().getDescr();
//            } else {
//                clientDescr = getRegistrationNumber();
//            }
//		}
//		return clientDescr;
        //todo fix
        return "";
	}

	public void setClientDescr(String clientDescr) {
		this.clientDescr = clientDescr;
	}

	public String getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(String clientInfo) {
		this.clientInfo = clientInfo;
	}

    public Date getMotDate() {
		return motDate;
	}

	public void setMotDate(Date motDate) {
		this.motDate = motDate;
	}

	public Date getRoadTaxDate() {
		return roadTaxDate;
	}

	public void setRoadTaxDate(Date roadTaxDate) {
		this.roadTaxDate = roadTaxDate;
	}

	public Date getInsuranceDueDate() {
		return insuranceDueDate;
	}

	public void setInsuranceDueDate(Date insuranceDueDate) {
		this.insuranceDueDate = insuranceDueDate;
	}

	public Double getStartOdo() {
		return startOdo;
	}

	public void setStartOdo(Double startOdo) {
		this.startOdo = startOdo;
	}

	public Date getStartOdoDate() {
		return startOdoDate;
	}

	public void setStartOdoDate(Date startOdoDate) {
		this.startOdoDate = startOdoDate;
	}

	public Double getGpsOdo() {
		return gpsOdo;
	}

	public void setGpsOdo(Double gpsOdo) {
		this.gpsOdo = gpsOdo;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getTodayJourneyTime() {
		return todayJourneyTime;
	}

	public void setTodayJourneyTime(int todayJourneyTime) {
		this.todayJourneyTime = todayJourneyTime;
	}

	public short getMondayStartTimeHours() {
		return mondayStartTimeHours;
	}

	public void setMondayStartTimeHours(short mondayStartTimeHours) {
		this.mondayStartTimeHours = mondayStartTimeHours;
	}

	public short getMondayStartTimeMins() {
		return mondayStartTimeMins;
	}

	public void setMondayStartTimeMins(short mondayStartTimeMins) {
		this.mondayStartTimeMins = mondayStartTimeMins;
	}

	public short getTuesdayStartTimeHours() {
		return tuesdayStartTimeHours;
	}

	public void setTuesdayStartTimeHours(short tuesdayStartTimeHours) {
		this.tuesdayStartTimeHours = tuesdayStartTimeHours;
	}

	public short getTuesdayStartTimeMins() {
		return tuesdayStartTimeMins;
	}

	public void setTuesdayStartTimeMins(short tuesdayStartTimeMins) {
		this.tuesdayStartTimeMins = tuesdayStartTimeMins;
	}

	public short getWednesdayStartTimeHours() {
		return wednesdayStartTimeHours;
	}

	public void setWednesdayStartTimeHours(short wednesdayStartTimeHours) {
		this.wednesdayStartTimeHours = wednesdayStartTimeHours;
	}

	public short getWednesdayStartTimeMins() {
		return wednesdayStartTimeMins;
	}

	public void setWednesdayStartTimeMins(short wednesdayStartTimeMins) {
		this.wednesdayStartTimeMins = wednesdayStartTimeMins;
	}

	public short getThursdayStartTimeHours() {
		return thursdayStartTimeHours;
	}

	public void setThursdayStartTimeHours(short thursdayStartTimeHours) {
		this.thursdayStartTimeHours = thursdayStartTimeHours;
	}

	public short getThursdayStartTimeMins() {
		return thursdayStartTimeMins;
	}

	public void setThursdayStartTimeMins(short thursdayStartTimeMins) {
		this.thursdayStartTimeMins = thursdayStartTimeMins;
	}

	public short getFridayStartTimeHours() {
		return fridayStartTimeHours;
	}

	public void setFridayStartTimeHours(short fridayStartTimeHours) {
		this.fridayStartTimeHours = fridayStartTimeHours;
	}

	public short getFridayStartTimeMins() {
		return fridayStartTimeMins;
	}

	public void setFridayStartTimeMins(short fridayStartTimeMins) {
		this.fridayStartTimeMins = fridayStartTimeMins;
	}

	public short getSaturdayStartTimeHours() {
		return saturdayStartTimeHours;
	}

	public void setSaturdayStartTimeHours(short saturdayStartTimeHours) {
		this.saturdayStartTimeHours = saturdayStartTimeHours;
	}

	public short getSaturdayStartTimeMins() {
		return saturdayStartTimeMins;
	}

	public void setSaturdayStartTimeMins(short saturdayStartTimeMins) {
		this.saturdayStartTimeMins = saturdayStartTimeMins;
	}

	public short getSundayStartTimeHours() {
		return sundayStartTimeHours;
	}

	public void setSundayStartTimeHours(short sundayStartTimeHours) {
		this.sundayStartTimeHours = sundayStartTimeHours;
	}

	public short getSundayStartTimeMins() {
		return sundayStartTimeMins;
	}

	public void setSundayStartTimeMins(short sundayStartTimeMins) {
		this.sundayStartTimeMins = sundayStartTimeMins;
	}

	public short getMondayEndTimeHours() {
		return mondayEndTimeHours;
	}

	public void setMondayEndTimeHours(short mondayEndTimeHours) {
		this.mondayEndTimeHours = mondayEndTimeHours;
	}

	public short getMondayEndTimeMins() {
		return mondayEndTimeMins;
	}

	public void setMondayEndTimeMins(short mondayEndTimeMins) {
		this.mondayEndTimeMins = mondayEndTimeMins;
	}

	public short getTuesdayEndTimeHours() {
		return tuesdayEndTimeHours;
	}

	public void setTuesdayEndTimeHours(short tuesdayEndTimeHours) {
		this.tuesdayEndTimeHours = tuesdayEndTimeHours;
	}

	public short getTuesdayEndTimeMins() {
		return tuesdayEndTimeMins;
	}

	public void setTuesdayEndTimeMins(short tuesdayEndTimeMins) {
		this.tuesdayEndTimeMins = tuesdayEndTimeMins;
	}

	public short getWednesdayEndTimeHours() {
		return wednesdayEndTimeHours;
	}

	public void setWednesdayEndTimeHours(short wednesdayEndTimeHours) {
		this.wednesdayEndTimeHours = wednesdayEndTimeHours;
	}

	public short getWednesdayEndTimeMins() {
		return wednesdayEndTimeMins;
	}

	public void setWednesdayEndTimeMins(short wednesdayEndTimeMins) {
		this.wednesdayEndTimeMins = wednesdayEndTimeMins;
	}

	public short getThursdayEndTimeHours() {
		return thursdayEndTimeHours;
	}

	public void setThursdayEndTimeHours(short thursdayEndTimeHours) {
		this.thursdayEndTimeHours = thursdayEndTimeHours;
	}

	public short getThursdayEndTimeMins() {
		return thursdayEndTimeMins;
	}

	public void setThursdayEndTimeMins(short thursdayEndTimeMins) {
		this.thursdayEndTimeMins = thursdayEndTimeMins;
	}

	public short getFridayEndTimeHours() {
		return fridayEndTimeHours;
	}

	public void setFridayEndTimeHours(short fridayEndTimeHours) {
		this.fridayEndTimeHours = fridayEndTimeHours;
	}

	public short getFridayEndTimeMins() {
		return fridayEndTimeMins;
	}

	public void setFridayEndTimeMins(short fridayEndTimeMins) {
		this.fridayEndTimeMins = fridayEndTimeMins;
	}

	public short getSaturdayEndTimeHours() {
		return saturdayEndTimeHours;
	}

	public void setSaturdayEndTimeHours(short saturdayEndTimeHours) {
		this.saturdayEndTimeHours = saturdayEndTimeHours;
	}

	public short getSaturdayEndTimeMins() {
		return saturdayEndTimeMins;
	}

	public void setSaturdayEndTimeMins(short saturdayEndTimeMins) {
		this.saturdayEndTimeMins = saturdayEndTimeMins;
	}

	public short getSundayEndTimeHours() {
		return sundayEndTimeHours;
	}

	public void setSundayEndTimeHours(short sundayEndTimeHours) {
		this.sundayEndTimeHours = sundayEndTimeHours;
	}

	public short getSundayEndTimeMins() {
		return sundayEndTimeMins;
	}

	public void setSundayEndTimeMins(short sundayEndTimeMins) {
		this.sundayEndTimeMins = sundayEndTimeMins;
	}

	public boolean isMonday() {
		return monday;
	}

	public void setMonday(boolean monday) {
		this.monday = monday;
	}

	public boolean isTuesday() {
		return tuesday;
	}

	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}

	public boolean isWednesday() {
		return wednesday;
	}

	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}

	public boolean isThursday() {
		return thursday;
	}

	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}

	public boolean isFriday() {
		return friday;
	}

	public void setFriday(boolean friday) {
		this.friday = friday;
	}

	public boolean isSaturday() {
		return saturday;
	}

	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}

	public boolean isSunday() {
		return sunday;
	}

	public void setSunday(boolean sunday) {
		this.sunday = sunday;
	}

	public short getOdoStartTimeHours() {
		return odoStartTimeHours;
	}

	public void setOdoStartTimeHours(short odoStartTimeHours) {
		this.odoStartTimeHours = odoStartTimeHours;
	}

	public short getOdoStartTimeMins() {
		return odoStartTimeMins;
	}

	public void setOdoStartTimeMins(short odoStartTimeMins) {
		this.odoStartTimeMins = odoStartTimeMins;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getMotPureDate() {
//		if (motDate != null) {
//			return Util.getDaysFromDate(motDate);
//		}
        //todo fix
		return "";
	}

	public void setMotPureDate(String motPureDate) {
	}

	public String getTaxPureDate() {
//		if (roadTaxDate != null) {
//			return Util.getDaysFromDate(roadTaxDate);
//		}
        //todo fix
		return "";
	}

	public void setTaxPureDate(String taxPureDate) {
	}

	public String getServicePureDate() {
//		if (serviceDate != null) {
//			return Util.getDaysFromDate(serviceDate);
//		}
        //todo fix
		return "";
	}

	public void setServicePureDate(String servicePureDate) {
	}

	public String getInsurancePureDate() {
//		if (insuranceDueDate != null) {
//			return Util.getDaysFromDate(insuranceDueDate);
//		}
        //todo fix
		return "";
	}

	public void setInsurancePureDate(String insurancePureDate) {
	}

	public String getRegularDriver() {
		return getRegDriverFullName();
	}

	public void setRegularDriver(String regularDriver) {
	}

	public String getRenewalDateStr() {
		return (this.getRenewalDate() != null ? formatter.format(this
				.getRenewalDate()) : "");
	}

	public void setRenewalDateStr(String renewalDateStr) {
	}

	public void setAccelAvailable(boolean accelAvailable) {
		this.accelAvailable = accelAvailable;
	}

	public boolean getAccelAvailable() {
		return accelAvailable;
	}

    public String getAoiPoiDescr() {
//        return Util.buildAoiPoiDescr(poiDescr, aoiDescr);
        //todo fix
        return "";
    }

    public boolean isInStealthMode() {
        return inStealthMode;
    }

    public void setInStealthMode(boolean inStealthMode) {
        this.inStealthMode = inStealthMode;
    }

    //for flex serialization
    public void setAoiPoiDescr(String aoiDescr) {    }

    public String getAoiDescr() {
        return aoiDescr;
    }

    public void setAoiDescr(String aoiDescr) {
        this.aoiDescr = aoiDescr;
    }

    public BaseSecurityUser getCurrentDriver() {
        return currentDriver;
    }

    public void setCurrentDriver(BaseSecurityUser currentDriver) {
        this.currentDriver = currentDriver;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BaseReseller getReseller() {
        return reseller;
    }

    public void setReseller(BaseReseller reseller) {
        this.reseller = reseller;
    }

    public boolean isDigitalInput1High() {
        return digitalInput1High;
    }

    public void setDigitalInput1High(boolean digitalInput1High) {
        this.digitalInput1High = digitalInput1High;
    }

    public String getDigitalInput1Name() {
        return digitalInput1Name;
    }

    public void setDigitalInput1Name(String digitalInput1Name) {
        this.digitalInput1Name = digitalInput1Name;
    }

    public boolean isDigitalInput2High() {
        return digitalInput2High;
    }

    public void setDigitalInput2High(boolean digitalInput2High) {
        this.digitalInput2High = digitalInput2High;
    }

    public String getDigitalInput2Name() {
        return digitalInput2Name;
    }

    public void setDigitalInput2Name(String digitalInput2Name) {
        this.digitalInput2Name = digitalInput2Name;
    }

    public boolean isDigitalInput3High() {
        return digitalInput3High;
    }

    public void setDigitalInput3High(boolean digitalInput3High) {
        this.digitalInput3High = digitalInput3High;
    }

    public String getDigitalInput3Name() {
        return digitalInput3Name;
    }

    public void setDigitalInput3Name(String digitalInput3Name) {
        this.digitalInput3Name = digitalInput3Name;
    }

    public boolean isDigitalInput4High() {
        return digitalInput4High;
    }

    public void setDigitalInput4High(boolean digitalInput4High) {
        this.digitalInput4High = digitalInput4High;
    }

    public String getDigitalInput4Name() {
        return digitalInput4Name;
    }

    public void setDigitalInput4Name(String digitalInput4Name) {
        this.digitalInput4Name = digitalInput4Name;
    }

    public boolean isDigitalInput5High() {
        return digitalInput5High;
    }

    public void setDigitalInput5High(boolean digitalInput5High) {
        this.digitalInput5High = digitalInput5High;
    }

    public String getDigitalInput5Name() {
        return digitalInput5Name;
    }

    public void setDigitalInput5Name(String digitalInput5Name) {
        this.digitalInput5Name = digitalInput5Name;
    }

    public boolean isDigitalInput6High() {
        return digitalInput6High;
    }

    public void setDigitalInput6High(boolean digitalInput6High) {
        this.digitalInput6High = digitalInput6High;
    }

    public String getDigitalInput6Name() {
        return digitalInput6Name;
    }

    public void setDigitalInput6Name(String digitalInput6Name) {
        this.digitalInput6Name = digitalInput6Name;
    }

    public String getIgnitionStatus() {
     //   return Util.getIgnitionStatus(ignitionActive, getCurSpeed()).toString();
        //todo fix
        return "";
    }

    // For serialization
    public void setIgnitionStatus(String ignitionStatus) {
    }

    public String getDigitalInput1Value() {
        return digitalInput1Value;
    }

    public void setDigitalInput1Value(String digitalInput1Value) {
        this.digitalInput1Value = digitalInput1Value;
    }

    public String getDigitalInput2Value() {
        return digitalInput2Value;
    }

    public void setDigitalInput2Value(String digitalInput2Value) {
        this.digitalInput2Value = digitalInput2Value;
    }

    public String getDigitalInput3Value() {
        return digitalInput3Value;
    }

    public void setDigitalInput3Value(String digitalInput3Value) {
        this.digitalInput3Value = digitalInput3Value;
    }

    public String getDigitalInput4Value() {
        return digitalInput4Value;
    }

    public void setDigitalInput4Value(String digitalInput4Value) {
        this.digitalInput4Value = digitalInput4Value;
    }

    public String getDigitalInput5Value() {
        return digitalInput5Value;
    }

    public void setDigitalInput5Value(String digitalInput5Value) {
        this.digitalInput5Value = digitalInput5Value;
    }

    public String getDigitalInput6Value() {
        return digitalInput6Value;
    }

    public void setDigitalInput6Value(String digitalInput6Value) {
        this.digitalInput6Value = digitalInput6Value;
    }

    public int getDigitalInput1Color() {
        return digitalInput1Color;
    }

    public void setDigitalInput1Color(int digitalInput1Color) {
        this.digitalInput1Color = digitalInput1Color;
    }

    public int getDigitalInput2Color() {
        return digitalInput2Color;
    }

    public void setDigitalInput2Color(int digitalInput2Color) {
        this.digitalInput2Color = digitalInput2Color;
    }

    public int getDigitalInput3Color() {
        return digitalInput3Color;
    }

    public void setDigitalInput3Color(int digitalInput3Color) {
        this.digitalInput3Color = digitalInput3Color;
    }

    public int getDigitalInput4Color() {
        return digitalInput4Color;
    }

    public void setDigitalInput4Color(int digitalInput4Color) {
        this.digitalInput4Color = digitalInput4Color;
    }

    public int getDigitalInput6Color() {
        return digitalInput6Color;
    }

    public void setDigitalInput6Color(int digitalInput6Color) {
        this.digitalInput6Color = digitalInput6Color;
    }

    public int getDigitalInput5Color() {
        return digitalInput5Color;
    }

    public void setDigitalInput5Color(int digitalInput5Color) {
        this.digitalInput5Color = digitalInput5Color;
    }

    public int getActiveDinsCount() {
        return activeDinsCount;
    }

    public void setActiveDinsCount(int activeDinsCount) {
        this.activeDinsCount = activeDinsCount;
    }

    public int getLastDinNumber() {
        return lastDinNumber;
    }

    public void setLastDinNumber(int lastDinNumber) {
        this.lastDinNumber = lastDinNumber;
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

    public boolean isDotOnMap() {
        return dotOnMap;
    }

    public void setDotOnMap(boolean dotOnMap) {
        this.dotOnMap = dotOnMap;
    }

    public Date getFirstUseDate() {
        return firstUseDate;
    }

    public void setFirstUseDate(Date firstUseDate) {
        this.firstUseDate = firstUseDate;
    }

    public Date getLastUseDate() {
        return lastUseDate;
    }

    public void setLastUseDate(Date lastUseDate) {
        this.lastUseDate = lastUseDate;
    }

    public int getLastActiveMsgId() {
        return lastActiveMsgId;
    }

    public void setLastActiveMsgId(int lastActiveMsgId) {
        this.lastActiveMsgId = lastActiveMsgId;
    }
}
