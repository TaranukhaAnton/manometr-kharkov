package application.actions;

import application.data.Customer;
import application.data.Supplier;
import application.data.User;
import application.data.invoice.*;
import application.hibernate.Factory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class InvoiceAction extends DispatchAction {

    public ActionForward setupFilter(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {


        //  printProps(request);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            // User u = Factory.getUserDAO().findById(((User) request.getSession().getAttribute("logonUser")).getId());
            User u = Factory.getUserDAO().findById((Long) request.getSession().getAttribute("logonUserId"));
            if (u == null) return null;
            InvoiceFilter filter = (u.getInvoiceFilter() == null) ? (new InvoiceFilter()) : u.getInvoiceFilter();
            filter.setF0((request.getParameter("f0") == null) ? 0 : new Integer(request.getParameter("f0")));
            filter.setF1((request.getParameter("f1") == null) ? 0 : new Integer(request.getParameter("f1")));
            filter.setF2((request.getParameter("f2") == null) ? 0 : new Integer(request.getParameter("f2")));
            if (!request.getParameter("f1_from").isEmpty())
                filter.setF1From(new Integer(request.getParameter("f1_from")));
            if (!request.getParameter("f1_to").isEmpty())
                filter.setF1To(new Integer(request.getParameter("f1_to")));

            if (!request.getParameter("f2_to").isEmpty())
                filter.setF2To(sdf.parse(request.getParameter("f2_to")));
            if (!request.getParameter("f2_from").isEmpty())
                filter.setF2From(sdf.parse(request.getParameter("f2_from")));

            // System.out.println("livel "+request.getSession().getAttribute("livel"));
            String[] strPurpose = request.getParameterValues("purpose");
            List<Long> selectedPurpose = new LinkedList<Long>();
            if (strPurpose != null)
                for (String s : strPurpose)
                    selectedPurpose.add(new Long(s));
            filter.setPurposeFilter(selectedPurpose.toArray(new Long[selectedPurpose.size()]));

            String[] strState = request.getParameterValues("state");
            List<Long> selectedState = new LinkedList<Long>();
            if (strState != null)
                for (String s : strState)
                    selectedState.add(new Long(s));
            filter.setStateFilter(selectedState.toArray(new Long[selectedState.size()]));

            String[] strCurrency = request.getParameterValues("currency");
            List<Long> selectedCurrency = new LinkedList<Long>();
            if (strCurrency != null)
                for (String s : strCurrency)
                    selectedCurrency.add(new Long(s));
            filter.setCurrencyFilter(selectedCurrency.toArray(new Long[selectedCurrency.size()]));


            if (((Integer) request.getSession().getAttribute("livel")) > 2) {

                String[] strUserId = request.getParameterValues("user");
                List<Long> selectedUsers = new LinkedList<Long>();
                if (strUserId != null)
                    for (String s : strUserId)
                        selectedUsers.add(new Long(s));

                filter.setUsers(selectedUsers.toArray(new Long[selectedUsers.size()]));
            } else {
                filter.setUsers(new Long[]{((User) request.getSession().getAttribute("logonUser")).getId()});
            }


            u.setInvoiceFilter(filter);
            Factory.getUserDAO().makePersistent(u);

//            String res = "{\"f0\":0,\"f1\":0,\"f2\":0}";
//            response.setContentType("text/html; charset=UTF-8");
//            response.getWriter().write(res);

        } catch (Exception e) {
            e.printStackTrace();
        }
        ActionForward actionForward = new ActionForward();
        actionForward.setPath("/invoiceAction.do?method=viewInvoices");
        actionForward.setRedirect(true);
        return actionForward;

    }

    public ActionForward getFilter(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            User u = (User) request.getSession().getAttribute("logonUser");
            if (u == null) return null;
            InvoiceFilter filter = (u.getInvoiceFilter() == null) ? (new InvoiceFilter()) : u.getInvoiceFilter();

            // request.get
            // String res = "{\"f0\":0,\"f1\":0,\"f2\":1}";
            //response.set
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(filter.toJSONString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public ActionForward viewInvoices(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return mapping.findForward("invoices");
    }

    public ActionForward viewPayments(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String param = request.getParameter("invoiceId");
        Invoice invoice = Factory.getInvoiceDAO().findById(new Long(param));
        request.setAttribute("invoice", invoice);

        return mapping.findForward("paymentsForm");
    }

    public ActionForward viewShipments(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String param = request.getParameter("invoiceId");

        Invoice invoice = Factory.getInvoiceDAO().findById(new Long(param));
//        System.out.println(invoice.getShipments().size()+"  ");
        request.setAttribute("invoice", invoice);
        return mapping.findForward("shipmentsForm");
    }

    public ActionForward viewBookings(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return mapping.findForward("bookings");
    }

    public ActionForward editInvoiceItem(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        NumberFormat df = NumberFormat.getInstance();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        //   printProps(request);


        try {
            String param = request.getParameter("param");
            String value = request.getParameter("value");
            String id = request.getParameter("id");


            InvoiceItem item = Factory.getInvoiceItemDAO().findById(new Long(id));
            Invoice invoice = item.getInvoice();
            //   BigDecimal exchangeRate = item.getInvoice().getExchangeRate();


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
            // System.out.println("res = " + res);

            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(res);
            Factory.getInvoiceItemDAO().makePersistent(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ActionForward verifyOrderPresence(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request, HttpServletResponse response) {
        try {
            //   printProps(request);
            DateFormat f = new SimpleDateFormat("dd.MM.yyyy");
            List<SimpleExpression> simpleExpressions = new LinkedList();
            simpleExpressions.add(Restrictions.eq("number", new Integer(request.getParameter("number"))));
            simpleExpressions.add(Restrictions.eq("numberModifier", request.getParameter("numberModifier")));


            Map<String, Object> map = new HashMap<String, Object>();
            map.put("number", new Integer(request.getParameter("number")));
            map.put("numberModifier", request.getParameter("numberModifier"));
            //  map.put("orderOpen", true);

            List<Booking> result = Factory.getBookingDAO().findByExample(map);
            //System.out.println("result.size() = " + result.size());

            if (result.size() == 0) {
                response.getWriter().write("true");
            } else {
                String resp = "true";

                for (Booking booking : result) {
                    if (booking.getDate().getYear() == f.parse(request.getParameter("date")).getYear()) {
                        resp = "false";
                        break;
                    }
                }
                response.getWriter().write(resp);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ActionForward verifyInvoicePresence(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request, HttpServletResponse response) {
        // printProps(request);
        try {
            DateFormat f = new SimpleDateFormat("dd.MM.yyyy");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("isInvoice", new Boolean(request.getParameter("isInvoice")));
            map.put("number", new Integer(request.getParameter("number")));
            map.put("numberModifier", request.getParameter("numberModifier"));
            List<Invoice> invoices = Factory.getInvoiceDAO().findByExample(map);
            Boolean correct = true;
            String mes = "";
            String resp = "{";
//            System.out.println(" invoices.size() " + invoices.size());
            if (invoices.size() > 0) {
                for (Invoice inv : invoices) {
                    if (inv.getDate().getYear() == f.parse(request.getParameter("date")).getYear()) {
                        correct = false;
                        mes += "Такой счет/кп уже существует. \\u000D";
                        break;
                    }
                }
            }

            resp += "\"presence\":" + correct + ",";
            Customer emploer = Factory.getCustomerDAO().findByShortName(request.getParameter("emploer"));
            resp += "\"emploer\":" + (emploer != null) + ",";
            correct &= emploer != null;
            if (emploer == null)

            {
                correct = false;
                mes += "Неверно указан заказчик. \\u000D ";
            }


            Customer consumer = Factory.getCustomerDAO().findByShortName(request.getParameter("consumer"));
            resp += "\"consumer\":" + ((request.getParameter("consumer") == "") || (consumer != null)) + ",";
            if (!((request.getParameter("consumer") == "") || (consumer != null))) {
                mes += "Неверно указан конечный потребитель. \\u000D ";
            }

            resp += "\"correct\":" + correct + ",";
            resp += "\"mes\":\"" + ((correct) ? "" : mes) + "\"}";


            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(resp);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ActionForward editInvoiceParams(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response) {
        //  printProps(request);
        NumberFormat df = NumberFormat.getInstance();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        try {
            String param = request.getParameter("param");
            String id = request.getParameter("id");
            String value = request.getParameter("value");
            Invoice invoice = null;
            response.setContentType("text/html; charset=UTF-8");
            if (id != null) if (!id.isEmpty()) {
                invoice = Factory.getInvoiceDAO().findById(new Long(id));
            }
            if (invoice != null) {
                if (param.equals("date")) {
                    invoice.setDate((new SimpleDateFormat("dd.MM.yyyy")).parse(value));
                } else if (param.equals("daysAfterDelivery")) {
                    invoice.setDaysAfterDelivery(new Integer(value));
                } else if (param.equals("notes")) {
                    invoice.setNotes(value);
                } else if (param.equals("сomments")) {
                    invoice.setComments(value);
                } else if (param.equals("deliveryTime")) {
                    invoice.setDeliveryTime(value);
                } else if (param.equals("bookingNotes")) {
                    invoice.getBooking().setNotes(value);
                } else if (param.equals("bookingComments")) {
                    invoice.getBooking().setComments(value);
                } else if (param.equals("purpose")) {
                    invoice.setPurpose(new Integer(value));
                } else if (param.equals("prepayment")) {
                    invoice.setPrepayment(new BigDecimal(value.replace(",", ".")));
                } else if (param.equals("paymentOnTheNitice")) {
                    invoice.setPaymentOnTheNitice(new BigDecimal(value.replace(",", ".")));
                } else if (param.equals("postpay")) {
                    invoice.setPostpay(new BigDecimal(value.replace(",", ".")));
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
                } else if (param.equals("emploer")) {
                    Customer emploer = Factory.getCustomerDAO().findByShortName(value);

                    if (emploer == null) {
                        response.getWriter().write("{\"res\":\"error\",\"param\":\"emploer\"}");
                    } else {
                        response.getWriter().write("{\"res\":\"ok\",\"param\":\"emploer\"}");
                        invoice.setEmploer(emploer);
                        // Factory.getInvoiceDAO().makePersistent(invoice);
                    }


                } else if (param.equals("consumer")) {
                    Map<String, Object> exam = new HashMap();
                    exam.put("shortName", value);
                    exam.put("status", true);
                    List<Customer> consumers = Factory.getCustomerDAO().findByExample(exam);


                    // Customer consumer = Factory.getCustomerDAO().findByShortName(value);
                    response.setContentType("text/html; charset=UTF-8");
                    if (consumers.isEmpty()) {
                        System.out.println("ошибка!!!");
                        response.getWriter().write("{\"res\":\"error\",\"param\":\"consumer\"}");
                    } else {
                        invoice.setConsumer(consumers.get(0));
                        // Factory.getInvoiceDAO().makePersistent(invoice);
                        response.getWriter().write("{\"res\":\"ok\",\"param\":\"consumer\"}");
                    }


                } else if (param.equals("supplier")) {
                    Supplier supplier = Factory.getSupplierDAO().findById(new Long(value));

                    //Supplier opdSupplier = invoice.getSupplier();
                    BigDecimal oldExcangeRate = invoice.getExchangeRate();
                    invoice.setSupplier(supplier);
                    invoice.setExchangeRate(supplier.getCurrency().getExchangeRate());
                    String resp = "{\"currency\":\"" + supplier.getCurrency().getName() + "\", ";
                    resp += "\"exchangeRate\":\"" + supplier.getCurrency().getExchangeRate() + "\", ";
                    resp += "\"isNative\":\"";
                    resp += supplier.getCurrency().getId() == 1;
                    resp += "\",\"items\": [ ";
                    List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();


                    for (int i = 0; i < invoiceItems.size(); i++) {
                        InvoiceItem invoiceItem = invoiceItems.get(i);
                        BigDecimal sellingPrice = invoiceItem.getSellingPrice().multiply(oldExcangeRate).divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP);
                        resp += "{\"id\":\"sellingPrice" + invoiceItem.getId() + "\",\"value\":\"" +
                                df.format(sellingPrice) + "\"},";
                        invoiceItem.setSellingPrice(sellingPrice);
                        Factory.getInvoiceItemDAO().makePersistent(invoiceItem);
                        resp += "{\"id\":\"sum" + invoiceItem.getId() + "\",\"value\":\"" + df.format(invoiceItem.getSum()) + "\"},";
                    }

                    resp = resp.substring(0, resp.length() - 1) + "]" +
//                            ",\"total\":\"" + df.format(invoice.getTotal().divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP)) + "\"," +
//                            "\"sum\":\"" + df.format(invoice.getSum().divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP)) +
//                            "\",\"nds\":\"" + df.format(invoice.getNDSPayment().divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP)) + "\"}";
                            ",\"total\":\"" + df.format(invoice.getTotal()) + "\"," +
                            "\"sum\":\"" + df.format(invoice.getSum()) +
                            "\",\"nds\":\"" + df.format(invoice.getNDSPayment()) + "\"}";

                    response.getWriter().write(resp);

                } else if (param.equals("commonPercent")) {
                    BigDecimal percent = new BigDecimal(value.replace(",", ".").trim());
                    String resp = "{\"items\": [ ";
                    List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
                    for (int i = 0; i < invoiceItems.size(); i++) {
                        InvoiceItem invoiceItem = invoiceItems.get(i);
                        invoiceItem.setPercent(percent);
                        Factory.getInvoiceItemDAO().makePersistent(invoiceItem);
                        df.setMinimumFractionDigits(4);
                        df.setMaximumFractionDigits(4);
                        resp += "{\"id\":\"percent" + invoiceItem.getId() + "\",\"value\":\"" +
                                df.format(invoiceItem.calculatePercent()) + "\"},";
                        df.setMinimumFractionDigits(2);
                        df.setMaximumFractionDigits(2);

                        resp += "{\"id\":\"sellingPrice" + invoiceItem.getId() + "\",\"value\":\"" +
                                df.format(invoiceItem.getSellingPrice()) + "\"},";
                        resp += "{\"id\":\"sum" + invoiceItem.getId() + "\",\"value\":\"" +
                                df.format(invoiceItem.getSum()) + "\"},";
                    }
                    resp = resp.substring(0, resp.length() - 1) + "]," +
                            "\"total\":\"" + df.format(invoice.getTotal()) + "\"," +
                            "\"sum\":\"" + df.format(invoice.getSum()) + "\"," +
                            "\"nds\":\"" + df.format(invoice.getNDSPayment()) + "\"}";
                    response.getWriter().write(resp);
                } else if (param.equals("commonTransportCost")) {
                    BigDecimal transportationCost = new BigDecimal(value.replace(",", ".").trim());
                    String resp = "{\"items\": [ ";
                    List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
                    for (int i = 0; i < invoiceItems.size(); i++) {
                        InvoiceItem invoiceItem = invoiceItems.get(i);
                        invoiceItem.setTransportationCost(transportationCost);
                        Factory.getInvoiceItemDAO().makePersistent(invoiceItem);
                        df.setMinimumFractionDigits(4);
                        df.setMaximumFractionDigits(4);
                        resp += "{\"id\":\"percent" + invoiceItem.getId() + "\",\"value\":\"" +
                                df.format(invoiceItem.calculatePercent()) + "\"},";
                        df.setMinimumFractionDigits(2);
                        df.setMaximumFractionDigits(2);
                        resp += "{\"id\":\"transportationCost" + invoiceItem.getId() + "\",\"value\":\"" +
                                df.format(invoiceItem.getTransportationCost()) + "\"},";
                        resp += "{\"id\":\"sellingPrice" + invoiceItem.getId() + "\",\"value\":\"" +
                                df.format(invoiceItem.getSellingPrice()) + "\"},";
                        resp += "{\"id\":\"sum" + invoiceItem.getId() + "\",\"value\":\"" +
                                df.format(invoiceItem.getSum()) + "\"},";
                    }
                    resp = resp.substring(0, resp.length() - 1) + "]," +
                            "\"total\":\"" + df.format(invoice.getTotal()) + "\"," +
                            "\"sum\":\"" + df.format(invoice.getSum()) + "\"," +
                            "\"nds\":\"" + df.format(invoice.getNDSPayment()) + "\"}";
                    response.getWriter().write(resp);
                } else if (param.equals("roundPrice")) {
                    Integer roundValue = new Integer(value);
                    String resp = "{\"items\": [ ";
                    List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();

                    for (int i = 0; i < invoiceItems.size(); i++) {


                        InvoiceItem invoiceItem = invoiceItems.get(i);
                        BigDecimal price = invoiceItem.getSellingPrice();
                        price = price.movePointLeft(3);
                        price = price.setScale(roundValue, BigDecimal.ROUND_HALF_DOWN);
                        price = price.movePointRight(3);
                        invoiceItem.setSellingPrice(price);
                        Factory.getInvoiceItemDAO().makePersistent(invoiceItem);
                        df.setMinimumFractionDigits(4);
                        df.setMaximumFractionDigits(4);
                        resp += "{\"id\":\"percent" + invoiceItem.getId() + "\",\"value\":\"" + df.format(invoiceItem.calculatePercent()) + "\"},";
                        df.setMinimumFractionDigits(2);
                        df.setMaximumFractionDigits(2);
                        resp += "{\"id\":\"sellingPrice" + invoiceItem.getId() + "\",\"value\":\"" + df.format(invoiceItem.getSellingPrice()) + "\"},";
                        resp += "{\"id\":\"sum" + invoiceItem.getId() + "\",\"value\":\"" + df.format(invoiceItem.getSum()) + "\"},";
                    }

                    resp = resp.substring(0, resp.length() - 1) + "]" +
                            ",\"total\":\"" + df.format(invoice.getTotal().divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP)) + "\"," +
                            "\"sum\":\"" + df.format(invoice.getSum().divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP)) +
                            "\",\"nds\":\"" + df.format(invoice.getNDSPayment().divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP)) + "\"}";

                    // System.out.println(resp);
                    response.getWriter().write(resp);
                } else if (param.equals("NDS")) {
                    invoice.setNDS(new BigDecimal(value.replace(",", ".")));
                    String resp = "{\"items\": []" +
                            ",\"total\":\"" + df.format(invoice.getTotal()) + "\"" +
                            ",\"sum\":\"" + df.format(invoice.getSum()) + "\"," +
                            "\"nds\":\"" + df.format(invoice.getNDSPayment()) + "\"}";


                    response.getWriter().write(resp);


                } else if (param.equals("exchangeRate")) {
                    BigDecimal exchangeRate = new BigDecimal(value.replace(",", "."));
                    BigDecimal oldExcangeRate = invoice.getExchangeRate();
                    invoice.setExchangeRate(exchangeRate);
                    String resp = "{\"items\": [ ";
                    List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
                    for (int i = 0; i < invoiceItems.size(); i++) {
                        InvoiceItem invoiceItem = invoiceItems.get(i);
                        BigDecimal sellingPrice = invoiceItem.getSellingPrice().multiply(oldExcangeRate).divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP);
                        resp += "{\"id\":\"sellingPrice" + invoiceItem.getId() + "\",\"value\":\"" +
                                df.format(sellingPrice) + "\"},";
                        invoiceItem.setSellingPrice(sellingPrice);
                        Factory.getInvoiceItemDAO().makePersistent(invoiceItem);
                        resp += "{\"id\":\"sum" + invoiceItem.getId() + "\",\"value\":\"" + df.format(invoiceItem.getSum()) + "\"},";
                    }

                    resp = resp.substring(0, resp.length() - 1) + "]" +
                            ",\"total\":\"" + df.format(invoice.getTotal()) + "\"," +
                            "\"sum\":\"" + df.format(invoice.getSum()) +
                            "\",\"nds\":\"" + df.format(invoice.getNDSPayment()) + "\"}";

                    response.getWriter().write(resp);


                }
                //#######################################

                //  invoice.setChangeDate(new Date());
                //    Factory.getBookingDAO().makePersistent(invoice.getBooking());
                Factory.getInvoiceDAO().makePersistent(invoice);
                // Factory.getInvoiceDAO().update(invoice);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public ActionForward openOrder(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response) {
        // printProps(request);
        Booking booking = new Booking();
        try {
            DateFormat f = new SimpleDateFormat("dd.MM.yyyy");
            Invoice invoice = Factory.getInvoiceDAO().findById(new Long(request.getParameter("invoceId")));


            booking.setDate(f.parse(request.getParameter("date")));
            booking.setNumber(new Integer(request.getParameter("number")));
            booking.setNumberModifier(request.getParameter("numberModifier"));
            booking.setCurrentState(0);
            booking.setInvoice(invoice);
            invoice.setBooking(booking);
            invoice.setCurrentState(Invoice.STATE_ZAK);
            Factory.getBookingDAO().makePersistent(booking);
            Factory.getInvoiceDAO().makePersistent(invoice);

            //  request.setAttribute("invoice", invoice);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Invoice invoice = Factory.getInvoiceDAO().findById(new Long(param));
        //  request.setAttribute("id", booking.getId());

        ActionForward actionForward = new ActionForward();
        actionForward.setPath("/invoiceAction.do?method=viewBooking&id=" + booking.getId());
        actionForward.setRedirect(true);
        return actionForward;


        // return mapping.findForward("bookingForm");
    }


    public ActionForward addInvoice(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response) {
        //   printProps(request);
        try {
            Invoice invoice = new Invoice();
            // User u = (User) request.getSession().getAttribute("logonUser");
            Customer emploer = Factory.getCustomerDAO().findByShortName(request.getParameter("emploer"));
            invoice.setEmploer(emploer);

            Customer consumer = Factory.getCustomerDAO().findByShortName(request.getParameter("consumer"));
            invoice.setConsumer(consumer);


            invoice.setExecutor(emploer.getPerson());
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
            invoice.setPaymentOnTheNitice(new BigDecimal(0));
            invoice.setPrepayment(new BigDecimal(0));
            invoice.setPaymentOnTheNitice(new BigDecimal(0));
            invoice.setPostpay(new BigDecimal(0));
            invoice.setCurrentState(Invoice.STATE_CHERN);
            invoice.setDeliveryTime("45-60 дней с момента предоплаты.");


            List<Supplier> suppliers = Factory.getSupplierDAO().findAll();
            if (!suppliers.isEmpty()) {
                invoice.setSupplier(suppliers.get(0));
                invoice.setExchangeRate(suppliers.get(0).getCurrency().getExchangeRate());
            }

            invoice.setInvoiceItems(new LinkedList<InvoiceItem>());
            Factory.getInvoiceDAO().makePersistent(invoice);

            //  System.out.println("invoiceId = " + invoice.getId());
            ActionForward actionForward = new ActionForward();
            actionForward.setPath("/invoiceAction.do?method=viewInvoice&id=" + invoice.getId());
            actionForward.setRedirect(true);
            return actionForward;
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("invoices");
        }
    }

    public ActionForward invFromKp(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response) {
    //    printProps(request);
        try {
            Invoice parent = Factory.getInvoiceDAO().findById(new Long(request.getParameter("parentID")));
            Invoice newInvoice = new Invoice();
            if (parent.getEmploer() != null)
                newInvoice.setEmploer(Factory.getCustomerDAO().findById(parent.getEmploer().getId()));
            if (parent.getConsumer() != null)
                newInvoice.setConsumer(Factory.getCustomerDAO().findById(parent.getConsumer().getId()));


            newInvoice.setExecutor(parent.getExecutor());
            newInvoice.setNDS(parent.getNDS());
            newInvoice.setPurpose(parent.getPurpose());
            newInvoice.setCurrentState(Invoice.STATE_MOD);
            DateFormat f = new SimpleDateFormat("dd.MM.yyyy");

            newInvoice.setDate(f.parse(request.getParameter("date")));
            newInvoice.setChangeDate(new Date());

            newInvoice.setInvoice(new Boolean(request.getParameter("isInvoice")));
            newInvoice.setNumber(new Integer(request.getParameter("number")));
            newInvoice.setNumberModifier(request.getParameter("numberModifier"));
            newInvoice.setValidity(parent.getValidity());
            newInvoice.setPaymentOnTheNitice(parent.getPaymentOnTheNitice());
            newInvoice.setPrepayment(parent.getPrepayment());
            newInvoice.setPostpay(parent.getPostpay());
            newInvoice.setDeliveryTime(parent.getDeliveryTime());
            newInvoice.setSupplier(parent.getSupplier());
            newInvoice.setExchangeRate(parent.getExchangeRate());

            newInvoice.setInvoiceItems(new LinkedList<InvoiceItem>());
            if (parent.getT0() != null)
                newInvoice.setT0(new Integer(parent.getT0()));
            if (parent.getT1() != null)
                newInvoice.setT1(new Integer(parent.getT1()));
            if (parent.getT2() != null)
                newInvoice.setT2(new Integer(parent.getT2()));
            if (parent.getT3() != null)
                newInvoice.setT3(new Integer(parent.getT3()));
            if (parent.getT4() != null)
                newInvoice.setT4(new Integer(parent.getT4()));
            if (parent.getT5() != null)
                newInvoice.setT5(new Integer(parent.getT5()));


            Factory.getInvoiceDAO().makePersistent(newInvoice);
            for (InvoiceItem item : parent.getInvoiceItems()) {
                InvoiceItem clone = item.getClone();
                clone.setInvoice(newInvoice);
                Factory.getInvoiceItemDAO().makePersistent(clone);
                newInvoice.addInvoiceItems(clone);
            }


            Factory.getInvoiceDAO().makePersistent(newInvoice);


            //  System.out.println("invoiceId = " + newInvoice.getId());
            ActionForward actionForward = new ActionForward();
            actionForward.setPath("/invoiceAction.do?method=viewInvoice&id=" + newInvoice.getId());
            actionForward.setRedirect(true);
            return actionForward;
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("invoices");
        }
    }


    public ActionForward copyInvoice(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response) {
    //    printProps(request);
        try {
            Invoice parent = Factory.getInvoiceDAO().findById(new Long(request.getParameter("parentID")));
            Invoice newInvoice = new Invoice();
            Customer emploer = Factory.getCustomerDAO().findByShortName(request.getParameter("emploer"));
            newInvoice.setEmploer(emploer);

            Customer consumer = Factory.getCustomerDAO().findByShortName(request.getParameter("consumer"));
            newInvoice.setConsumer(consumer);


            newInvoice.setExecutor(emploer.getPerson());
//            newInvoice.setExecutor(u);
            newInvoice.setNDS(new BigDecimal("20"));
//            newInvoice.setExchangeRate(new BigDecimal("1"));
            newInvoice.setPurpose(Invoice.PURPOSE_POSTAVKA);

            DateFormat f = new SimpleDateFormat("dd.MM.yyyy");

            newInvoice.setDate(f.parse(request.getParameter("date")));
            // newInvoice.setChangeDate(new Date());

            newInvoice.setInvoice(new Boolean(request.getParameter("isInvoice")));
            newInvoice.setNumber(new Integer(request.getParameter("number")));
            newInvoice.setNumberModifier(request.getParameter("numberModifier"));
            newInvoice.setValidity(10);
            newInvoice.setPaymentOnTheNitice(new BigDecimal(0));
            newInvoice.setPrepayment(new BigDecimal(0));
            newInvoice.setPaymentOnTheNitice(new BigDecimal(0));
            newInvoice.setPostpay(new BigDecimal(0));
            newInvoice.setCurrentState(Invoice.STATE_CHERN);
            newInvoice.setDeliveryTime("45-60 дней с момента предоплаты.");


            List<Supplier> suppliers = Factory.getSupplierDAO().findAll();
            if (!suppliers.isEmpty()) {
                newInvoice.setSupplier(suppliers.get(0));
                newInvoice.setExchangeRate(suppliers.get(0).getCurrency().getExchangeRate());
            }

                       newInvoice.setInvoiceItems(new LinkedList<InvoiceItem>());
            if (parent.getT0() != null)
                newInvoice.setT0(new Integer(parent.getT0()));
            if (parent.getT1() != null)
                newInvoice.setT1(new Integer(parent.getT1()));
            if (parent.getT2() != null)
                newInvoice.setT2(new Integer(parent.getT2()));
            if (parent.getT3() != null)
                newInvoice.setT3(new Integer(parent.getT3()));
            if (parent.getT4() != null)
                newInvoice.setT4(new Integer(parent.getT4()));
            if (parent.getT5() != null)
                newInvoice.setT5(new Integer(parent.getT5()));


            Factory.getInvoiceDAO().makePersistent(newInvoice);
            for (InvoiceItem item : parent.getInvoiceItems()) {
                InvoiceItem clone = item.getClone();
                clone.setInvoice(newInvoice);
                Factory.getInvoiceItemDAO().makePersistent(clone);
                newInvoice.addInvoiceItems(clone);
            }


            Factory.getInvoiceDAO().makePersistent(newInvoice);

            //  System.out.println("invoiceId = " + newInvoice.getId());
            ActionForward actionForward = new ActionForward();
            actionForward.setPath("/invoiceAction.do?method=viewInvoice&id=" + newInvoice.getId());
            actionForward.setRedirect(true);
            return actionForward;
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("invoices");
        }
    }


    public ActionForward viewInvoice(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response) {
        String param = request.getParameter("id");
        Invoice invoice = Factory.getInvoiceDAO().findById(new Long(param));
        request.setAttribute("invoice", invoice);
        return mapping.findForward("edit");
    }

    public ActionForward viewBooking(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response) {
        String param = request.getParameter("id");
        // Booking booking = Factory.getBookingDAO().findById();

        request.setAttribute("id", new Long(param));
        return mapping.findForward("bookingForm");
    }


    public ActionForward deleteInvoice(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Invoice invoice;
        try {
            String param = request.getParameter("id");
            if (param != null)
                if (!param.isEmpty()) {

                    invoice = Factory.getInvoiceDAO().findById(new Long(param));


                    Factory.getInvoiceDAO().delete(invoice);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ActionForward forward = new ActionForward();
        forward.setPath("/invoiceAction.do?method=viewInvoices");
        forward.setRedirect(true);
        return forward;


    }


    public ActionForward deleteInvoiceItem(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        InvoiceItem invoiceItem;
        try {
            String param = request.getParameter("id");
            if (param != null) if (!param.isEmpty()) {

                invoiceItem = Factory.getInvoiceItemDAO().findById(new Long(param));

                Invoice invoice = invoiceItem.getInvoice();
                invoice.getInvoiceItems().remove(invoiceItem);

                Integer count;


                switch (invoiceItem.getType())

                {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        count = invoice.getT0();
                        count -= invoiceItem.getQuantity();
                        invoice.setT0(count);
                        break;
                    case 4:
                        count = invoice.getT1();
                        count -= invoiceItem.getQuantity();
                        invoice.setT1(count);
                        break;


                    case 5:
                        count = invoice.getT3();
                        count -= invoiceItem.getQuantity();
                        invoice.setT3(count);
                        break;
                    case 6:
                        count = invoice.getT2();
                        count -= invoiceItem.getQuantity();
                        invoice.setT2(count);
                        break;
                    case 7:
                    case 8:
                        count = invoice.getT4();
                        count -= invoiceItem.getQuantity();
                        invoice.setT4(count);
                        break;


                    case 9:
                        count = invoice.getT5();
                        count -= invoiceItem.getQuantity();
                        invoice.setT5(count);
                        break;


                }


                Factory.getInvoiceDAO().makePersistent(invoice);
                Factory.getInvoiceItemDAO().delete(invoiceItem);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ActionForward forward = new ActionForward();
        forward.setPath("/invoiceAction.do?method=viewInvoice&id=" + request.getParameter("invoiceId"));
        forward.setRedirect(true);
        return forward;
    }

    private void printProps(HttpServletRequest request) {
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String param = en.nextElement();
            // System.out.print(param + " -- ");
            // System.out.println(request.getParameter(param));
            for (String s : request.getParameterValues(param))
                System.out.println(param + " = " + s);
        }
    }

    public ActionForward findCustomers(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String q = request.getParameter("q");
        if (q != null && q != "") {
            List<Customer> result = Factory.getCustomerDAO().findBySortNameExample(q);
            if (result != null)
                response.setContentType("text/html; charset=UTF-8");
            for (Customer customer : result) {
                String s = customer.getShortName() + "|" + customer.getId()
                        + "\n";

                response.getWriter().write(s);
            }

        }
        return null;
    }


    public ActionForward moveUpItem(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Invoice invoice = Factory.getInvoiceDAO().findById(new Long(request.getParameter("invoiceId")));
        List<InvoiceItem> list = invoice.getInvoiceItems();

        Integer position = new Integer(request.getParameter("id"));


        if ((position > 0) & (position < list.size())) {
//            InvoiceItem cur = list.get(position);
//            InvoiceItem o = list.get(position - 1);
//            list.set(position, o);
//            list.set(position - 1, cur);

            Collections.swap(list, position, position - 1);
            invoice.setInvoiceItems(list);
            Factory.getInvoiceDAO().makePersistent(invoice);
        }


        ActionForward forward = new ActionForward();
        forward.setPath("/invoiceAction.do?method=viewInvoice&id=" + request.getParameter("invoiceId"));
        forward.setRedirect(true);
        return forward;
    }

    public ActionForward moveDownItem(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Invoice invoice = Factory.getInvoiceDAO().findById(new Long(request.getParameter("invoiceId")));
        List<InvoiceItem> list = invoice.getInvoiceItems();

        Integer position = new Integer(request.getParameter("id"));
        if ((position >= 0) & (position < (list.size() - 1))) {
            Collections.swap(list, position, position + 1);
            invoice.setInvoiceItems(list);
            Factory.getInvoiceDAO().makePersistent(invoice);
        }
        ActionForward forward = new ActionForward();
        forward.setPath("/invoiceAction.do?method=viewInvoice&id=" + request.getParameter("invoiceId"));
        forward.setRedirect(true);
        return forward;
    }


    public ActionForward downloadArch(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // printProps(request);
        Invoice invoice = Factory.getInvoiceDAO().findById(new Long(request.getParameter("invoiceId")));
        ServletContext context = this.getServlet().getServletContext();
        String type = request.getParameter("type");
        try {
//            System.out.println("context.getRealPath(\"/disign/invoice2.jrxml\") =" + context.getRealPath("/disign/invoice2.jrxml"));
//            JasperReport report = JasperCompileManager.compileReport(context.getRealPath("/disign/invoice2.jrxml"));
            JasperReport report = JasperCompileManager.compileReport(context.getRealPath("/disign/invoice.jrxml"));
            Map parameters = new HashMap();
            String title = (invoice.isInvoice() ? "Счет-фактура № " : "Коммерческое предложение № ") + invoice.getNumber();
            title += (invoice.getNumberModifier().isEmpty()) ? "" : ("/" + invoice.getNumberModifier());
            // FontFactory.register("D:\\ARIAL.TTF");
            // FontFactory.register();
//            System.out.println(title);
//            parameters.put("Title", title);
            parameters.put("date", (new SimpleDateFormat("dd.MM.yyyy")).format(invoice.getDate()));
            parameters.put("orgForm", invoice.getEmploer().getOrgForm().getName());
            parameters.put("emploer", invoice.getEmploer().getName());
            parameters.put("name", invoice.getSupplier().getName());
            parameters.put("address", invoice.getSupplier().getAddress());
            parameters.put("phone", invoice.getSupplier().getPhone());
            parameters.put("erdpou", invoice.getSupplier().getErdpou());

            parameters.put("currency", invoice.getSupplier().getCurrency().getName());
            parameters.put("NDSPayment", invoice.getNDSPayment());
            parameters.put("total", invoice.getTotal());
            parameters.put("sum", invoice.getSum());
            parameters.put("nds", invoice.getNDS());

            parameters.put("strTotal", (new jAmount(invoice.getSupplier().getCurrency().getId().intValue()
                    , invoice.getTotal().divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP).toString())).toString());

            parameters.put("paymentTerms", (new jAmount(1, invoice.getTotal().toString())).toString());
            parameters.put("deliveryTerms", (new jAmount(1, invoice.getTotal().toString())).toString());
            parameters.put("delivery", (new jAmount(1, invoice.getTotal().toString())).toString());
            parameters.put("invoice", invoice);
            parameters.put("path", context.getRealPath("/images/reportImages/"));
            parameters.put("city", Factory.getCityDAO().findById(invoice.getEmploer().getCity()).getName());


            OutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");

            if (type.equals("xls")) {
                parameters.put("type", ".jpg");
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(invoice.getInvoiceItems()));
                response.setHeader("Content-Disposition", "attachment;filename=invoice.xls");
                JRXlsExporter jrXlsExporter = new JRXlsExporter();
                jrXlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jrXlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                jrXlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                jrXlsExporter.exportReport();
            } else if (type.equals("pdf")) {
                parameters.put("type", ".wmf");
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(invoice.getInvoiceItems()));
                response.setHeader("Content-Disposition", "attachment;filename=invoice.pdf");
                JasperExportManager.exportReportToPdfStream(jasperPrint, out);

            } else if (type.equals("odt")) {
                parameters.put("type", ".jpg");
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(invoice.getInvoiceItems()));
                response.setHeader("Content-Disposition", "attachment;filename=invoice.odt");

                JROdtExporter exporter = new JROdtExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                exporter.exportReport();
            }

//            response.setHeader("Content-Disposition", "attachment;filename=invoice.odt");
//            JRDocxExporter exporter = new JRDocxExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
//            exporter.exportReport();


            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }


    public ActionForward bookingPrint(ActionMapping mapping, ActionForm form,
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

    public ActionForward addShipment(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {

//        printProps(request);

        String param = request.getParameter("invoiceId");
        Invoice invoice = Factory.getInvoiceDAO().findById(new Long(param));

        Shipment shipment = new Shipment();

        shipment.setDate((new SimpleDateFormat("dd.MM.yyyy")).parse(request.getParameter("date")));
        shipment.setShipmentNum(request.getParameter("shipmentNum"));
        for (InvoiceItem item : invoice.getInvoiceItems()) {
            param = request.getParameter("invItId" + item.getId());
            if ((param != null) && (!param.isEmpty())) {
                ShipmentMediator sm = new ShipmentMediator(item, new Integer(param));
                shipment.addShippingMediator(sm);
                item.addShippingMediators(sm);
                Factory.getInvoiceItemDAO().makePersistent(item);
            }

        }

        if ((shipment.getShippingMediators() != null) && (!shipment.getShippingMediators().isEmpty())) {

            shipment.setInvoice(invoice);
            // Factory.getShipmentDAO().makePersistent(shipment);
            invoice.addShipment(shipment);

            if (invoice.isDeliveryMade()) {
                invoice.getBooking().setCurrentState(Booking.STATE_OTGR);
                invoice.getBooking().setDateOfDeviveryMade(new Date());
                if (invoice.isPaymentMade()) {
                    invoice.getBooking().setCurrentState(Booking.STATE_ISP);
                    invoice.setCurrentState(Invoice.STATE_ISP);
                }
            }

            Factory.getBookingDAO().makePersistent(invoice.getBooking());
            Factory.getInvoiceDAO().makePersistent(invoice);


        }


        ActionForward forward = new ActionForward();
        forward.setPath("/invoiceAction.do?method=viewShipments&invoiceId=" + request.getParameter("invoiceId"));
        forward.setRedirect(true);
        return forward;
    }


    public ActionForward addPayment(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        //   printProps(request);

        String param = request.getParameter("invoiceId");
        Invoice invoice = Factory.getInvoiceDAO().findById(new Long(param));
        try {
            Payment payment = new Payment();
//                                              
            payment.setExchangeRate(new BigDecimal(request.getParameter("exchangeRate").replaceAll("[^0-9,.]", "").replace(",", ".")));
            payment.setPaymentSum(new BigDecimal(request.getParameter("paymentSum").replaceAll("[^0-9,.-]", "").replace(",", ".")));
            payment.setPurpose(new Integer(request.getParameter("purpose")));
            payment.setDate((new SimpleDateFormat("dd.MM.yyyy")).parse(request.getParameter("date")));
            // payment.setInvoice(invoice);
            Factory.getPaymentDAO().makePersistent(payment);
            invoice.addPayment(payment);
            if (invoice.isDeliveryMade() && invoice.isPaymentMade()) {
                invoice.getBooking().setCurrentState(Booking.STATE_ISP);
                invoice.setCurrentState(Invoice.STATE_ISP);
            }


            Factory.getInvoiceDAO().makePersistent(invoice);
        } catch (Exception e) {
            e.printStackTrace();
        }


        ActionForward forward = new ActionForward();
        forward.setPath("/invoiceAction.do?method=viewPayments&invoiceId=" + request.getParameter("invoiceId"));
        forward.setRedirect(true);
        return forward;
    }


    public ActionForward getShipmentSum(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        //   printProps(request);


        String param = request.getParameter("invoiceId");
        Invoice invoice = Factory.getInvoiceDAO().findById(new Long(param));

        BigDecimal sum = new BigDecimal("0");

        BigDecimal count;

        for (InvoiceItem item : invoice.getInvoiceItems()) {
            param = request.getParameter("invItId" + item.getId());
            if ((param != null) && (!param.isEmpty())) {
                count = new BigDecimal(param);
                sum = sum.add(item.getSellingPrice().multiply(count));
            }

        }
        NumberFormat df = NumberFormat.getInstance();
        df.setMinimumFractionDigits(2);

        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(df.format(sum));

        return null;
    }

    public ActionForward checkPossibilityOf(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String param = request.getParameter("param");
        String id = request.getParameter("id");
        Invoice invoice = Factory.getInvoiceDAO().findById(new Long(id));
        String resp = "";
        if (param.equals("ann")) {
            if ((invoice.getBooking() == null))

            {
                resp = "{\"response\":\"ok\"}";
            } else {
                resp = (invoice.getBooking().getCurrentState().equals(Booking.STATE_ANN)) ? "{\"response\":\"ok\"}" : "{\"response\":\"Состояние з.н. должно быть «аннулирован».\"}";
            }

        } else if (param.equals("otl")) {
            if ((invoice.getControlDate() != null) && (invoice.getControlDate().after(new Date()))) {
                resp = "{\"response\":\"ok\"}";
            } else {
                resp = "{\"response\":\"Поле «Контроль» д.б. заполненно, дата больше текущей.\"}";
            }


        }

        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(resp);

        return null;
    }


    public ActionForward editBookingParams(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response) {
        //  printProps(request);

        try {
            String param = request.getParameter("param");
            String id = request.getParameter("id");
            String value = request.getParameter("value");
            Booking booking = null;
            response.setContentType("text/html; charset=UTF-8");
            if (id != null) if (!id.isEmpty()) {
                booking = Factory.getBookingDAO().findById(new Long(id));
            }
            if (booking != null) {
                Invoice invoice = booking.getInvoice();
                if (param.equals("bookingNotes")) {
                    booking.setNotes(value);
                } else if (param.equals("bookingComments")) {
                    booking.setComments(value);
                } else if (param.equals("currentState")) {
                    Boolean validate = true;
                    Integer state = new Integer(value);

                    switch (state) {
                        case 0:
                            // booking.setDate(null);
                            break;
                        case 1:
                            TreeSet<Date> set = new TreeSet();
                            for (InvoiceItem item : invoice.getInvoiceItems())
                                set.add(InvoiceItem.dateBeforeNDays(booking.getDate(), item.getDeliveryTime()));
                            booking.setSupplierObligations1(set.first());
                            booking.setSupplierObligations2(set.last());
                            break;
                        case 2:
                            booking.setDateOfNoticeOpening(new Date());
                            break;
                        case 6:

                            if (!(
                                    invoice.getPayments().isEmpty() &&
                                            invoice.getShipments().isEmpty())) {
                                response.getWriter().write("{\"res\":\"Были отгрузки или оплаты. Невозможно приостановить.\"}");
                                validate = false;
                            }
                            break;
                        case 7:
                            // System.out.println(invoice.getTotalPayments()+" "+ invoice.getTotalPayments().compareTo(BigDecimal.ZERO));
                            if (!(invoice.isAnyGoodsNotShipped() && (invoice.getTotalPayments().compareTo(BigDecimal.ZERO) == 0))) {
                                response.getWriter().write("{\"res\":\"Сумма отгрузок и сумма оплат должна равняться нулю.\"}");
                                validate = false;
                            }


                            break;

                    }

                    if (validate) {
                        booking.setCurrentState(state);
                        response.getWriter().write("{\"res\":\"ok\"}");
                        Factory.getBookingDAO().makePersistent(booking);
                    }

                }
                //#######################################


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }


}
