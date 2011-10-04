package ua.com.manometr.service.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.invoice.InvoiceFilterDAO;
import ua.com.manometr.model.invoice.InvoiceFilter;

import java.util.List;

@Service
public class InvoiceFilterServiceImpl implements InvoiceFilterService {

	@Autowired
	private InvoiceFilterDAO invoicefilterDAO;

	@Override
	@Transactional
	public void addInvoiceFilter(InvoiceFilter invoicefilter) {
		invoicefilterDAO.addInvoiceFilter(invoicefilter);
	}

	@Override
	@Transactional
	public List<InvoiceFilter> listInvoiceFilter() {
		return invoicefilterDAO.listInvoiceFilter();
	}

	@Override
	@Transactional
	public void removeInvoiceFilter(Long id) {
		invoicefilterDAO.removeInvoiceFilter(id);
	}

}
