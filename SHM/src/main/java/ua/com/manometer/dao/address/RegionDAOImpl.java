package ua.com.manometer.dao.address;
import ua.com.manometer.model.address.Region;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegionDAOImpl implements RegionDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addRegion(Region region) {
        sessionFactory.getCurrentSession().save(region);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Region> listRegion() {
        return sessionFactory.getCurrentSession().createQuery("from Region").list();
    }



}
