package ua.com.manometer.service.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.invoice.InvoiceDAO;
import ua.com.manometer.model.invoice.Invoice;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceDAO invoiceDAO;

	@Override
	@Transactional
	public void addInvoice(Invoice invoice) {
		invoiceDAO.addInvoice(invoice);
	}

	@Override
	@Transactional
	public List<Invoice> listInvoice() {
		return invoiceDAO.listInvoice();
	}

	@Override
	@Transactional
	public void removeInvoice(Long id) {
		invoiceDAO.removeInvoice(id);
	}

}
