package org.krams.tutorial.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class BaseMovableItemView extends EnumModel {
    @Column(name = "created_date", updatable = false, insertable = false)
    private Date createdDate;

    @Column(name = "image_file_name")
    private String imageFileName;

    @Column(name = "notes")
    private String notes;

    public void copyTo(BaseMovableItemView copy) {
        super.copyTo(copy);
        copy.createdDate = createdDate;
        copy.notes = notes;
        copy.imageFileName = imageFileName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isIsUnassignedGroup() {
//        return Util.isUnassignedGroup(this);
        //todo
        return false;

    }
}
