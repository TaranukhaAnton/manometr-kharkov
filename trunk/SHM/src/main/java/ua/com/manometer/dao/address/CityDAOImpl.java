package ua.com.manometer.dao.address;
import org.hibernate.criterion.Restrictions;
import ua.com.manometer.model.address.City;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityDAOImpl implements CityDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<City> listCityForArea(Integer areaId) {
        return sessionFactory.getCurrentSession().createCriteria(City.class).
                add(Restrictions.eq("area.id", areaId)).list();
    }

}
