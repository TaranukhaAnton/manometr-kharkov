package ua.com.manometr.dao.invoice;
import ua.com.manometr.model.invoice.Invoice;

import java.util.List;

public interface InvoiceDAO {

	public void addInvoice(Invoice invoice);

	public List<Invoice> listInvoice();

	public void removeInvoice(Long id);

}
