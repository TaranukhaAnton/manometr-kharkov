package com.sp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 01.07.2009
 * Time: 10:00:20
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "map_handheld_account")
public class MapHandheldAccount extends BaseMapItemAccount {
    public void copyTo(MapHandheldAccount copy){
        super.copyTo(copy);
    }
}
