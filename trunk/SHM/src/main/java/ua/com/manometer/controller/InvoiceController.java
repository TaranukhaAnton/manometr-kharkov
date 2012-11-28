package ua.com.manometer.controller;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.JodaTimeContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.manometer.model.Customer;
import ua.com.manometer.model.Supplier;
import ua.com.manometer.model.invoice.Invoice;
import ua.com.manometer.model.invoice.InvoiceItem;
import ua.com.manometer.service.CustomerService;
import ua.com.manometer.service.SupplierService;
import ua.com.manometer.service.invoice.InvoiceItemService;
import ua.com.manometer.service.invoice.InvoiceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
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
    @Autowired
    InvoiceItemService invoiceItemService;

    @RequestMapping("/")
    public String populateInvoices(Map<String, Object> map) {
        final List<Invoice> value = invoiceService.listInvoice();
        map.put("listInvoices", value);
        return "invoices";
    }

    @RequestMapping("/view")
    public String viewInvoice(@RequestParam("id") Long id, Map<String, Object> map) {
        Invoice invoice = invoiceService.getInvoice(id);
        map.put("invoice", invoice);
        map.put("supplierList", supplierService.listSupplier());
        return "editInvoice";
    }


    @RequestMapping("/add")
    public String addInvoice(HttpServletRequest request, Map<String, Object> map) throws ParseException {
        Invoice invoice = new Invoice();
        String employerShortName = request.getParameter("employer");
        invoice.setEmployer(employerShortName);
        invoice.setConsumer(request.getParameter("consumer"));
        Customer employer = customerService.getCustomerByShortName(employerShortName);
        invoice.setExecutor(employer.getPerson());
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
        invoice.setInvoiceItems(new LinkedList<InvoiceItem>());
        invoiceService.saveInvoice(invoice);
        return "redirect:/invoices/view?id=" + invoice.getId();
    }


    @RequestMapping("/editInvoiceItem")
    public
    @ResponseBody
    String editInvoiceItem(@RequestParam("invId") Long invoiceId, @RequestParam("id") final Long id, @RequestParam("param") String param, @RequestParam("value") String value)
            throws Exception {
        Invoice invoice = invoiceService.getInvoice(invoiceId);
        InvoiceItem item = (InvoiceItem) CollectionUtils.find(invoice.getInvoiceItems(), new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                return ((InvoiceItem) o).getId().equals(id);
            }
        });

        NumberFormat df = NumberFormat.getInstance();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);

        if (param.equalsIgnoreCase("quantity")) {

            Integer oldVal = item.getQuantity();
            Integer newVal = new Integer(value);
            item.setQuantity(newVal);
            Integer count;
            switch (item.getType())

            {
                case 0:
                case 1:
                case 2:
                case 3:
                    count = invoice.getT0();
                    count += newVal - oldVal;
                    invoice.setT0(count);
                    break;
                case 4:
                    count = invoice.getT1();
                    count += newVal - oldVal;
                    invoice.setT1(count);
                    break;


                case 5:
                    count = invoice.getT3();
                    count += newVal - oldVal;
                    invoice.setT3(count);
                    break;
                case 6:
                    count = invoice.getT2();
                    count += newVal - oldVal;
                    invoice.setT2(count);
                    break;
                case 7:
                case 8:
                    count = invoice.getT4();
                    count += newVal - oldVal;
                    invoice.setT4(count);
                    break;


                case 9:
                    count = invoice.getT5();
                    count += newVal - oldVal;
                    invoice.setT5(count);
                    break;


            }
        } else if (param.equalsIgnoreCase("additionalCost")) {
            item.setAdditionalCost(new BigDecimal(value.replace(",", ".")));
        } else if (param.equalsIgnoreCase("transportationCost")) {
            item.setTransportationCost(new BigDecimal(value.replace(",", ".").replace(" ", "")));
        } else if (param.equalsIgnoreCase("percent")) {
            item.setPercent(new BigDecimal(value.replace(",", ".")));
        } else if (param.equalsIgnoreCase("sellingPrice")) {
            BigDecimal t = new BigDecimal(value.replace(",", "."));
            item.setSellingPrice(t);
        } else if (param.equalsIgnoreCase("deliveryTime")) {
            item.setDeliveryTime(new Integer(value));
        } else if (param.equalsIgnoreCase("data")) {
            int i = InvoiceItem.daysBetween(item.getInvoice().getBooking().getDate(), (new SimpleDateFormat("dd.MM.yyyy")).parse(value));
            item.setDeliveryTime(i);
        }
        // NumberFormat df = NumberFormat.getInstance();


        //Factory.getInvoiceDAO().findById(new Long(request.getParameter("invoiceId")));
        String res = "{";
        res += "\"total\":\"" + df.format(invoice.getTotal()) + "\"," +
                "\"sumTot\":\"" + df.format(invoice.getSum()) + "\"," +
                "\"nds\":\"" + df.format(invoice.getNDSPayment()) + "\"";
        res += ",\"quantity\":\"" + item.getQuantity() + "\"";
        df.setMinimumFractionDigits(4);
        df.setMaximumFractionDigits(4);
        res += ",\"percent\":\"" + df.format(item.calculatePercent()) + "\"";
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        res += ",\"sellingPrice\":\"" + df.format(item.getSellingPrice()) + "\"";
        res += ",\"sum\":\"" + df.format(item.getSum()) + "\"";
        res += ",\"transportationCost\":\"" + df.format(item.getTransportationCost()) + "\"";
        res += ",\"additionalCost\":\"" + df.format(item.getAdditionalCost()) + "\"";
        res += ",\"deliveryTime\":\"" + item.getDeliveryTime() + "\"";
        res += "}";

