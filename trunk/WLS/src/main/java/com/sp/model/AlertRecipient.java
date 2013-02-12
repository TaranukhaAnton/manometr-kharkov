package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 20.08.2009
 * Time: 14:17:25
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "alert_recipients")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class AlertRecipient extends BaseModel {
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "send_by_email")
    private boolean sendByEmail;

    @ManyToOne
    @JoinColumn(name = "alert_id")
    public Alert alert;
    
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public boolean getSendByEmail() {
        return sendByEmail;
    }

    public void setSendByEmail(boolean sendByEmail) {
        this.sendByEmail = sendByEmail;
    }


    public void copyTo(AlertRecipient copy){
        super.copyTo(copy);
        copy.createdDate = createdDate;
        copy.firstName = firstName;
        copy.lastName = lastName;
        copy.email = email;
        copy.alert = alert;
        copy.sendByEmail = sendByEmail;
    }

}
