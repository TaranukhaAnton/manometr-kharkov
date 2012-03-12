package ua.com.manometr.dao.address;
import org.hibernate.criterion.Restrictions;
import ua.com.manometr.model.address.Area;
import ua.com.manometr.model.address.City;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityDAOImpl implements CityDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addCity(City city) {
        sessionFactory.getCurrentSession().save(city);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<City> listCity() {
        return sessionFactory.getCurrentSession().createQuery("from City").list();
    }

    @Override
    public void removeCity(Long id) {
        City city = (City) sessionFactory.getCurrentSession().load(City.class, id);
        if (city != null) {
            sessionFactory.getCurrentSession().delete(city);
        }
    }

    @Override
    public List<City> listCityForArea(Long areaId) {
        return sessionFactory.getCurrentSession().createCriteria(City.class).
                add(Restrictions.eq("area.id", areaId)).list();
    }

}
