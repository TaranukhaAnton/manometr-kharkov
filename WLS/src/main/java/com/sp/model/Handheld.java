package com.sp.model;

import com.sp.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * User: andrey
 */
@Entity
@Table(name = "handheld")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class Handheld extends BaseMovableItem {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "sim_id")
    private Sim assignedSim;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "type_id")
	private HandheldType type;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "box_type_id")
	private HandheldBoxType boxType;

    @Column(name = "header_name")
    private String headerName;

    @Column(name = "is_daylight_saving")
    private boolean daylightSaving;

    public void copyTo(Handheld copy) {
        super.copyTo(copy);
        copy.assignedSim = assignedSim;
        copy.type = type;
        copy.boxType = boxType;
        copy.headerName = headerName;
        copy.daylightSaving = daylightSaving;
    }

    public void copyToLazy(Handheld copy){
        super.copyToLazy(copy);
        copy.assignedSim = assignedSim;
        copy.headerName = headerName;
        copy.daylightSaving = daylightSaving;
    }

    public Sim getAssignedSim() {
        return assignedSim;
    }

    public void setAssignedSim(Sim assignedSim) {
        this.assignedSim = assignedSim;
    }

    public String getClientDescr(){
        String descr = "";
        if(getBoxType().getId()==Constants.HH_DT1_BOX_TYPE_ID){
            if(StringUtils.isNotBlank(getHeaderName())){
                descr = getHeaderName();
            }else{
                descr = "Device";
            }
        }else{
            if (super.getAssociatedRegularDriver() != null){
                descr += super.getAssociatedRegularDriver().getLastName() + "'s ";
            }
            descr += getTypeDescr();
        }
        return descr;
    }

    public void setClientDescr(String descr){}

    public HandheldType getType() {
        return type;
    }

    public void setType(HandheldType type) {
        this.type = type;
    }

    public HandheldBoxType getBoxType() {
        return boxType;
    }

    public void setBoxType(HandheldBoxType boxType) {
        this.boxType = boxType;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public boolean isDaylightSaving() {
        return daylightSaving;
    }

    public void setDaylightSaving(boolean daylightSaving) {
        this.daylightSaving = daylightSaving;
    }

}
