package ua.com.manometr.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.manometr.model.Customer;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addCustomer(Customer customer) {
        sessionFactory.getCurrentSession().save(customer);
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

}