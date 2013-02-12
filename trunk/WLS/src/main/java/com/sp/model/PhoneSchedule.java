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
@Table(name = "phone_schedule")
public class PhoneSchedule extends BaseModel {
    @Column(name = "schedule_name")
    private String scheduleName;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "user_id")
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public void copyTo(PhoneSchedule copy) {
        super.copyTo(copy);
        copy.scheduleName = scheduleName;
        copy.createdDate = createdDate;
        copy.userId = userId;
    }

    public String toOptionText(){
        return scheduleName;
    }
}
