package com.sp.model;

import javax.persistence.*;

@Entity
@Table(name = "security_user")

public class BaseSecurityUser extends BaseModel {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "dallas_key_id")
    private DallasKey dallasKey;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

     public void copyTo(BaseSecurityUser copy) {
         super.copyTo(copy);
         copy.firstName = firstName;
         copy.lastName = lastName;
         copy.dallasKey = dallasKey;
     }

    public DallasKey getDallasKey() {
        return dallasKey;
    }

    public void setDallasKey(DallasKey dallasKey) {
        this.dallasKey = dallasKey;
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

    public String toOptionText() {
          return firstName + " " + lastName;
    }

     public String getDescr() {
        return firstName + " " + lastName;
    }

     public void setDescr(String s){}

}
