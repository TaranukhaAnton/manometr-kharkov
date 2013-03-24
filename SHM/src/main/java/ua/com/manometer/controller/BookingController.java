package ua.com.manometer.controller;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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






    /*
    *
    *    public ActionForward bookingPrint(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=invoice.pdf");
        Booking booking = Factory.getBookingDAO().findById(new Long(request.getParameter("id")));
        Invoice invoice = booking.getInvoice();

        ServletContext context = this.getServlet().getServletContext();
        try {

            JasperReport report = JasperCompileManager.compileReport(context.getRealPath("/disign/booking.jrxml"));
            Map parameters = new HashMap();
            parameters.put("invoice", invoice);
            parameters.put("booking", booking);

            //  parameters.put("path", context.getRealPath("/images/reportImages/"));
            // parameters.put("city", Factory.getCityDAO().findById(invoice.getEmploer().getCity()).getName());

            List<InvoiceItem> result = new LinkedList<InvoiceItem>();
            for (InvoiceItem i : invoice.getInvoiceItems()) {
                if (i.getType() != 9) result.add(i);
            }


            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(result));
            OutputStream out = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, out);


//            response.setHeader("Content-Disposition", "attachment;filename=invoice.xls");
//            JRXlsExporter jrXlsExporter = new JRXlsExporter();
//            jrXlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            jrXlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
//            jrXlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
//            jrXlsExporter.exportReport();


//            response.setHeader("Content-Disposition", "attachment;filename=invoice.docx");
//            JRDocxExporter exporter = new JRDocxExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
//            exporter.exportReport();

            out.flush();
            out.close();


            // invoice.getNumber()

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
    *
    *
    * */


    @RequestMapping(value = "/export_report", method = RequestMethod.GET)
    public String exportReport(@RequestParam("invoice_id") Integer invoiceId, @RequestParam("type") String type, ModelMap model, HttpServletRequest request) {
        Invoice invoice = invoiceService.getInvoice(invoiceId);
        List<InvoiceItem> result = new LinkedList<InvoiceItem>();
        for (InvoiceItem i : invoice.getInvoiceItems()) {
            if (i.getType() != 9) result.add(i);
        }

        JRDataSource dataSource = new JRBeanCollectionDataSource(result);

//        Customer employer = customerService.getCustomerByShortName(invoice.getEmployer());
//        City city = cityService.getCity(employer.getCity());
//
//        String orgForm = "";
//        String cityName = "";
//        JAmount jAmount = null;
//        Locale locale = null;
//
//        if (language.equals("ru")) {
//            orgForm = employer.getOrgForm().getName();
//            cityName = Customer.localityTypeAlias[employer.getLocalityType().intValue()];
//            cityName += " " + city.getName();
//            jAmount = new JAmountRU();
//            locale = new Locale("ru", "RU");
//        } else if (language.equals("ua")) {
//            orgForm = employer.getOrgForm().getNameUkr();
//            cityName = Customer.localityTypeAliasUkr[employer.getLocalityType().intValue()];
//            cityName += " " + city.getNameUkr();
//            jAmount = new JAmountUA();
//            locale = new Locale("ua", "UA");
//        } else if (language.equals("en")) {
//            orgForm = employer.getOrgForm().getNameEng();
//            cityName = Customer.localityTypeAliasEn[employer.getLocalityType().intValue()];
//            cityName += " " + city.getNameEn();
//            jAmount = new JAmountEN();
//            locale = new Locale("en", "EN");
//        }

//        model.addAttribute("orgForm", orgForm);
//        model.addAttribute("city", cityName);

        model.addAttribute("dataSource", dataSource);
        // Add the report format
        model.addAttribute("format", type);
        model.addAttribute("invoice", invoice);
        model.addAttribute("booking", invoice.getBooking());
        return "bookingReport";
    }


}
