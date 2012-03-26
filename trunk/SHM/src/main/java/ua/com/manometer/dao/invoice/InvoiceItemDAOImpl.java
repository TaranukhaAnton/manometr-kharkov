package ua.com.manometer.dao.invoice;
import ua.com.manometer.model.invoice.InvoiceItem;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceItemDAOImpl implements InvoiceItemDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addInvoiceItem(InvoiceItem invoiceitem) {
        sessionFactory.getCurrentSession().save(invoiceitem);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<InvoiceItem> listInvoiceItem() {
        return sessionFactory.getCurrentSession().createQuery("from InvoiceItem").list();
    }

    @Override
    public void removeInvoiceItem(Long id) {
        InvoiceItem invoiceitem = (InvoiceItem) sessionFactory.getCurrentSession().load(InvoiceItem.class, id);
        if (invoiceitem != null) {
            sessionFactory.getCurrentSession().delete(invoiceitem);
        }
    }

}
