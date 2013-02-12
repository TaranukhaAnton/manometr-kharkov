package com.sp.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */

@Entity
@Table(name = "permission")
public class Permission extends EnumModel {
    @Column(name = "object_action")
    private String objectAction;
    
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private PermissionTypeFilter category;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Permission.class,
            fetch = FetchType.EAGER
    )
    @JoinTable(name = "permission_parent",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "child_id")
    )
    private Set<Permission> children;

    public String getObjectAction() {
        return objectAction;
    }

    public void setObjectAction(String objectAction) {
        this.objectAction = objectAction;
    }

    public void copyTo(Permission copy) {
        super.copyTo(copy);
        copy.objectAction = objectAction;
        copy.category = category;
        copy.children = children;
    }

	public void setCategory(PermissionTypeFilter category) {
		this.category = category;
	}

	public PermissionTypeFilter getCategory() {
		return category;
	}

    public Set<Permission> getChildren() {
        return children;
    }

    public void setChildren(Set<Permission> children) {
        this.children = children;
    }
}
