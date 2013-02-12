package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 25.06.2009
 * Time: 20:12:51
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "handheld_view")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class HandheldView extends AccountedMovableItemView {
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private HandheldView parent;
//
//    @Column(name = "created_date")
//    private Date createdDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Handheld.class,
            fetch = FetchType.LAZY
    )
    @JoinTable(name = "map_handheld_view",
        joinColumns=@JoinColumn(name="group_id"),
        inverseJoinColumns=@JoinColumn(name="unit_id")
    )
    private Set<Handheld> groupHandheld;

//    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
//    @JoinColumn(name = "account_id")
//    private Account account;
//
//    @Column(name = "notes")
//    private String notes;
//
//    @Transient
//    private boolean isUnassignedGroup;


    public void copyTo(HandheldView copy) {
        super.copyTo(copy);
//        copy.setCreatedDate(createdDate);
//        if (account != null){
//            Account acc = new Account();
//            account.copyTo(acc);
//            copy.setAccount(acc);
//        }
//        copy.notes = notes;
//        copy.isUnassignedGroup = isUnassignedGroup;
//
        if (parent != null) {
            HandheldView groupCopy = new HandheldView();
            parent.copyTo(groupCopy);
            copy.setParent(groupCopy);
        }

        if (groupHandheld != null) {
            Set<Handheld> handhelds = new HashSet<Handheld>(groupHandheld.size());
            for (Handheld obj : groupHandheld) {
                Handheld copyHandheld = new Handheld();
                obj.copyTo(copyHandheld);
                handhelds.add(copyHandheld);
            }
            copy.setGroupHandheld(handhelds);
        }
    }

//    public boolean getIsUnassignedGroup() {
//        return isUnassignedGroup;
//    }
//
//    public void setIsUnassignedGroup(boolean unassignedGroup) {
//        isUnassignedGroup = unassignedGroup;
//    }
//
    public HandheldView getParent() {
        return parent;
    }

    public void setParent(HandheldView parent) {
        this.parent = parent;
    }
//
//    public Date getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(Date createdDate) {
//        this.createdDate = createdDate;
//    }

    public Set<Handheld> getGroupHandheld() {
        return groupHandheld;
    }

    public void setGroupHandheld(Set<Handheld> groupHandheld) {
        this.groupHandheld = groupHandheld;
    }

//    public Account getAccount() {
//        return account;
//    }
//
//    public void setAccount(Account account) {
//        this.account = account;
//    }
//
//    public String getNotes() {
//        return notes;
//    }
//
//    public void setNotes(String notes) {
//        this.notes = notes;
//    }

}
