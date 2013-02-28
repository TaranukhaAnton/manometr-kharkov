package org.krams.tutorial.service;

import org.krams.tutorial.dao.GroupDao;
import org.krams.tutorial.dao.VehicleDao;
import org.krams.tutorial.domain.UnitView;
import org.krams.tutorial.domain.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class VehicleService {

    @Autowired
    VehicleDao vehicleDao;

    @Autowired
    private GroupDao groupDao;

    @Transactional
    public List<Vehicle> findAll() {
        return vehicleDao.findAll();
    }

    @Transactional
    public void saveOrUpdate(Vehicle vehicle) {
        vehicleDao.saveOrUpdate(vehicle);
    }

    @Transactional
    public Collection<Vehicle> getByUnitViewId(int unitViewId) {
        UnitView unitView = groupDao.findById(unitViewId,true);
        Set<Vehicle> objSet = unitView.getGroupVehicles();
        return objSet;
    }


}
