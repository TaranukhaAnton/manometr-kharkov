package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "granted_group_permission")
public class GroupPermissionGranting extends BaseModel {
    @Column(name = "is_delegation_enabled")
    private boolean delegationEnabled;

    @Column(name = "is_denied")
    private boolean denied;

    @Column(name = "permission_id")
    private int permissionId;

    @Column(name = "group_id")
    private int groupId;

    @Transient
    private String descr;

    @Transient
    private String objectAction;

    @Transient
    private int state = UserPermissionGranting.GRANTED_STATE;

    public void copyTo(GroupPermissionGranting copy) {
        super.copyTo(copy);
        copy.setDelegationEnabled(delegationEnabled);
        copy.setDenied(denied);
        copy.setGroupId(groupId);
        copy.setPermissionId(permissionId);
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
            state = UserPermissionGranting.DENIED_STATE;
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
        if (denied) {
            state = UserPermissionGranting.DENIED_STATE;
        }
        return state;
    }

    public void setState(int state) {
        if (UserPermissionGranting.DENIED_STATE == state) {
            denied = true;
        }
        this.state = state;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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
}