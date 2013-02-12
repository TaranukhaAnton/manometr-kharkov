package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "owner")
public class Owner extends EnumModel {
    private Date established;
    @Column(name = "primary_email")
    private String primaryEmail;

    @Column(name = "secondary_email")
    private String secondaryEmail;

    private String address;

    private String tel;

    @Column(name = "is_deleted")
    private boolean deleted;

    public void copyTo(Owner copy) {
        super.copyTo(copy);
        copy.setEstablished(established);
        copy.setPrimaryEmail(primaryEmail);
        copy.setSecondaryEmail(secondaryEmail);
        copy.setAddress(address);
        copy.setTel(tel);
        copy.deleted = deleted;
    }

    public Date getEstablished() {
        return established;
    }

    public void setEstablished(Date established) {
        this.established = established;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    public void setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
