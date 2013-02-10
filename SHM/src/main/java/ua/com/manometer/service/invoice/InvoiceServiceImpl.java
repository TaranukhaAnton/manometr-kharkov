package ua.com.manometer.service.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.invoice.InvoiceDAO;
import ua.com.manometer.model.invoice.Invoice;
import ua.com.manometer.model.invoice.InvoiceFilter;
import ua.com.manometer.model.invoice.InvoiceItem;
import ua.com.manometer.util.InvoiceUtils;

import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceDAO invoiceDAO;


    @Override
    @Transactional
    public Invoice getInvoice(Integer id) {
        return invoiceDAO.getInvoice(id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional
    public void saveInvoice(Invoice invoice) {
        updateCounters(invoice);
        updateMoneyFields(invoice);
        invoiceDAO.saveInvoice(invoice);

        System.out.println( invoice.getTotalPayments());
        System.out.println( invoice.getTotal());
        System.out.println( invoice.getSum());
        System.out.println(invoice.getPaymentPercent());
        System.out.println( invoice.isPaymentMade());
        System.out.println( invoice.isDeliveryMade());
        System.out.println(invoice.isAnyGoodsShipped());
        System.out.println(invoice.getNdsPayment());
        System.out.println( invoice.getAdditionToPrice());


        
        
        
        
        
    }

    @Override
    @Transactional
    public List<Invoice> listInvoice() {
        return invoiceDAO.listInvoice();
    }

    @Override
    @Transactional
    public List<Invoice> listFilteredInvoice(InvoiceFilter invoiceFilter) {
        return invoiceDAO.listFilteredInvoice(invoiceFilter);
    }

    @Override
    @Transactional
    public void removeInvoice(Integer id) {
        invoiceDAO.removeInvoice(id);
    }

    @Override
    @Transactional
    public Boolean checkPresence(Integer number, String numberModifier, Boolean invoice, Date date) {
        return invoiceDAO.checkPresence(number, numberModifier, invoice, date);
    }

    void updateCounters(Invoice invoice) {
        int t0 = 0;
        int t1 = 0;
        int t2 = 0;
        int t3 = 0;
        int t4 = 0;
        int t5 = 0;

        List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
        for (InvoiceItem ii : invoiceItems) {

            switch (ii.getType()) {
                case 0:
                case 1:
                case 2:
                case 3:
                    t0 += ii.getQuantity();
                    break;
                case 4:
                    t1 += ii.getQuantity();
                    break;

                case 5:
                    t3 += ii.getQuantity();
                    break;

                case 6:
                    t2 += ii.getQuantity();
                    break;

                case 7:
                case 8:
                    t4 += ii.getQuantity();
                    break;
                case 9:
                    t5 += ii.getQuantity();
                    break;
            }
            invoice.setT0(t0 == 0 ? null : t0);
            invoice.setT1(t1 == 0 ? null : t1);
            invoice.setT2(t2 == 0 ? null : t2);
            invoice.setT3(t3 == 0 ? null : t3);
            invoice.setT4(t4 == 0 ? null : t4);
            invoice.setT5(t5 == 0 ? null : t5);
        }


    }

    void updateMoneyFields(Invoice invoice) {
        InvoiceUtils.setupInvoice(invoice);
    }


}
