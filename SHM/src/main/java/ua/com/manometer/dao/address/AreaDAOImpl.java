package ua.com.manometer.dao.address;

import org.hibernate.criterion.Restrictions;
import ua.com.manometer.model.address.Area;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AreaDAOImpl implements AreaDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addArea(Area area) {
        sessionFactory.getCurrentSession().save(area);
    }



    @Override
    public List<Area> listAreaForCountry(Integer countryId) {
        return sessionFactory.getCurrentSession().createCriteria(Area.class).
                add(Restrictions.eq("country.id", countryId)).list();
    }

}
