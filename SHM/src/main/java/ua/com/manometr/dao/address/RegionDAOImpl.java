package ua.com.manometr.dao.address;
import ua.com.manometr.model.address.Region;

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

    @Override
    public void removeRegion(Long id) {
        Region region = (Region) sessionFactory.getCurrentSession().load(Region.class, id);
        if (region != null) {
            sessionFactory.getCurrentSession().delete(region);
        }
    }

}
