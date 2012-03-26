package ua.com.manometer.service.invoice;

import ua.com.manometer.model.invoice.Invoice;

import java.util.List;

public interface InvoiceService {

	public void addInvoice(Invoice invoice);

	public List<Invoice> listInvoice();

	public void removeInvoice(Long id);

}
