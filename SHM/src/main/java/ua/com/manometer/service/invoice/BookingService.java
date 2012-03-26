package ua.com.manometer.service.invoice;

import ua.com.manometer.model.invoice.Booking;

import java.util.List;

public interface BookingService {

	public void addBooking(Booking booking);

	public List<Booking> listBooking();

	public void removeBooking(Long id);

}
