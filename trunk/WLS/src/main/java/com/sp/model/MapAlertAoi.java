package com.sp.model;

import javax.persistence.*;

@Entity
@Table(name = "map_alert_aoi")
public class MapAlertAoi extends BaseMapAlert {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "aoi_id")
    private Aoi aoi;

    public void copyTo(MapAlertAoi copy){
        super.copyTo(copy);
        copy.aoi = aoi;
    }

    public Aoi getAoi() {
        return aoi;
    }

    public void setAoi(Aoi aoi) {
        this.aoi = aoi;
    }
}
