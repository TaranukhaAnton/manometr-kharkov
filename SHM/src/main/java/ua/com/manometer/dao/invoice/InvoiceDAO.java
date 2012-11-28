package ua.com.manometer.dao.invoice;
import ua.com.manometer.model.invoice.Invoice;

import java.util.List;

public interface InvoiceDAO {

	public void saveInvoice(Invoice invoice);

	public List<Invoice> listInvoice();

	public void removeInvoice(Long id);

    public  Invoice getInvoice(Long id);
}
