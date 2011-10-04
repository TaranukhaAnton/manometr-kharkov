package ua.com.manometr.service.invoice;

import ua.com.manometr.model.invoice.Payment;

import java.util.List;

public interface PaymentService {

	public void addPayment(Payment payment);

	public List<Payment> listPayment();

	public void removePayment(Long id);

}
