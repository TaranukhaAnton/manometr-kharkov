package com.sp.model;


import javax.persistence.*;


/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 29.05.2009
 * Time: 16:51:51
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "map_alert_poi")
public class MapAlertPoi extends BaseMapAlert {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "poi_id")
    private Poi poi;

    public void copyTo(MapAlertPoi copy){
        super.copyTo(copy);
        copy.poi = poi;
    }

    public Poi getPoi() {
        return poi;
    }

    public void setPoi(Poi poi) {
        this.poi = poi;
    }
}
