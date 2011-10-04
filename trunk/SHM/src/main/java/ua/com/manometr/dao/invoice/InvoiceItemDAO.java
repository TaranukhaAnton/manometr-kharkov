package ua.com.manometr.dao.invoice;
import ua.com.manometr.model.invoice.InvoiceItem;

import java.util.List;

public interface InvoiceItemDAO {

	public void addInvoiceItem(InvoiceItem invoiceitem);

	public List<InvoiceItem> listInvoiceItem();

	public void removeInvoiceItem(Long id);

}
