package ua.com.manometer.dao.invoice;
import ua.com.manometer.model.invoice.Booking;

import java.util.Date;
import java.util.List;

public interface BookingDAO {

	public void saveBooking(Booking booking);

	public List<Booking> listBooking();

	public void removeBooking(Long id);

    Boolean checkPresence(Integer number, String numberModifier, Date date);
}
