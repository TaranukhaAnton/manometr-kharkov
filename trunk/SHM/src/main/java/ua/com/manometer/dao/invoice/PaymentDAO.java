package ua.com.manometer.dao.invoice;
import ua.com.manometer.model.invoice.Payment;

import java.util.List;

public interface PaymentDAO {

	public void addPayment(Payment payment);

	public List<Payment> listPayment();

	public void removePayment(Integer id);

}
