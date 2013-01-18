package ua.com.manometer.dao.invoice;
import ua.com.manometer.model.invoice.Booking;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookingDAOImpl implements BookingDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveBooking(Booking booking) {
        sessionFactory.getCurrentSession().saveOrUpdate(booking);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Booking> listBooking() {
        return sessionFactory.getCurrentSession().createQuery("from Booking").list();
    }

    @Override
    public void removeBooking(Long id) {
        Booking booking = (Booking) sessionFactory.getCurrentSession().load(Booking.class, id);
        if (booking != null) {
            sessionFactory.getCurrentSession().delete(booking);
        }
    }

}
