package ua.com.manometer.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.manometer.model.Currency;

import java.util.List;
@Repository
public class CurrencyDAOImpl implements CurrencyDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addCurrency(Currency currency) {
        sessionFactory.getCurrentSession().save(currency);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Currency> listCurrency() {
        return sessionFactory.getCurrentSession().createQuery("from Currency").list();
    }

    @Override
    public void removeCurrency(Integer id) {
        Currency currency = (Currency) sessionFactory.getCurrentSession().load(Currency.class, id);
        if (currency != null) {
            sessionFactory.getCurrentSession().delete(currency);
        }
    }

}
