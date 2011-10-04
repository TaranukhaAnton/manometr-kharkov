package ua.com.manometr.dao.invoice;
import ua.com.manometr.model.invoice.ShipmentMediator;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShipmentMediatorDAOImpl implements ShipmentMediatorDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addShipmentMediator(ShipmentMediator shipmentmediator) {
        sessionFactory.getCurrentSession().save(shipmentmediator);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ShipmentMediator> listShipmentMediator() {
        return sessionFactory.getCurrentSession().createQuery("from ShipmentMediator").list();
    }

    @Override
    public void removeShipmentMediator(Long id) {
        ShipmentMediator shipmentmediator = (ShipmentMediator) sessionFactory.getCurrentSession().load(ShipmentMediator.class, id);
        if (shipmentmediator != null) {
            sessionFactory.getCurrentSession().delete(shipmentmediator);
        }
    }

}
