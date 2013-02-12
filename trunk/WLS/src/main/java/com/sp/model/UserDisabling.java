package com.sp.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by Alexander
 */
@MappedSuperclass
public class UserDisabling extends BaseModel {
    @Column(name = "user_id")
    private int userId;

    public void copyTo(UserDisabling copy) {
        super.copyTo(copy);
        copy.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
