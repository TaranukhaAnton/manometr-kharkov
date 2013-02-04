package ua.com.manometer.dao.invoice;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import ua.com.manometer.model.Supplier;
import ua.com.manometer.model.invoice.Invoice;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.manometer.model.invoice.InvoiceFilter;

import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
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

    @SuppressWarnings("unchecked")
    @Override
    public List<Invoice> listFilteredInvoice(InvoiceFilter invoiceFilter) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Invoice.class);
        Conjunction conjunction = Restrictions.conjunction();

        if (!invoiceFilter.getStateFilter().isEmpty()) {
            conjunction.add(Restrictions.in("currentState", invoiceFilter.getStateFilter()));
        }
        if (!invoiceFilter.getUsers().isEmpty()) {
            conjunction.add(Restrictions.in("executor.id", invoiceFilter.getUsers()));
        }

        if (!invoiceFilter.getCurrencyFilter().isEmpty()) {
            List<Integer> suppliersIdList = getSuppliersByCurrencyIdList(invoiceFilter.getCurrencyFilter());
            conjunction.add(Restrictions.in("supplier.id",suppliersIdList ));
        }
        if (!invoiceFilter.getPurposeFilter().isEmpty()) {
            conjunction.add(Restrictions.in("purpose", invoiceFilter.getPurposeFilter()));
        }

        criteria.add(conjunction);


        return criteria.list();
    }

    List<Integer>  getSuppliersByCurrencyIdList( List<Integer> currencies){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Supplier.class);
        criteria.add(Restrictions.in("currency.id", currencies));
        List<Supplier> list = criteria.list();
        //todo transformer
        List<Integer> result = new LinkedList<Integer>();
        for (Supplier supplier : list) {
            result.add(supplier.getId());
        }
        return result;
    }
    
    @Override
    public void removeInvoice(Integer id) {
        Invoice invoice = (Invoice) sessionFactory.getCurrentSession().load(Invoice.class, id);
        if (invoice != null) {
            sessionFactory.getCurrentSession().delete(invoice);
        }
    }

    @Override
    public Invoice getInvoice(Integer id) {
        Invoice invoice = (Invoice) sessionFactory.getCurrentSession().get(Invoice.class, id);
        Hibernate.initialize(invoice.getPayments());
        Hibernate.initialize(invoice.getBooking());
        Hibernate.initialize(invoice.getInvoiceItems());
        Hibernate.initialize(invoice.getPayments());
        Hibernate.initialize(invoice.getShipments());
        return invoice;
    }

    @Override
    public Boolean checkPresence(Integer number, String numberModifier, Boolean invoice, Date date) {

        Session session = sessionFactory.getCurrentSession();
        BigInteger count = (BigInteger) session.createSQLQuery("select count(*) from invoice i where year(i.date) = year (?) and i.number = ? and i.numberModifier = ? and isInvoice = ? ")
                .setDate(0, date).setInteger(1, number).setString(2, numberModifier).setString(3, invoice ? "Y" : "N").uniqueResult();
        return count.compareTo(BigInteger.ZERO) == 1;
    }


}
