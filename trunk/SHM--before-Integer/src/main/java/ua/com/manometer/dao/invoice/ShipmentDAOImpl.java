package ua.com.manometer.dao.invoice;
import ua.com.manometer.model.invoice.Shipment;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShipmentDAOImpl implements ShipmentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addShipment(Shipment shipment) {
        sessionFactory.getCurrentSession().save(shipment);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Shipment> listShipment() {
        return sessionFactory.getCurrentSession().createQuery("from Shipment").list();
    }

    @Override
    public void removeShipment(Long id) {
        Shipment shipment = (Shipment) sessionFactory.getCurrentSession().load(Shipment.class, id);
        if (shipment != null) {
            sessionFactory.getCurrentSession().delete(shipment);
        }
    }

}
