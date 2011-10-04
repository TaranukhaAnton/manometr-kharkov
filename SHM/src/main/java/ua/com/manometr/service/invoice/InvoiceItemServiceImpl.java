package ua.com.manometr.service.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.invoice.InvoiceItemDAO;
import ua.com.manometr.model.invoice.InvoiceItem;

import java.util.List;

@Service
public class InvoiceItemServiceImpl implements InvoiceItemService {

	@Autowired
	private InvoiceItemDAO invoiceitemDAO;

	@Override
	@Transactional
	public void addInvoiceItem(InvoiceItem invoiceitem) {
		invoiceitemDAO.addInvoiceItem(invoiceitem);
	}

	@Override
	@Transactional
	public List<InvoiceItem> listInvoiceItem() {
		return invoiceitemDAO.listInvoiceItem();
	}

	@Override
	@Transactional
	public void removeInvoiceItem(Long id) {
		invoiceitemDAO.removeInvoiceItem(id);
	}

}
