package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "handheld_type")
public class HandheldType extends EnumModel {
    @Column(name = "image_file_name")
    private String imageFileName;

    public void copyTo(VehicleType copy) {
        super.copyTo(copy);
        copy.setImageFileName(imageFileName);
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
