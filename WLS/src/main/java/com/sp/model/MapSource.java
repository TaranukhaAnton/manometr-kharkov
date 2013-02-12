package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: andrey
 */
@Entity
@Table(name = "map_source")
public class MapSource extends EnumModel {

    @Column(name = "is_key_required")
    private boolean keyRequired;

    private String notes;

    public void copyTo(MapSource copy) {
        super.copyTo(copy);
        copy.keyRequired = keyRequired;
        copy.notes = notes;
    }

    public boolean isKeyRequired() {
        return keyRequired;
    }

    public void setKeyRequired(boolean keyRequired) {
        this.keyRequired = keyRequired;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toOptionText() {
        return notes;    
    }
}
