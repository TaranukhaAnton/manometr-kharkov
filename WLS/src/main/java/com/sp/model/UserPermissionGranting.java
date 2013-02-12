package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "granted_user_permission")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class UserPermissionGranting extends BaseModel {
    @Column(name = "is_delegation_enabled")
    private boolean delegationEnabled;
    
    @Column(name = "is_denied")
    private boolean denied;

    @Column(name = "permission_id")
    private int permissionId;

    @Column(name = "user_id")
    private int userId;

    @Transient
    private String descr;

    @Transient
    private String objectAction;

    @Transient
    private PermissionTypeFilter category;

    @Transient
    private List<Integer> childrenPermitions = new ArrayList<Integer>();

    @Transient
    private int state = GRANTED_STATE;
    public static final int NOT_GRANTED_STATE = 0;
    public static final int GRANTED_STATE = 1;
    public static final int DENIED_STATE = -1;

    public void copyTo(UserPermissionGranting copy) {
        super.copyTo(copy);
        copy.setDelegationEnabled(delegationEnabled);
        copy.setDenied(denied);        
        copy.setPermissionId(permissionId);
        copy.setUserId(userId);
    }

    public boolean isDelegationEnabled() {
        return delegationEnabled;
    }

    public void setDelegationEnabled(boolean delegationEnabled) {
        this.delegationEnabled = delegationEnabled;
    }

    public boolean isDenied() {
        return denied;
    }

    public void setDenied(boolean denied) {
        if (denied) {
            state = DENIED_STATE;
        }
        this.denied = denied;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        denied = DENIED_STATE == state;        
        this.state = state;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getObjectAction() {
        return objectAction;
    }

    public void setObjectAction(String objectAction) {
        this.objectAction = objectAction;
    }

    public PermissionTypeFilter getCategory() {
        return category;
    }

    public void setCategory(PermissionTypeFilter category) {
        this.category = category;
    }

    public List<Integer> getChildrenPermitions() {
        return childrenPermitions;
    }
}
