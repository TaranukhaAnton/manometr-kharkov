package ua.com.manometr.service.invoice;

import ua.com.manometr.model.invoice.InvoiceItem;

import java.util.List;

public interface InvoiceItemService {

	public void addInvoiceItem(InvoiceItem invoiceitem);

	public List<InvoiceItem> listInvoiceItem();

	public void removeInvoiceItem(Long id);

}