//        invoiceItemService.saveInvoiceItem(item);
        invoiceService.saveInvoice(invoice);

        return res;
    }


    @RequestMapping("/editInvoiceParams")
    public
    @ResponseBody
    Map editInvoiceParams(@RequestParam("id") Long id, @RequestParam("param") String param, @RequestParam("value") String value) throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>();
        Invoice invoice = invoiceService.getInvoice(id);
        if (invoice == null) {
            //todo      log.error
            return map;
        }

        NumberFormat df = NumberFormat.getInstance();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);


        if (param.equals("date")) {
            invoice.setDate((new SimpleDateFormat("dd.MM.yyyy")).parse(value));
        } else if (param.equals("daysAfterDelivery")) {
            invoice.setDaysAfterDelivery(new Integer(value));
        } else if (param.equals("notes")) {
            invoice.setNotes(value);
        } else if (param.equals("comments")) {
            invoice.setComments(value);
        } else if (param.equals("deliveryTime")) {
            invoice.setDeliveryTime(value);
        } else if (param.equals("purpose")) {
            invoice.setPurpose(new Integer(value));
        } else if (param.equals("prepayment")) {
            invoice.setPrepayment(new BigDecimal(value.replace(",", ".")));
        } else if (param.equals("paymentOnTheNotice")) {
            invoice.setPaymentOnTheNotice(new BigDecimal(value.replace(",", ".")));
        } else if (param.equals("postPay")) {
            invoice.setPostPay(new BigDecimal(value.replace(",", ".")));
        } else if (param.equals("probabilityOfPayment")) {
            invoice.setProbabilityOfPayment(new Integer(value));
        } else if (param.equals("awaitingPayment")) {
            invoice.setAwaitingPayment((new SimpleDateFormat("dd.MM.yyyy")).parse(value));
        } else if (param.equals("awaitingDelivery")) {
            invoice.setAwaitingDelivery((new SimpleDateFormat("dd.MM.yyyy")).parse(value));
        } else if (param.equals("controlDate")) {
            invoice.setControlDate((new SimpleDateFormat("dd.MM.yyyy")).parse(value));
        } else if (param.equals("validity")) {
            invoice.setValidity(new Integer(value));
        } else if (param.equals("currentState")) {
            invoice.setCurrentState(new Integer(value));
        } else if (param.equals("employer")) {
            if (customerService.isCustomerPresent(value)) {
                map.put("res", "ok");
                map.put("param", "employer");
                invoice.setEmployer(value);
            } else {
                map.put("res", "error");
                map.put("param", "employer");
            }
        } else if (param.equals("consumer")) {
            if (customerService.isCustomerPresent(value)) {
                map.put("res", "ok");
                map.put("param", "consumer");
                invoice.setConsumer(value);
            } else {
                map.put("res", "error");
                map.put("param", "consumer");
            }
        } else if (param.equals("supplier")) {
            Supplier supplier = supplierService.getSupplier(new Long(value));
            BigDecimal oldExchangeRate = invoice.getExchangeRate();
            invoice.setSupplier(supplier);
            invoice.setExchangeRate(supplier.getCurrency().getExchangeRate());
            map.put("currency", supplier.getCurrency().getName());
            map.put("exchangeRate", supplier.getCurrency().getExchangeRate().toString());
            map.put("isNative", (supplier.getCurrency().getId() == 1)); //todo безобразие

            List<Map> items = new LinkedList<Map>();
            for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
                BigDecimal sellingPrice = invoiceItem.getSellingPrice().multiply(oldExchangeRate).divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP);
                invoiceItem.setSellingPrice(sellingPrice);
                invoiceItemService.saveInvoiceItem(invoiceItem);
                items.add(invoiceItemToMap(invoiceItem));
            }
            map.put("items", items);
            map.put("total", df.format(invoice.getTotal()));
            map.put("sum", df.format(invoice.getSum()));
            map.put("nds", df.format(invoice.getNDSPayment()));
        } else if (param.equals("commonPercent")) {
            BigDecimal percent = new BigDecimal(value.replace(",", ".").trim());
            List<Map> items = new LinkedList<Map>();
            for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
                invoiceItem.setPercent(percent);
                invoiceItemService.saveInvoiceItem(invoiceItem);
                items.add(invoiceItemToMap(invoiceItem));
            }
            map.put("items", items);
            map.put("total", df.format(invoice.getTotal()));
            map.put("sum", df.format(invoice.getSum()));
            map.put("nds", df.format(invoice.getNDSPayment()));
        } else if (param.equals("commonTransportCost")) {
            BigDecimal transportationCost = new BigDecimal(value.replace(",", ".").trim());

            List<Map> items = new LinkedList<Map>();
            for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
                invoiceItem.setTransportationCost(transportationCost);
                invoiceItemService.saveInvoiceItem(invoiceItem);
                items.add(invoiceItemToMap(invoiceItem));
            }
            map.put("items", items);
            map.put("total", df.format(invoice.getTotal()));
            map.put("sum", df.format(invoice.getSum()));
            map.put("nds", df.format(invoice.getNDSPayment()));
        } else if (param.equals("roundPrice")) {
            Integer roundValue = new Integer(value);
            List<Map> items = new LinkedList<Map>();
            for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
                BigDecimal price = invoiceItem.getSellingPrice();
                price = price.movePointLeft(3);
                price = price.setScale(roundValue, BigDecimal.ROUND_HALF_DOWN);
                price = price.movePointRight(3);
                invoiceItem.setSellingPrice(price);
                invoiceItemService.saveInvoiceItem(invoiceItem);
                items.add(invoiceItemToMap(invoiceItem));
            }
            map.put("items", items);
            map.put("total", df.format(invoice.getTotal()));
            map.put("sum", df.format(invoice.getSum()));
            map.put("nds", df.format(invoice.getNDSPayment()));

        } else if (param.equals("NDS")) {
            invoice.setNDS(new BigDecimal(value.replace(",", ".")));
            List<Map> items = new LinkedList<Map>();
            map.put("items", items);
            map.put("total", df.format(invoice.getTotal()));
            map.put("sum", df.format(invoice.getSum()));
            map.put("nds", df.format(invoice.getNDSPayment()));
        } else if (param.equals("exchangeRate")) {
            BigDecimal exchangeRate = new BigDecimal(value.replace(",", "."));
            BigDecimal oldExchangeRate = invoice.getExchangeRate();
            invoice.setExchangeRate(exchangeRate);
            List<Map> items = new LinkedList<Map>();
            for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
                BigDecimal sellingPrice = invoiceItem.getSellingPrice().multiply(oldExchangeRate).divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP);
                invoiceItem.setSellingPrice(sellingPrice);
                invoiceItemService.saveInvoiceItem(invoiceItem);
                items.add(invoiceItemToMap(invoiceItem));
            }
            map.put("items", items);
            map.put("total", df.format(invoice.getTotal()));
            map.put("sum", df.format(invoice.getSum()));
            map.put("nds", df.format(invoice.getNDSPayment()));
        }
        //#######################################
        invoiceService.saveInvoice(invoice);
        return map;
    }


    @RequestMapping("/verifyInvoicePresence")
    public
    @ResponseBody
    Map verifyInvoicePresence(HttpServletRequest request) throws ParseException {
        Map map = new HashMap();

        DateFormat f = new SimpleDateFormat("dd.MM.yyyy");

        Boolean isInvoice = new Boolean(request.getParameter("isInvoice"));
        Integer number = new Integer(request.getParameter("number"));
        String numberModifier = request.getParameter("numberModifier");
        Date date = f.parse(request.getParameter("date"));
        Boolean isAlreadyPresent = invoiceService.checkPresence(number, numberModifier, isInvoice, date);
        Boolean correct = true;
        String mes = "";

        if (isAlreadyPresent) {
            correct = false;
            mes += ((isInvoice) ? "Cчет" : "Кп") + " уже существует. \\u0D";
        }

        map.put("presence", isAlreadyPresent);

        String employer = request.getParameter("employer");
        Boolean employerCorrect = customerService.isCustomerPresent(employer);
        map.put("employer", employerCorrect);
        if (!employerCorrect) {
            correct = false;
            mes += "Неверно указан заказчик. \\u0D ";
        }


        String consumer = request.getParameter("consumer");
        if (StringUtils.isNotBlank(consumer)) {
            Boolean consumerCorrect = customerService.isCustomerPresent(consumer);
            map.put("consumer", !consumerCorrect);
            if (!consumerCorrect) {
                correct = false;
                mes += "Неверно указан конечный потребитель. \\u0D ";
            }
        }else {
            map.put("consumer", true);
        }


        map.put("correct", correct);
        map.put("mes", mes);

        return map;
    }


    private Map invoiceItemToMap(InvoiceItem item) {
        Map result = new HashMap();
        NumberFormat df = NumberFormat.getInstance();
        result.put("quantity", item.getQuantity());
        df.setMinimumFractionDigits(4);
        df.setMaximumFractionDigits(4);
        result.put("percent", df.format(item.calculatePercent()));
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        result.put("sellingPrice", df.format(item.getSellingPrice()));
        result.put("sum", df.format(item.getSum()));
        result.put("transportationCost", df.format(item.getTransportationCost()));
        result.put("additionalCost", df.format(item.getAdditionalCost()));
        result.put("deliveryTime", item.getDeliveryTime());
        result.put("id", item.getId());
        return result;
    }


}