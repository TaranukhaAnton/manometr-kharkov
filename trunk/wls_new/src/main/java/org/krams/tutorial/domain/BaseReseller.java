package org.krams.tutorial.domain;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@Entity
@Table(name = "reseller")
@MappedSuperclass
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
