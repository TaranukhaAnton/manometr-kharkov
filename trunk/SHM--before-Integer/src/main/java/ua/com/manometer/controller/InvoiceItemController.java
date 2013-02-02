package ua.com.manometer.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.manometer.dao.price.PriceFirstPartDAO;
import ua.com.manometer.model.invoice.Invoice;
import ua.com.manometer.model.invoice.InvoiceItem;
import ua.com.manometer.model.invoice.PressureSensor;
import ua.com.manometer.model.invoice.Production;
import ua.com.manometer.model.modeldescription.ModelDescription;
import ua.com.manometer.model.price.IdPrice;
import ua.com.manometer.model.price.PriceFirstPart;
import ua.com.manometer.model.price.ProductionPrice;
import ua.com.manometer.service.invoice.InvoiceItemService;
import ua.com.manometer.service.invoice.InvoiceService;
import ua.com.manometer.service.modeldescription.ModelDescriptionService;
import ua.com.manometer.service.price.PriceFirstPartService;
import ua.com.manometer.service.price.ProductionPriceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/invoice_item")
public class InvoiceItemController {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    InvoiceItemService invoiceItemService;

    @Autowired
    ModelDescriptionService modelDescriptionService;

    @Autowired
    private PriceFirstPartService priceFirstPartService;

    @Autowired
    private ProductionPriceService productionPriceService;


    @RequestMapping("/add")
    public String add(@RequestParam("invoice_id") Long invoice_id, @RequestParam(value = "invoice_item_id", required = false) Long itemId,
                      @RequestParam("type") String type, Map<String, Object> map) {
        map.put("invoice_id", invoice_id);
        if (itemId != null) {
            map.put("invoice_item_id", itemId);
        }
        return "add" + StringUtils.capitalize(type);
    }

    @RequestMapping(value = "/add_list", method = RequestMethod.GET)
    public String addList(@RequestParam("invoice_id") Long invoice_id, @RequestParam("type") Integer type, Map<String, Object> map) {
        map.put("invoice_id", invoice_id);
        map.put("type", type);
        map.put("list", productionPriceService.listProductionPriceByType(type));
        return "addList";
    }

    @RequestMapping(value = "/add_list", method = RequestMethod.POST)
    public String addListExt(@RequestParam("invoice_id") Long invoice_id,
                             @RequestParam("type") Integer type,
                             HttpServletRequest request)     throws Exception {
        printProps(request);
        Invoice invoice = invoiceService.getInvoice(invoice_id);
        String productionId = request.getParameter("production_id");
        BigDecimal cost;
        BigDecimal price;
        Boolean addToList = false;
        String name;

        if (productionId != null) {
            Long id = new Long(productionId);
            ProductionPrice productionPrice = productionPriceService.getProductionPrice(id);
            cost = productionPrice.getCost();
            price = productionPrice.getPrice();
            name = productionPrice.getName();
        } else {
            addToList = request.getParameter("addToList") != null;
            cost = new BigDecimal(request.getParameter("cost"));
            price = new BigDecimal(request.getParameter("price"));
            name = request.getParameter("name");
        }



        Production production = new Production();
        production.setName(name);
        production.setCost(cost);
        production.setPrice(price);
        production.setQuantity(1);
        production.setDeliveryTime(45);
        production.setType(type);
        production.setInvoice(invoice);
        production.setTransportationCost(new BigDecimal("0"));
        production.setAdditionalCost(new BigDecimal("0"));
        invoiceItemService.setupMoneyFields(production, new BigDecimal("1.3"));
        if (addToList) {
            ProductionPrice item = new ProductionPrice();
            item.setCost(cost);
            item.setName(name);
            item.setPrice(price);
            item.setType(type);
            productionPriceService.addProductionPrice(item);
        }



        invoice.addInvoiceItems(production);
        invoiceItemService.saveInvoiceItem(production);
        invoiceService.saveInvoice(invoice);
        return "redirect:/invoices/view?invoice_id=" + invoice_id;
    }

