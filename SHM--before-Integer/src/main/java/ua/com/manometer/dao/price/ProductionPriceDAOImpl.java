package ua.com.manometer.dao.price;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import ua.com.manometer.model.price.PriceFirstPart;
import ua.com.manometer.model.price.ProductionPrice;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductionPriceDAOImpl implements ProductionPriceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addProductionPrice(ProductionPrice productionprice) {
        sessionFactory.getCurrentSession().saveOrUpdate(productionprice);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductionPrice> listProductionPriceByType(Integer type) {
        return sessionFactory.getCurrentSession().createCriteria(ProductionPrice.class).add(Restrictions.eq("type", type)).list();
    }

    @Override
    public void removeProductionPrice(Long id) {
        ProductionPrice productionprice = (ProductionPrice) sessionFactory.getCurrentSession().get(ProductionPrice.class, id);
        if (productionprice != null) {
            sessionFactory.getCurrentSession().delete(productionprice);
        }
    }

}
