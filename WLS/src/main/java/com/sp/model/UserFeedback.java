package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 20.11.2009
 * Time: 14:50:39
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "user_feedback")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class UserFeedback extends BaseModel{

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private SecurityUser securityUser;

    @Column(name = "url")
    private String url;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "vehicle_group_id")
    private UnitView vehicleGroup;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

    public SecurityUser getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UnitView getVehicleGroup() {
        return vehicleGroup;
    }

    public void setVehicleGroup(UnitView vehicleGroup) {
        this.vehicleGroup = vehicleGroup;
    }

    public void copyTo(UserFeedback copy){
        super.copyTo(copy);
        copy.securityUser = securityUser;
        copy.url = url;
        copy.vehicleGroup = vehicleGroup;
        copy.comment = comment;
        copy.createdDate = createdDate;
    }




}
