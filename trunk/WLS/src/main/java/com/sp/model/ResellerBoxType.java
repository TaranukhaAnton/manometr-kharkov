package com.sp.model;

import javax.persistence.*;

/**
 * User: andrew
 * Date: 17.06.2010
 */
@Entity
@Table(name = "reseller_box_type")
public class ResellerBoxType extends BaseModel {
    @Column(name = "reseller_id")
    private int resellerId;
    
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "box_type_id")
    private BoxType boxType;

    @Column(name = "journey_generation_delay")
    private int journeyGenerationDelay = 14;

    public void copyTo(ResellerBoxType copy) {
        super.copyTo(copy);
        copy.resellerId = resellerId;
        copy.boxType = boxType;
        copy.journeyGenerationDelay = journeyGenerationDelay;
    }

    public int getResellerId() {
        return resellerId;
    }

    public void setResellerId(int resellerId) {
        this.resellerId = resellerId;
    }

    public BoxType getBoxType() {
        return boxType;
    }

    public void setBoxType(BoxType boxType) {
        this.boxType = boxType;
    }

    public int getJourneyGenerationDelay() {
        return journeyGenerationDelay;
    }

    public void setJourneyGenerationDelay(int journeyGenerationDelay) {
        this.journeyGenerationDelay = journeyGenerationDelay;
    }
}