    @RequestMapping("/get_ii")
    public
    @ResponseBody
    Map getInvoiceItem(@RequestParam("invoice_item_id") Long itemId) {
        Map result = new HashMap();
        PressureSensor pressureSensor = (PressureSensor) invoiceItemService.getInvoiceItem(itemId);
        ModelDescription modelDescription = modelDescriptionService.getModelDescription(new Long(pressureSensor.getModel()));
        result.put("pressureSensor", toJsonMap(pressureSensor));
        result.put("modelDescription", modelDescription);
        return result;
    }

    @RequestMapping("/del_ii")
    public String deleteInvoiceItem(@RequestParam("invoice_item_id") Long itemId, @RequestParam("invoice_id") Long invoiceId) {
        Invoice invoice = invoiceService.getInvoice(invoiceId);
        Iterator<InvoiceItem> iterator = invoice.getInvoiceItems().iterator();
        while (iterator.hasNext()) {
            InvoiceItem next = iterator.next();
            if (next.getId().equals(itemId)) {
                iterator.remove();
                invoiceService.saveInvoice(invoice);
                invoiceItemService.removeInvoiceItem(itemId);
                break;
            }
        }
        return "redirect:/invoices/view?invoice_id=" + invoiceId;
    }


    @RequestMapping("/add_pressure_sensor")
    public String addPressureSensor(@RequestParam("invoice_id") Long invoiceId,
                                    @RequestParam(value = "invoice_item_id", required = false) Long itemId,
                                    HttpServletRequest request) {

        PressureSensor pressureSensor;
        if (itemId == null) {
            pressureSensor = new PressureSensor();
        } else {
            pressureSensor = (PressureSensor) invoiceItemService.getInvoiceItem(itemId);
        }


        // String item =    request.getParameter("beforeSpec");
        pressureSensor.setPreamble(request.getParameter("beforeSpec"));
        pressureSensor.setSeries(new Integer(request.getParameter("typeTxt")));
        pressureSensor.setIsp(new Integer(request.getParameter("isp")));
        pressureSensor.setModel(request.getParameter("model"));
        String model = request.getParameter("model");
        pressureSensor.setType((model.charAt(0) == '5') ? 0 : (model.charAt(0) == '2') ? 1 : 2);

        pressureSensor.setP(request.getParameter("p") != null);
        pressureSensor.setVM(request.getParameter("vm") != null);

        pressureSensor.setMat(new Integer(request.getParameter("mat")));
        pressureSensor.setKlim(new Integer(request.getParameter("klim")));
        pressureSensor.setError(new Integer(request.getParameter("err")));
        if (request.getParameter("hi") == "") {
            pressureSensor.setHiLimit(new Float(request.getParameter("hid")));
            pressureSensor.setLowLimit(null);
        } else {
            String s = request.getParameter("hi").replace(",", ".");
            pressureSensor.setHiLimit(new Float(s));
            s = request.getParameter("low").replace(",", ".");
            pressureSensor.setLowLimit((s == "") ? null : new Float(s));
        }
        pressureSensor.setEd_izm(new Integer(request.getParameter("ed_izm")));
        pressureSensor.setStat(StringUtils.isBlank(request.getParameter("stat")) ? 0 : new Integer(request.getParameter("stat")));
        pressureSensor.setOutType(new Integer(request.getParameter("out")));
        pressureSensor.setKmch(StringUtils.isBlank(request.getParameter("kmch")) ? 0 : new Integer(request.getParameter("kmch")));
        pressureSensor.setDu(StringUtils.isBlank(request.getParameter("du")) ? 0 : new Integer(request.getParameter("du")));
        pressureSensor.setR(request.getParameter("r") != null);
        pressureSensor.setI(request.getParameter("i") != null);
        pressureSensor.setHIM(request.getParameter("him") != null);
        pressureSensor.setPI(request.getParameter("pi") != null);
        pressureSensor.setTU(request.getParameter("tu") != null);
        pressureSensor.setAfterSpec(request.getParameter("afterSpec"));


//            if (invoiceId != null) {


        if (itemId == null) {
            Invoice i = invoiceService.getInvoice(invoiceId);
            pressureSensor.setQuantity(1);
            pressureSensor.setDeliveryTime(45);
            pressureSensor.setInvoice(i);
            pressureSensor.setTransportationCost(new BigDecimal("0"));
            pressureSensor.setAdditionalCost(new BigDecimal("0"));

            invoiceItemService.setupMoneyFields(pressureSensor, new BigDecimal("1.3"));


            i.addInvoiceItems(pressureSensor);
            invoiceItemService.saveInvoiceItem(pressureSensor);
            invoiceService.saveInvoice(i);
        } else {
            BigDecimal koef = pressureSensor.calculatePercent();
            invoiceItemService.setupMoneyFields(pressureSensor, koef);
            invoiceItemService.saveInvoiceItem(pressureSensor);
        }
        return "redirect:/invoices/view?invoice_id=" + invoiceId;
    }


