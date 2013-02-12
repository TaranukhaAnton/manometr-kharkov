package com.sp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "tag_carrier_type")
public class TagCarrierType extends EnumModel {
    private String notes;

    public void copyTo(TagCarrierType copy) {
        super.copyTo(copy);
        copy.setNotes(notes);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
