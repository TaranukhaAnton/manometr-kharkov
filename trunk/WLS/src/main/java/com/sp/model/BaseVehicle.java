package com.sp.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * User: andrew
 * Date: 07.07.2010
 */
@Entity
@Table(name = "unit")

@XmlRootElement
public class BaseVehicle extends AddressedEnumModel {
    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "timestamp", insertable = false, updatable = false)
    private Date timestamp;


    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "type_id")
	private VehicleType type;

    public void copyTo(BaseVehicle copy) {
        super.copyTo(copy);
        copy.registrationNumber = registrationNumber;
        copy.timestamp = timestamp;
        copy.type = type;
    }

    public String getRegistrationNumberJsEsceped() {
        return registrationNumber.replace("'", "\'");
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String toOptionText() {
		return registrationNumber;
	}

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
