package com.sp.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@MappedSuperclass
public class EnumModel extends BaseModel {
    
    private String descr;

    @Transient
    private boolean selected;

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public void copyTo(EnumModel copy) {
        super.copyTo(copy);
        copy.descr = descr;
        copy.selected = selected;
    }

    public String toOptionText() {
        return descr;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "EnumModel{" +
                "descr='" + descr + '\'' +
                ", selected=" + selected +
                '}';
    }
}
