package ua.com.manometer.service.invoice;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.manometer.model.invoice.Invoice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class InvoiceServiceImplTest {
    @Autowired
    InvoiceService invoiceService;

    DateFormat f = new SimpleDateFormat("dd.MM.yyyy");

    @Test
    public void testGetInvoice() throws Exception {
        Invoice invoice = invoiceService.getInvoice(119L);
        System.out.println("invoice = " + invoice);

    }

    @Test
    public void testSaveInvoice() throws Exception {

    }

    @Test
    public void testListInvoice() throws Exception {

    }

    @Test
    public void testRemoveInvoice() throws Exception {

    }

    @Test
    public void testCheckPresence() throws Exception {
        Boolean a = invoiceService.checkPresence(234, "a", true, new Date());
        Assert.assertTrue(a);

        a = invoiceService.checkPresence(234, "b", true, new Date());
        Assert.assertFalse(a);

        a = invoiceService.checkPresence(234, "a", true, f.parse("24.05.2011"));
        Assert.assertFalse(a);
    }
}
