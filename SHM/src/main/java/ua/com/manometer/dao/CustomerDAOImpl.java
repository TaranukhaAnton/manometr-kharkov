package ua.com.manometer.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.manometer.model.Customer;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addCustomer(Customer customer) {
        sessionFactory.getCurrentSession().saveOrUpdate(customer);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Customer> listCustomer() {
        return sessionFactory.getCurrentSession().createQuery("from Customer").list();
    }

    @Override
    public void removeCustomer(Integer id) {
        Customer customer = (Customer) sessionFactory.getCurrentSession().load(Customer.class, id);
        if (customer != null) {
            sessionFactory.getCurrentSession().delete(customer);
        }
    }

    @Override
    public Customer getCustomer(Integer id) {
        Customer customer = (Customer) sessionFactory.getCurrentSession().get(Customer.class, id);
        return customer;
    }

    @Override
    public List<String> findByShortNameExample(String customerTemplate) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT DISTINCT  c.shortName FROM customer c WHERE c.status = 'Y' AND c.shortName LIKE '" + customerTemplate + "%'");

        List<String> list = (List<String>) query.list();
        return list;

    }

    @Override
    public Boolean isCustomerPresent(String customer) {
        Query query = sessionFactory.getCurrentSession().
                createSQLQuery("select IF(count(*)>0, 'true','false' ) from Customer c where c.shortName = '" + customer + "'  and c.status = 'Y'");
        return new Boolean((String) query.uniqueResult());
    }

    @Override
    public Customer getCustomerByShortName(String shortName) {
        return (Customer) sessionFactory.getCurrentSession().createCriteria(Customer.class).
                add(Restrictions.eq("status", true)).add(Restrictions.eq("shortName", shortName)).uniqueResult();
    }


}