package ua.com.manometr.dao.invoice;
import ua.com.manometr.model.invoice.Payment;

import java.util.List;

public interface PaymentDAO {

	public void addPayment(Payment payment);

	public List<Payment> listPayment();

	public void removePayment(Long id);

}
