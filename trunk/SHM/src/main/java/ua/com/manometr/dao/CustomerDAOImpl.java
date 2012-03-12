package ua.com.manometr.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.manometr.model.Customer;
import ua.com.manometr.model.User;
import ua.com.manometr.model.address.Area;

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
    public void removeCustomer(Long id) {
        Customer customer = (Customer) sessionFactory.getCurrentSession().load(Customer.class, id);
        if (customer != null) {
            sessionFactory.getCurrentSession().delete(customer);
        }
    }

    @Override
    public Customer getCustomer(Long id) {
        Customer customer = (Customer) sessionFactory.getCurrentSession().get(Customer.class, id);
        return customer;
    }

    @Override
    public List<Customer> findByShortNameExample(String customerTemplate) {

        return sessionFactory.getCurrentSession().createCriteria(Customer.class).
                add(Restrictions.eq("status", true)).add(Restrictions.like("shortName", customerTemplate + "%")).list();

    }

    @Override
    public Customer getCustomerByShortName(String shortName) {
        return (Customer) sessionFactory.getCurrentSession().createCriteria(Customer.class).
                add(Restrictions.eq("status", true)).add(Restrictions.eq("shortName", shortName)).uniqueResult();
    }


}