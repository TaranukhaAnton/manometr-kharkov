package ua.com.manometr.dao.price;
import ua.com.manometr.model.price.OptionsPrice;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OptionsPriceDAOImpl implements OptionsPriceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addOptionsPrice(OptionsPrice optionsprice) {
        sessionFactory.getCurrentSession().save(optionsprice);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OptionsPrice> listOptionsPrice() {
        return sessionFactory.getCurrentSession().createQuery("from OptionsPrice").list();
    }

    @Override
    public void removeOptionsPrice(Long id) {
        OptionsPrice optionsprice = (OptionsPrice) sessionFactory.getCurrentSession().load(OptionsPrice.class, id);
        if (optionsprice != null) {
            sessionFactory.getCurrentSession().delete(optionsprice);
        }
    }

}
