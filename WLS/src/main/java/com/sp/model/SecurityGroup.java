package com.sp.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "security_group")
public class SecurityGroup extends EnumModel {
    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "parent_id")
    private SecurityGroup parent;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = SecurityUser.class,
            fetch = FetchType.EAGER
    )
    @JoinTable(name = "map_user_group",
        joinColumns=@JoinColumn(name="group_id"),
        inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private Set<SecurityUser> securityGroupUsers;

    public void copyTo(SecurityGroup copy) {
        super.copyTo(copy);
        copy.setCreatedDate(createdDate);
        copy.setParent(parent);
        copy.securityGroupUsers = securityGroupUsers;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public SecurityGroup getParent() {
        return parent;
    }

    public void setParent(SecurityGroup parent) {
        this.parent = parent;
    }

    public Set<SecurityUser> getSecurityGroupUsers() {
        return securityGroupUsers;
    }

    public void setSecurityGroupUsers(Set<SecurityUser> securityGroupUsers) {
        this.securityGroupUsers = securityGroupUsers;
    }
}
