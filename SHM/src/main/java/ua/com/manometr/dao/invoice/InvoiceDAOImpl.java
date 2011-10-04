package ua.com.manometr.dao.invoice;
import ua.com.manometr.model.invoice.Invoice;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceDAOImpl implements InvoiceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addInvoice(Invoice invoice) {
        sessionFactory.getCurrentSession().save(invoice);
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

}