    @RequestMapping("/moveDownItem")
    public String moveDownItem(@RequestParam("invoice_id") Long invoiceId,
                               @RequestParam("position") Integer position) {
        Invoice invoice = invoiceService.getInvoice(invoiceId);
        List<InvoiceItem> list = invoice.getInvoiceItems();

        if ((position >= 0) & (position < (list.size() - 1))) {
            Collections.swap(list, position, position + 1);
            invoice.setInvoiceItems(list);
            invoiceService.saveInvoice(invoice);
        }
        return "redirect:/invoices/view?invoice_id=" + invoice.getId();
    }

    @RequestMapping("/moveUpItem")
    public String moveUpItem(@RequestParam("invoice_id") Long invoiceId,
                             @RequestParam("position") Integer position) {
        Invoice invoice = invoiceService.getInvoice(invoiceId);
        List<InvoiceItem> list = invoice.getInvoiceItems();
        if ((position > 0) & (position < list.size())) {
            Collections.swap(list, position, position - 1);
            invoice.setInvoiceItems(list);
            invoiceService.saveInvoice(invoice);
        }
        return "redirect:/invoices/view?invoice_id=" + invoice.getId();
    }


    private Map toJsonMap(PressureSensor pressureSensor) {
        Map map = new HashMap();
        map.put("du", pressureSensor.getDu());
        map.put("model", pressureSensor.getModel());
        map.put("hiLimit", pressureSensor.getHiLimit());
        map.put("lowLimit", pressureSensor.getLowLimit());
        map.put("VM", pressureSensor.isVM());
        map.put("ed_izm", pressureSensor.getEd_izm());
        map.put("type", pressureSensor.getSeries());
        map.put("kmch", pressureSensor.getKmch());
        map.put("preamble", pressureSensor.getPreamble());
        map.put("HIM", pressureSensor.isHIM());
        map.put("error", pressureSensor.getError());
        map.put("afterSpec", pressureSensor.getAfterSpec());
        map.put("mat", pressureSensor.getMat());
        map.put("isp", pressureSensor.getIsp());
        map.put("stat", pressureSensor.getStat());
        map.put("i", pressureSensor.isI());
        map.put("r", pressureSensor.isR());
        map.put("PI", pressureSensor.isPI());
        map.put("p", pressureSensor.isP());
        map.put("TU", pressureSensor.isTU());
        map.put("outType", pressureSensor.getOutType());
        map.put("klim", pressureSensor.getKlim());
        return map;
    }


    public static void printProps(HttpServletRequest request) {
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String param = en.nextElement();
            // System.out.print(param + " -- ");
            // System.out.println(request.getParameter(param));
            for (String s : request.getParameterValues(param))
                System.out.println(param + " = " + s);
        }
    }

}
