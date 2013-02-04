package ua.com.manometer.service.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.invoice.PaymentDAO;
import ua.com.manometer.model.invoice.Payment;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentDAO paymentDAO;

	@Override
	@Transactional
	public void addPayment(Payment payment) {
		paymentDAO.addPayment(payment);
	}

	@Override
	@Transactional
	public List<Payment> listPayment() {
		return paymentDAO.listPayment();
	}

	@Override
	@Transactional
	public void removePayment(Integer id) {
		paymentDAO.removePayment(id);
	}

}
