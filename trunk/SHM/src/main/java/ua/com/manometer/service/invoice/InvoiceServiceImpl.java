package ua.com.manometer.service.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.invoice.InvoiceDAO;
import ua.com.manometer.model.invoice.Invoice;

import java.util.Date;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceDAO invoiceDAO;


    @Override
    @Transactional
    public Invoice getInvoice(Long id) {
        return invoiceDAO.getInvoice(id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	@Transactional
	public void saveInvoice(Invoice invoice) {
		invoiceDAO.saveInvoice(invoice);
	}

	@Override
	@Transactional
	public List<Invoice> listInvoice() {
		return invoiceDAO.listInvoice();
	}

	@Override
	@Transactional
	public void removeInvoice(Long id) {
		invoiceDAO.removeInvoice(id);
	}

    @Override
    @Transactional
    public Boolean checkPresence(Integer number, String numberModifier, Boolean invoice, Date date) {

          //todo !!!!!!
        return invoiceDAO.checkPresence( number,  numberModifier,  invoice,  date);
    }


}
