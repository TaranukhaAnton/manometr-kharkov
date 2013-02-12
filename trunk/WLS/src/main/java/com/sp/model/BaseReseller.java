package com.sp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: andrew
 * Date: 05.07.2010
 */
@Entity
@Table(name = "reseller")

public class BaseReseller extends EnumModel {
    private int code;

    public void copyTo(BaseReseller copy) {
        super.copyTo(copy);
        copy.code = code;
    }
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
