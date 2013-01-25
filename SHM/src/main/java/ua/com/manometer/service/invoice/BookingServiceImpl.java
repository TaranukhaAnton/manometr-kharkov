package ua.com.manometer.service.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.BaseDAO;
import ua.com.manometer.dao.invoice.BookingDAO;
import ua.com.manometer.model.invoice.Booking;

import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDAO bookingDAO;

    @Autowired
    BaseDAO baseDAO;

    @Override
    @Transactional
    public void saveBooking(Booking booking) {
        bookingDAO.saveBooking(booking);
    }

    @Override
    @Transactional
    public List<Booking> listBooking() {
        return bookingDAO.listBooking();
    }

    @Override
    @Transactional
    public void removeBooking(Long id) {
        bookingDAO.removeBooking(id);
    }

    @Override
    @Transactional
    public Booking getBooking(Long id) {
        Booking booking = (Booking) baseDAO.getById(id, Booking.class);
        return booking;
    }

    @Override
    @Transactional
    public Boolean checkPresence(Integer number, String numberModifier, Date date) {

        return bookingDAO.checkPresence(number,numberModifier,date);
        //todo !!!!1111
        //return false;
    }

}
