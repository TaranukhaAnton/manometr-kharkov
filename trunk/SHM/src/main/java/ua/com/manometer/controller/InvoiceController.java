package ua.com.manometer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.manometer.model.Customer;
import ua.com.manometer.model.Supplier;
import ua.com.manometer.model.invoice.Invoice;
import ua.com.manometer.model.invoice.InvoiceItem;
import ua.com.manometer.service.CustomerService;
import ua.com.manometer.service.SupplierService;
import ua.com.manometer.service.invoice.InvoiceService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    CustomerService customerService;
    @Autowired
    SupplierService supplierService;

    @RequestMapping("/")
    public String populateInvoices(Map<String, Object> map) {
        final List<Invoice> value = invoiceService.listInvoice();
        map.put("listInvoices", value);
        return "invoices";
    }

    @RequestMapping("/add")
    public String addInvoice(HttpServletRequest request, Map<String, Object> map) {

        try {
            Invoice invoice = new Invoice();
            // User u = (User) request.getSession().getAttribute("logonUser");
            Customer employer = customerService.getCustomerByShortName(request.getParameter("employer"));
            invoice.setEmployer(employer);

            Customer consumer = customerService.getCustomerByShortName(request.getParameter("consumer"));
            invoice.setConsumer(consumer);


            invoice.setExecutor(employer.getPerson());
//            invoice.setExecutor(u);
            invoice.setNDS(new BigDecimal("20"));
//            invoice.setExchangeRate(new BigDecimal("1"));
            invoice.setPurpose(Invoice.PURPOSE_POSTAVKA);

            DateFormat f = new SimpleDateFormat("dd.MM.yyyy");

            invoice.setDate(f.parse(request.getParameter("date")));
            // invoice.setChangeDate(new Date());

            invoice.setInvoice(new Boolean(request.getParameter("isInvoice")));
            invoice.setNumber(new Integer(request.getParameter("number")));
            invoice.setNumberModifier(request.getParameter("numberModifier"));
            invoice.setValidity(10);
            invoice.setPaymentOnTheNotice(new BigDecimal(0));
            invoice.setPrepayment(new BigDecimal(0));
            invoice.setPostPay(new BigDecimal(0));
            invoice.setCurrentState(Invoice.STATE_CHERN);
            invoice.setDeliveryTime("45-60 дней с момента предоплаты.");


            List<Supplier> suppliers = supplierService.listSupplier();
            if (!suppliers.isEmpty()) {
                invoice.setSupplier(suppliers.get(0));
                invoice.setExchangeRate(suppliers.get(0).getCurrency().getExchangeRate());
            }

            invoice.setInvoiceItems(new HashSet<InvoiceItem>());
            invoiceService.addInvoice(invoice);

//            return actionForward;
        } catch (Exception e) {
            e.printStackTrace();
//            return mapping.findForward("invoices");
        }


        final List<Invoice> value = invoiceService.listInvoice();
        map.put("listInvoices", value);
        return "invoices";
    }


}
