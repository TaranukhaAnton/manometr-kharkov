package ua.com.manometer.dao.price;
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
        sessionFactory.getCurrentSession().save(productionprice);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductionPrice> listProductionPrice() {
        return sessionFactory.getCurrentSession().createQuery("from ProductionPrice").list();
    }

    @Override
    public void removeProductionPrice(Long id) {
        ProductionPrice productionprice = (ProductionPrice) sessionFactory.getCurrentSession().load(ProductionPrice.class, id);
        if (productionprice != null) {
            sessionFactory.getCurrentSession().delete(productionprice);
        }
    }

}
