package ua.com.manometer.dao.invoice;
import ua.com.manometer.model.invoice.InvoiceFilter;

import java.util.List;

public interface InvoiceFilterDAO {

	public void addInvoiceFilter(InvoiceFilter invoicefilter);

	public List<InvoiceFilter> listInvoiceFilter();

	public void removeInvoiceFilter(Long id);

}
