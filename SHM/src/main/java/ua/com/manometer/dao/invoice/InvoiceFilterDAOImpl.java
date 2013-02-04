package ua.com.manometer.dao.invoice;
import ua.com.manometer.model.invoice.InvoiceFilter;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceFilterDAOImpl implements InvoiceFilterDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addInvoiceFilter(InvoiceFilter invoicefilter) {
        sessionFactory.getCurrentSession().save(invoicefilter);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<InvoiceFilter> listInvoiceFilter() {
        return sessionFactory.getCurrentSession().createQuery("from InvoiceFilter").list();
    }

    @Override
    public void removeInvoiceFilter(Integer id) {
        InvoiceFilter invoicefilter = (InvoiceFilter) sessionFactory.getCurrentSession().load(InvoiceFilter.class, id);
        if (invoicefilter != null) {
            sessionFactory.getCurrentSession().delete(invoicefilter);
        }
    }

}
