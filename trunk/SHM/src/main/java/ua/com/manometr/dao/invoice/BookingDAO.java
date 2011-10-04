package ua.com.manometr.dao.invoice;
import ua.com.manometr.model.invoice.Booking;

import java.util.List;

public interface BookingDAO {

	public void addBooking(Booking booking);

	public List<Booking> listBooking();

	public void removeBooking(Long id);

}
