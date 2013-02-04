package ua.com.manometer.dao.invoice;
import ua.com.manometer.model.invoice.Invoice;
import ua.com.manometer.model.invoice.InvoiceItem;

import java.util.List;

public interface InvoiceItemDAO {

	public void saveInvoiceItem(InvoiceItem invoiceitem);

	public List<InvoiceItem> listInvoiceItem();

	public void removeInvoiceItem(Integer id);

    public InvoiceItem getInvoiceItem(Integer id);
}
