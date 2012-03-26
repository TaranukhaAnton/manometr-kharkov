package ua.com.manometer.dao.invoice;
import ua.com.manometer.model.invoice.Invoice;

import java.util.List;

public interface InvoiceDAO {

	public void addInvoice(Invoice invoice);

	public List<Invoice> listInvoice();

	public void removeInvoice(Long id);

}
