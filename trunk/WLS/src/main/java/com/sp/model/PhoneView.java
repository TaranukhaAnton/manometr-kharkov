package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ananda
 * Date: Mar 21, 2009
 * Time: 9:47:58 AM
 */
@Entity
@Table(name = "phone_view")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class PhoneView extends AccountedMovableItemView {
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private PhoneView parent;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Phone.class,
            fetch = FetchType.LAZY
    )
    @JoinTable(name = "map_phone_unit",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    private Set<Phone> groupPhone;

    public void copyTo(PhoneView copy) {
        super.copyTo(copy);
        if (parent != null) {
            PhoneView groupCopy = new PhoneView();
            parent.copyTo(groupCopy);
            copy.setParent(groupCopy);
        }

        if (groupPhone != null) {
            Set<Phone> phones = new HashSet<Phone>(groupPhone.size());
            for (Phone obj : groupPhone) {
                Phone copyPhone = new Phone();
                obj.copyTo(copyPhone);
                phones.add(copyPhone);
            }
            copy.setGroupPhone(phones);
        }
    }

    public PhoneView getParent() {
        return parent;
    }

    public void setParent(PhoneView parent) {
        this.parent = parent;
    }

    public Set<Phone> getGroupPhone() {
        return groupPhone;
    }

    public void setGroupPhone(Set<Phone> groupPhone) {
        this.groupPhone = groupPhone;
    }
}

