package com.sp.dto.flex;

import java.util.Date;

/**
 * To change this template use File | Settings | File Templates.
 */
public class AlertRecipientFlx {

    private int id;

    private Date createdDate;

    private String firstName;

    private String lastName;

    private String email;

    private boolean sendByEmail;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSendByEmail() {
        return sendByEmail;
    }

    public void setSendByEmail(boolean sendByEmail) {
        this.sendByEmail = sendByEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

