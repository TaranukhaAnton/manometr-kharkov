package ua.com.manometer.dao.invoice;
import ua.com.manometer.model.invoice.Invoice;
import ua.com.manometer.model.invoice.InvoiceFilter;

import java.util.Date;
import java.util.List;

public interface InvoiceDAO {

	public void saveInvoice(Invoice invoice);

	public List<Invoice> listInvoice();
    public List<Invoice> listFilteredInvoice(InvoiceFilter invoiceFilter);

	public void removeInvoice(Long id);

    public  Invoice getInvoice(Long id);

    public Boolean checkPresence(Integer number, String numberModifier, Boolean invoice, Date date);
}
