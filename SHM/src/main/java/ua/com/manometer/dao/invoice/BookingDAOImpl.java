package ua.com.manometer.dao.invoice;
import org.hibernate.classic.Session;
import ua.com.manometer.model.invoice.Booking;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
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
    public void removeBooking(Integer id) {
        Booking booking = (Booking) sessionFactory.getCurrentSession().load(Booking.class, id);
        if (booking != null) {
            sessionFactory.getCurrentSession().delete(booking);
        }
    }


    @Override
    public Boolean checkPresence(Integer number, String numberModifier, Date date) {
        Session session = sessionFactory.getCurrentSession();
        BigInteger count = (BigInteger) session.createSQLQuery("select count(*) from booking b where year(b.date) = year (?) and b.number = ? and b.numberModifier = ? ")
                .setDate(0, date).setInteger(1, number).setString(2, numberModifier).uniqueResult();
        return   count.compareTo(BigInteger.ZERO) == 1;
    }
}
