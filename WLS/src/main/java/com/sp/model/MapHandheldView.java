package com.sp.model;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 01.07.2009
 * Time: 16:02:07
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "map_handheld_view")
public class MapHandheldView extends BaseMapItemView {
    public void copyTo(MapHandheldView copy){
        super.copyTo(copy);
    }
}
