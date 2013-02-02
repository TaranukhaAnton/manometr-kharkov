package ua.com.manometer.service.invoice;

import ua.com.manometer.model.invoice.Invoice;
import ua.com.manometer.model.invoice.InvoiceItem;
import ua.com.manometer.model.invoice.PressureSensor;
import ua.com.manometer.model.invoice.Production;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceItemService {

    public InvoiceItem getInvoiceItem(Long id);

	public void saveInvoiceItem(InvoiceItem invoiceitem);

	public List<InvoiceItem> listInvoiceItem();

	public void removeInvoiceItem(Long id);

    public void setupMoneyFields(PressureSensor item ,BigDecimal koef );

    public void setupMoneyFields(Production item, BigDecimal koef);

}
