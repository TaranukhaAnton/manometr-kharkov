package ua.com.manometr.service.invoice;

import ua.com.manometr.model.invoice.InvoiceFilter;

import java.util.List;

public interface InvoiceFilterService {

	public void addInvoiceFilter(InvoiceFilter invoicefilter);

	public List<InvoiceFilter> listInvoiceFilter();

	public void removeInvoiceFilter(Long id);

}
