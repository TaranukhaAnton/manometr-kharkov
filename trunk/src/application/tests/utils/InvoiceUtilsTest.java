package application.tests.utils;

import application.data.invoice.Booking;
import application.data.invoice.Invoice;
import application.data.invoice.Production;
import application.utils.InvoiceUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static application.utils.InvoiceUtils.filterInvoicesWithDebts;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 03.07.11
 * Time: 23:54
 * To change this template use File | Settings | File Templates.
 */
public class InvoiceUtilsTest {

    public static void main(String[] args) {
        InvoiceUtilsTest test = new InvoiceUtilsTest();
//        test.test1();
        test.test2();

    }


    public void test1() {
        Invoice invoice = new Invoice();
        invoice.setNDS(new BigDecimal("20"));

        Booking booking = new Booking();
        booking.setDate(getDate(2011, 1, 10));
        invoice.setBooking(booking);
        List<Invoice> invoices = new LinkedList<Invoice>();
        invoices.add(invoice);
        filterInvoicesWithDebts(invoices, getDate(2011, 1, 5));
    }
    public void test2() {
        Invoice invoice = new Invoice();
        invoice.setNDS(new BigDecimal("20"));

        Booking booking = new Booking();
        booking.setDate(getDate(2011, 1, 10));
        invoice.setBooking(booking);

        Production production = new Production();
        production.setSellingPrice(new BigDecimal("100"));
        production.setQuantity(1);

        invoice.addInvoiceItems(production);


        List<Invoice> invoices = new LinkedList<Invoice>();
        invoices.add(invoice);
        filterInvoicesWithDebts(invoices, getDate(2011, 1, 5));
    }


    public static Date getDate(Integer year, Integer month, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


}
