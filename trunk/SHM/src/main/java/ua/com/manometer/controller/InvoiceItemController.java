package ua.com.manometer.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.manometer.model.invoice.Invoice;
import ua.com.manometer.model.invoice.InvoiceItem;
import ua.com.manometer.model.invoice.PressureSensor;
import ua.com.manometer.model.modeldescription.ModelDescription;
import ua.com.manometer.service.invoice.InvoiceItemService;
import ua.com.manometer.service.invoice.InvoiceService;
import ua.com.manometer.service.modeldescription.ModelDescriptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/invoice_item")
public class InvoiceItemController {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    InvoiceItemService invoiceItemService;

    @Autowired
    ModelDescriptionService modelDescriptionService;


    @RequestMapping("/add_co")
    public String addCo(@RequestParam("invoice_id") Long invoiceId, @RequestParam(value = "invoice_item_id", required = false) Long itemId, Map<String, Object> map) {
        map.put("invoice_id", invoiceId);
        map.put("invoice_item_id", (itemId == null) ? "" : itemId);
        return "addCo";
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
        return "redirect:/invoices/view?id=" + invoiceId;
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

            invoiceItemService.setupMoneyFields(pressureSensor,new BigDecimal("1.3"));



            i.addInvoiceItems(pressureSensor);
            invoiceItemService.saveInvoiceItem(pressureSensor);
            Integer count = i.getT0();
            if (count == null) {
                count = 1;
            } else {
                count += 1;
            }
            i.setT0(count);
            invoiceService.saveInvoice(i);
        } else {
            BigDecimal koef = pressureSensor.calculatePercent();
            invoiceItemService.setupMoneyFields(pressureSensor,koef);
            invoiceItemService.saveInvoiceItem(pressureSensor);
        }
        return "redirect:/invoices/view?id=" + invoiceId;
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


}
