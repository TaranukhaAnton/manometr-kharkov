package ua.com.manometer.service.invoice;

import ua.com.manometer.model.invoice.InvoiceItem;

import java.util.List;

public interface InvoiceItemService {

	public void addInvoiceItem(InvoiceItem invoiceitem);

	public List<InvoiceItem> listInvoiceItem();

	public void removeInvoiceItem(Long id);

}
