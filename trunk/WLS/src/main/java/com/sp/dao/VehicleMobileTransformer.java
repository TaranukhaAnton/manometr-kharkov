package com.sp.dao;

import com.sp.dto.mobile.VehicleMobile;
import com.sp.model.Vehicle;
import org.hibernate.transform.ResultTransformer;

import java.util.List;

/** Transforms Vehicle entity and ids of unit views into VehicleMobile class which is DTO for the REST API.
 *
 * Created by IntelliJ IDEA.
 * User: Oleg
 * Date: 02.10.12
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public class VehicleMobileTransformer implements ResultTransformer {

    @Override
    public Object transformTuple(Object[] objects, String[] strings) {
        Vehicle vehicle = (Vehicle) objects[0];
        String ids = (String) objects[1];

        VehicleMobile vehicleMobile = new VehicleMobile(vehicle);
        vehicleMobile.setUnitViewIds(ids);
        return vehicleMobile;
    }

    @Override
    public List transformList(List list) {
        return list;
    }
}
