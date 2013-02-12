package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "unit_type")
public class VehicleType  extends EnumModel {
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
