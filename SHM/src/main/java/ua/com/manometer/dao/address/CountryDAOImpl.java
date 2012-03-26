package ua.com.manometer.dao.address;
import ua.com.manometer.model.address.Country;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryDAOImpl implements CountryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addCountry(Country country) {
        sessionFactory.getCurrentSession().save(country);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Country> listCountry() {
        return sessionFactory.getCurrentSession().createQuery("from Country").list();
    }

    @Override
    public void removeCountry(Long id) {
        Country country = (Country) sessionFactory.getCurrentSession().load(Country.class, id);
        if (country != null) {
            sessionFactory.getCurrentSession().delete(country);
        }
    }

    @Override
    public Country getCountry(Long countryId) {
        Country country = (Country) sessionFactory.getCurrentSession().get(Country.class, countryId);
        return country;
    }


}
