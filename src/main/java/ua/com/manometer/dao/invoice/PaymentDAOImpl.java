package ua.com.manometer.dao.invoice;
import ua.com.manometer.model.invoice.Payment;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentDAOImpl implements PaymentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addPayment(Payment payment) {
        sessionFactory.getCurrentSession().save(payment);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Payment> listPayment() {
        return sessionFactory.getCurrentSession().createQuery("from Payment").list();
    }

    @Override
    public void removePayment(Long id) {
        Payment payment = (Payment) sessionFactory.getCurrentSession().load(Payment.class, id);
        if (payment != null) {
            sessionFactory.getCurrentSession().delete(payment);
        }
    }

}
