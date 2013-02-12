package com.sp.dto.mobile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg
 * Date: 27.08.12
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name="vehicles")
public class VehicleListMobile extends MobileSerializableList {
    private int count;
    private List<VehicleMobile> vehicles;

    public VehicleListMobile() {}

    public VehicleListMobile(List<VehicleMobile> vehicles) {
        this.vehicles = vehicles;
        this.count = vehicles.size();
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    @XmlElement(name="vehicles")
    public List<VehicleMobile> getVehicles() {
        return vehicles;
    }
    public void setVehicles(List<VehicleMobile> vehicles) {
        this.vehicles = vehicles;
    }

}
