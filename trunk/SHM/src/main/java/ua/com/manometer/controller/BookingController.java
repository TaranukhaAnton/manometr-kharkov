package ua.com.manometer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.manometer.model.invoice.Booking;
import ua.com.manometer.model.invoice.Invoice;
import ua.com.manometer.model.invoice.InvoiceItem;
import ua.com.manometer.service.invoice.BookingService;
import ua.com.manometer.service.invoice.InvoiceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static ua.com.manometer.model.invoice.Booking.*;
import static ua.com.manometer.util.CommonUtils.getCurrentDate;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    InvoiceService invoiceService;

    @Autowired
    BookingService bookingService;

    @RequestMapping("/")
    public String populateBookings(Map<String, Object> map) {
        final List<Booking> bookings = bookingService.listBooking();
        map.put("listBookings", bookings);
        return "bookings";
    }


    @RequestMapping("/view")
    public String viewInvoice(@RequestParam("invoice_id") Long id, Map<String, Object> map) {
        Invoice invoice = invoiceService.getInvoice(id);
        map.put("invoice", invoice);
        return "bookingForm";
    }


    @RequestMapping("/add")
    public String addInvoice(@RequestParam("invoice_id") Long id, HttpServletRequest request, Map<String, Object> map) throws ParseException {
        Invoice invoice = invoiceService.getInvoice(id);
        Booking booking = new Booking();
        DateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        booking.setDate(f.parse(request.getParameter("date")));
        booking.setNumber(new Integer(request.getParameter("number")));
        booking.setNumberModifier(request.getParameter("numberModifier"));
        booking.setCurrentState(0);
        booking.setInvoice(invoice);
        invoice.setBooking(booking);
        invoice.setCurrentState(Invoice.STATE_ZAK);
        bookingService.addBooking(booking);
        invoiceService.saveInvoice(invoice);
        return "redirect:/bookings/view?invoice_id=" + invoice.getId();
    }


    @RequestMapping("/editBookingParams")
    public
    @ResponseBody
    Map editBookingParams(@RequestParam("invoice_id") Long invoice_id, @RequestParam("param") String param, @RequestParam("value") String value) throws ParseException {


        Invoice invoice = invoiceService.getInvoice(invoice_id);
        Booking booking = invoice.getBooking();
        Map result = new HashMap();
        Boolean validate = true;

        if (param.equals("bookingNotes")) {
            booking.setNotes(value);
        } else if (param.equals("bookingComments")) {
            booking.setComments(value);
        } else if (param.equals("currentState")) {

            Integer state = new Integer(value);


            switch (state) {
                case STATE_CHERN:
                    // booking.setDate(null);
                    break;
                case STATE_PROIZV:
                    TreeSet<Date> set = new TreeSet();
                    for (InvoiceItem item : invoice.getInvoiceItems())
                        set.add(InvoiceItem.dateBeforeNDays(booking.getDate(), item.getDeliveryTime()));
                    booking.setSupplierObligations1(set.first());
                    booking.setSupplierObligations2(set.last());
                    break;
                case STATE_SKLAD:
                    booking.setDateOfNoticeOpening(getCurrentDate());
                    break;
                case STATE_PRIOST:

                    if (!(invoice.getPayments().isEmpty() && invoice.getShipments().isEmpty())) {
                        result.put("message", "Были отгрузки или оплаты. Невозможно приостановить.");
                        validate = false;
                    }
                    break;
                case STATE_ANN:
                    // System.out.println(invoice.computeTotalPayments()+" "+ invoice.computeTotalPayments().compareTo(BigDecimal.ZERO));
                    if (!(invoice.isAnyGoodsNotShipped() && (invoice.computeTotalPayments().compareTo(BigDecimal.ZERO) == 0))) {

                        result.put("message", "Сумма отгрузок и сумма оплат должна равняться нулю.");
                        validate = false;
                    }


                    break;

            }

            if (validate) {
                booking.setCurrentState(state);
            }


        }
        result.put("res", validate);
        result.put("message", "message");
        bookingService.addBooking(booking);
        return result;
    }


    @RequestMapping("/verify_booking")
    public
    @ResponseBody
    Map verifyBookingPresence(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        //   printProps(request);
        Integer number = new Integer(request.getParameter("number"));
        String numberModifier = request.getParameter("numberModifier");
        Date date = (new SimpleDateFormat("dd.MM.yyyy")).parse(request.getParameter("date"));
        Boolean isAlreadyPresent = bookingService.checkPresence(number, numberModifier, date);
        Map map = new HashMap();
        map.put("presence", isAlreadyPresent);
        return map;
    }


}
