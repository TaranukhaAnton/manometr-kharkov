package ua.com.manometer.service.invoice;

import ua.com.manometer.model.invoice.InvoiceFilter;

import java.util.List;

public interface InvoiceFilterService {

	public void addInvoiceFilter(InvoiceFilter invoicefilter);

	public List<InvoiceFilter> listInvoiceFilter();

	public void removeInvoiceFilter(Integer id);

}
