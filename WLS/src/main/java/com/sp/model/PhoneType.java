package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
/**
 * Created by IntelliJ IDEA.
 * User: ananda
 * Date: Mar 20, 2009
 * Time: 12:20:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "phone_image")
public class PhoneType extends EnumModel {
     @Column(name = "image_file_name")
     private String imageFileName;


    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public void copyTo(PhoneType copy) {
          super.copyTo(copy);
          copy.setImageFileName(imageFileName);
    }
    
}
