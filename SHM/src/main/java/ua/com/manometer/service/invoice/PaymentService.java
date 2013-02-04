package ua.com.manometer.service.invoice;

import ua.com.manometer.model.invoice.Payment;

import java.util.List;

public interface PaymentService {

	public void addPayment(Payment payment);

	public List<Payment> listPayment();

	public void removePayment(Integer id);

}
