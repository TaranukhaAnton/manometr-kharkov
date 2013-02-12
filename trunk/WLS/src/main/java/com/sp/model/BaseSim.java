package com.sp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: andrew
 * Date: 09.06.2011
 */
@Entity
@Table(name = "sim")

public class BaseSim extends BaseModel {
    private String number;

    public void copyTo(BaseSim copy) {
        super.copyTo(copy);
        copy.number = number;
    }

    public String toOptionText() {
        return number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
