package ua.com.manometr.dao.invoice;
import ua.com.manometr.model.invoice.InvoiceFilter;

import java.util.List;

public interface InvoiceFilterDAO {

	public void addInvoiceFilter(InvoiceFilter invoicefilter);

	public List<InvoiceFilter> listInvoiceFilter();

	public void removeInvoiceFilter(Long id);

}
