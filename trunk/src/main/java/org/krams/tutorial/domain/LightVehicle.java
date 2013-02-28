package org.krams.tutorial.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * User: andrew
 * Date: 12.10.2010
 */
@MappedSuperclass

public class LightVehicle extends BaseVehicle {
    @Column(name = "cur_speed", updatable = false)
    private int curSpeed;
    @Column(name = "cur_direction", updatable = false)
    private int curDirection;

    @Column(name = "fleet_id")
	private String fleetId;

    @Column(name = "immobilization_date")
	private Date immobilizationDate;

    @Column(name = "immobilize_status")
	private boolean immobilizeStatus;

    @Column(name = "cost_per_mile")
	private int costPerMile;

	@Column(name = "cost_per_hour")
	private int costPerHour;

    @Column(name = "avg_mpg_input")
	private Double avgMpgInput;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "associated_regular_driver_id")
	private BaseSecurityUser associatedRegularDriver;

    public void copyTo(LightVehicle copy) {
        super.copyTo(copy);
        copy.curSpeed = curSpeed;
        copy.curDirection = curDirection;
        copy.immobilizationDate = immobilizationDate;
        copy.immobilizeStatus = immobilizeStatus;
        copy.costPerMile = costPerMile;
        copy.costPerHour = costPerHour;
        copy.fleetId = fleetId;
        copy.avgMpgInput = avgMpgInput;
        copy.associatedRegularDriver = associatedRegularDriver;
    }

    public int getCostPerMile() {
        return costPerMile;
    }

    public void setCostPerMile(int item) {
        costPerMile = item;
    }

    public int getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(int item) {
        costPerHour = item;
    }

    public String getFleetId() {
		return fleetId;
	}

	public void setFleetId(String fleetId) {
		this.fleetId = fleetId;
	}

    public float getFactoredSpeed() {
       // return Util.getFactoredSpeed(curSpeed);
        //todo fix
        return 0f;
	}

	public void setFactoredSpeed(float factoredSpeed) {
	}

    public int getCurDirection() {
        return curDirection;
    }

    public void setCurDirection(int curDirection) {
        this.curDirection = curDirection;
    }

    public int getCurSpeed() {
        return curSpeed;
    }

    public void setCurSpeed(int curSpeed) {
        this.curSpeed = curSpeed;
    }

    public String getDirectionOfTravel() {
        //return Util.angleToDirectionOfTravel(getCurDirection());
        //todo fix
        return "";
    }
    
    
    public String getDirectionOfTravelFull() {
        //return Util.angleToDirectionOfTravelFull(getCurDirection());
        //todo fix
        return "";
    }

	public void setDirectionOfTravel(String directionOfTravel) {
	}
                                                     
    public Date getImmobilizationDate() {
        return immobilizationDate;
    }

    public void setImmobilizationDate(Date immobilizationDate) {
        this.immobilizationDate = immobilizationDate;
    }

    public boolean getImmobilizeStatus() {
        return immobilizeStatus;
    }

    public void setImmobilizeStatus(boolean immobilizeStatus) {
        this.immobilizeStatus = immobilizeStatus;
    }

    public Double getAvgMpgInput() {
        return avgMpgInput;
    }

    public void setAvgMpgInput(Double avgMpgInput) {
        this.avgMpgInput = avgMpgInput;
    }

    public String getRegDriverFullName() {
		if (associatedRegularDriver != null) {
			return associatedRegularDriver.getFirstName() + " "
					+ associatedRegularDriver.getLastName();
		}
		return "";
	}

    public BaseSecurityUser getAssociatedRegularDriver() {
        return associatedRegularDriver;
    }

    public void setAssociatedRegularDriver(BaseSecurityUser associatedRegularDriver) {
        this.associatedRegularDriver = associatedRegularDriver;
    }
}
