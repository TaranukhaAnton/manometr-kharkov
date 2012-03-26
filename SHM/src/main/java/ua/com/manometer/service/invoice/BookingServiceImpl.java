package ua.com.manometer.service.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.invoice.BookingDAO;
import ua.com.manometer.model.invoice.Booking;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingDAO bookingDAO;

	@Override
	@Transactional
	public void addBooking(Booking booking) {
		bookingDAO.addBooking(booking);
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

}
