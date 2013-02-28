package org.krams.tutorial.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.krams.tutorial.domain.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehicleDao {

    @Autowired
    SessionFactory sessionFactory;


    public List<Vehicle> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Vehicle.class);
        return criteria.list();
    }


    public void saveOrUpdate(Vehicle vehicle) {
        sessionFactory.getCurrentSession().saveOrUpdate(vehicle);
    }

}
