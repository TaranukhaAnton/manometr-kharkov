package ua.com.manometer.controller;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.manometer.model.Customer;
import ua.com.manometer.model.address.City;
import ua.com.manometer.model.invoice.Booking;
import ua.com.manometer.model.invoice.Invoice;
import ua.com.manometer.model.invoice.InvoiceItem;
import ua.com.manometer.service.CustomerService;
import ua.com.manometer.service.address.CityService;
import ua.com.manometer.service.invoice.BookingService;
import ua.com.manometer.service.invoice.InvoiceService;
import ua.com.manometer.util.amount.JAmount;
import ua.com.manometer.util.amount.JAmountEN;
import ua.com.manometer.util.amount.JAmountRU;
import ua.com.manometer.util.amount.JAmountUA;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Autowired
    CustomerService customerService;

    @Autowired
    CityService cityService;


    @RequestMapping("/")
    public String populateBookings(Map<String, Object> map) {
        final List<Booking> bookings = bookingService.listBooking();
        map.put("listBookings", bookings);
        return "bookings";
    }


    @RequestMapping("/view")
    public String viewInvoice(@RequestParam("invoice_id") Integer id, Map<String, Object> map) {
        Invoice invoice = invoiceService.getInvoice(id);
        map.put("invoice", invoice);
        return "bookingForm";
    }


    @RequestMapping("/add")
    public String addInvoice(@RequestParam("invoice_id") Integer id, HttpServletRequest request, Map<String, Object> map) throws ParseException {
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
        bookingService.saveBooking(booking);
        invoiceService.saveInvoice(invoice);
        return "redirect:/bookings/view?invoice_id=" + invoice.getId();
    }


    @RequestMapping("/editBookingParams")
    public
    @ResponseBody
    Map editBookingParams(@RequestParam("invoice_id") Integer invoice_id, @RequestParam("param") String param, @RequestParam("value") String value) throws ParseException {


        Invoice invoice = invoiceService.getInvoice(invoice_id);
        Booking booking = invoice.getBooking();
        Map result = new HashMap();
        Boolean validate = true;

        if (param.equals("date")) {
            booking.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(value));
        }  if (param.equals("bookingNotes")) {
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
                    if (!(!invoice.isAnyGoodsShipped() && (invoice.getTotalPayments().compareTo(BigDecimal.ZERO) == 0))) {

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
        bookingService.saveBooking(booking);
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




    @RequestMapping(value = "/export_report", method = RequestMethod.GET)
    public String exportReport(@RequestParam("invoice_id") Integer invoiceId, @RequestParam("type") String type, ModelMap model, HttpServletRequest request) {
        Invoice invoice = invoiceService.getInvoice(invoiceId);
        List<InvoiceItem> result = new LinkedList<InvoiceItem>();
        Set<Date> dates = new TreeSet<Date>();
        for (InvoiceItem i : invoice.getInvoiceItems()) {
            if (i.getType() != 9) result.add(i);
            Date date = InvoiceItem.dateBeforeNDays(invoice.getBooking().getDate(), i.getDeliveryTime());
            dates.add(date);
        }
        Date start = null;
        Date end = null;
        if (!dates.isEmpty()){
            start  = Collections.min(dates);
            end  = Collections.max(dates);
        }


        JRDataSource dataSource = new JRBeanCollectionDataSource(result);



        String fileName = "booking_" + invoice.getBooking().getNumber() + ((StringUtils.isBlank(invoice.getBooking().getNumberModifier())) ? "" : ("_"+invoice.getBooking().getNumberModifier()));
        model.addAttribute("name", fileName);

        model.addAttribute("dataSource", dataSource);
        // Add the report format
        model.addAttribute("format", type);
        model.addAttribute("invoice", invoice);
        model.addAttribute("booking", invoice.getBooking());
        model.addAttribute("end", end);
        model.addAttribute("start", start);


        return "bookingReport";
    }


}
