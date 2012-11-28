package ua.com.manometer.service.invoice;

import ua.com.manometer.model.invoice.Invoice;

import java.util.Date;
import java.util.List;

public interface InvoiceService {

	public Invoice getInvoice(Long id);

    public void saveInvoice(Invoice invoice);

	public List<Invoice> listInvoice();

	public void removeInvoice(Long id);

    public Boolean checkPresence(Integer number, String numberModifier, Boolean invoice, Date date);
}
