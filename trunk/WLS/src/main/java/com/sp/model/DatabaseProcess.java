package com.sp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: andrew
 * Date: 29.09.2010
 */
@Entity
@Table(name = "v_processlist")
public class DatabaseProcess extends EnumModel {
    private int time;
    private String state;

    public void copyTo(DatabaseProcess copy) {
        super.copyTo(copy);
        copy.time = time;
        copy.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
