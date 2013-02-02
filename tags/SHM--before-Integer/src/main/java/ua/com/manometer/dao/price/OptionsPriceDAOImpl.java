package ua.com.manometer.dao.price;
import ua.com.manometer.model.price.IdOptionsPrice;
import ua.com.manometer.model.price.OptionsPrice;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class OptionsPriceDAOImpl implements OptionsPriceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public OptionsPrice getOptionsPrice(Integer type, Integer isp, String param) {
        IdOptionsPrice id = new IdOptionsPrice(type, isp, param);
        return (OptionsPrice)sessionFactory.getCurrentSession().get(OptionsPrice.class, id);
    }


    @Override
    public List<OptionsPrice> listOptionsPrice() {
        return sessionFactory.getCurrentSession().createCriteria(OptionsPrice.class).list();
    }

    @Override
    public void updateOptionsPrice(OptionsPrice optionsPrice) {
            sessionFactory.getCurrentSession().update(optionsPrice);
    }

}
