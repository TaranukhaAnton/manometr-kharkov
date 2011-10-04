package ua.com.manometr.dao.price;
import ua.com.manometr.model.price.PriceFirstPart;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PriceFirstPartDAOImpl implements PriceFirstPartDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addPriceFirstPart(PriceFirstPart pricefirstpart) {
        sessionFactory.getCurrentSession().save(pricefirstpart);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PriceFirstPart> listPriceFirstPart() {
        return sessionFactory.getCurrentSession().createQuery("from PriceFirstPart").list();
    }

    @Override
    public void removePriceFirstPart(Long id) {
        PriceFirstPart pricefirstpart = (PriceFirstPart) sessionFactory.getCurrentSession().load(PriceFirstPart.class, id);
        if (pricefirstpart != null) {
            sessionFactory.getCurrentSession().delete(pricefirstpart);
        }
    }

}
