package ua.com.manometer.dao.invoice;
import org.hibernate.Hibernate;
import ua.com.manometer.model.invoice.Invoice;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceDAOImpl implements InvoiceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveInvoice(Invoice invoice) {
        sessionFactory.getCurrentSession().saveOrUpdate(invoice);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Invoice> listInvoice() {
        return sessionFactory.getCurrentSession().createQuery("from Invoice").list();
    }

    @Override
    public void removeInvoice(Long id) {
        Invoice invoice = (Invoice) sessionFactory.getCurrentSession().load(Invoice.class, id);
        if (invoice != null) {
            sessionFactory.getCurrentSession().delete(invoice);
        }
    }

    @Override
    public Invoice getInvoice(Long id) {
        Invoice invoice = (Invoice) sessionFactory.getCurrentSession().get(Invoice.class, id);
        Hibernate.initialize(invoice.getPayments());
        Hibernate.initialize(invoice.getBooking());
        Hibernate.initialize(invoice.getInvoiceItems());
        Hibernate.initialize(invoice.getPayments());
        Hibernate.initialize(invoice.getShipments());
        return invoice;
    }

}
