package ua.com.manometer.dao.invoice;
import ua.com.manometer.model.invoice.Booking;

import java.util.List;

public interface BookingDAO {

	public void addBooking(Booking booking);

	public List<Booking> listBooking();

	public void removeBooking(Long id);

}
