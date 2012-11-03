package ua.com.manometer.dao.price;
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
        //todo!!!!!!!!!!!!!!!!!!
        return (OptionsPrice)sessionFactory.getCurrentSession().createCriteria(OptionsPrice.class).list().get(0);
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
