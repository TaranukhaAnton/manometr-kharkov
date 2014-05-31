package ua.com.manometer.service.invoice;

import ua.com.manometer.model.invoice.Invoice;
import ua.com.manometer.model.invoice.InvoiceFilter;

import java.util.Date;
import java.util.List;

public interface InvoiceService {

	public Invoice getInvoice(Integer id);

    public void saveInvoice(Invoice invoice);

    public void updateInvoice(Integer id);

	public List<Invoice> listInvoice();

    public List<Invoice> listFilteredInvoice(InvoiceFilter invoiceFilter);

	public void removeInvoice(Integer id);

    public Boolean checkPresence(Integer number, String numberModifier, Boolean invoice, Date date);
}
