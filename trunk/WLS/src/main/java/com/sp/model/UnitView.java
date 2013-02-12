package com.sp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
@Entity
@Table(name = "unit_view")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class UnitView extends AccountedMovableItemView {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST},
               fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private UnitView parent;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Vehicle.class,
            fetch = FetchType.LAZY
    )
    @JoinTable(name = "map_unit_view_unit",
        joinColumns=@JoinColumn(name="group_id"),
        inverseJoinColumns=@JoinColumn(name="vehicle_id")
    )
    private Set<Vehicle> groupVehicles;

    public void copyTo(UnitView copy) {
        super.copyTo(copy);

        if (parent != null) {
            UnitView groupCopy = new UnitView();
            parent.copyTo(groupCopy);
            copy.setParent(groupCopy);
        }

        if (groupVehicles != null) {
            Set<Vehicle> vehicles = new HashSet<Vehicle>(groupVehicles.size());
            for (Vehicle obj : groupVehicles) {
                Vehicle copyVehicle = new Vehicle();
                obj.copyTo(copyVehicle);
                vehicles.add(copyVehicle);
            }
            copy.setGroupVehicles(vehicles);
        }
    }

    public UnitView getParent() {
        return parent;
    }

    public void setParent(UnitView parent) {
        this.parent = parent;
    }

    public Set<Vehicle> getGroupVehicles() {
        return groupVehicles;
    }

    public void setGroupVehicles(Set<Vehicle> groupVehicle) {
        this.groupVehicles = groupVehicle;
    }

   
}
