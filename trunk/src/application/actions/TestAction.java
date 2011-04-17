package application.actions;

import application.data.ModelDescription.ModelDescription;
import application.data.invoice.Invoice;
import application.data.invoice.PressureSensor;
import application.data.invoice.Production;
import application.data.price.PriceFirstPart;
import application.data.price.ProductionPrice;
import application.hibernate.Factory;
import application.hibernate.generic.GenericHibernateDAO;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class TestAction extends DispatchAction {
    String[] isp = {"общ.", "вн", "Ex", "AC", "К"};

    public ActionForward redactCoForward(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //   System.out.println("TestAction.redactCoForward");
        //  System.out.println("Session id = " + request.getSession().getId());
        //  System.out.println("");
        return mapping.findForward("redactCo");
    }

    public ActionForward redactAoForward(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return mapping.findForward("redactAo");
    }

    public ActionForward redactOpForward(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return mapping.findForward("redactOp");
    }

    //#############################################################################################

    public ActionForward addCoForm(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return mapping.findForward("addCoForm");
    }

    public ActionForward addAoForm(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return mapping.findForward("addAoForm");
    }

    public ActionForward addOpForm(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return mapping.findForward("addOpForm");
    }

    public ActionForward addListForm(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return mapping.findForward("addListForm");
    }


    public ActionForward addList(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //    printProps(request);
        // добавляем списочную продукцию
        String invoiceId = request.getParameter("invoiceId");
        String productionId = request.getParameter("productionId");
        Integer type = new Integer(request.getParameter("type"));

        if (invoiceId != null) {
            Production production = new Production();
            Invoice i = Factory.getInvoiceDAO().findById(new Long(invoiceId));
            ProductionPrice productionPrice = Factory.getProductionDAO().findById(new Long(productionId));
            production.setName(productionPrice.getName());
            production.setCost(productionPrice.getCost());
            production.setPrice(productionPrice.getPrice());
            production.setQuantity(1);
            production.setDeliveryTime(45);
            production.setType(type);
            production.setInvoice(i);
            production.setTransportationCost(new BigDecimal("0"));
            production.setAdditionalCost(new BigDecimal("0"));
            production.setupMoneyFields(new BigDecimal("1.3"));


            Integer count;
            switch (type) {
                case 3:
                    count = i.getT0();
                    if (count == null) {
                        count = 1;
                    } else {
                        count += 1;
                    }
                    i.setT0(count);
                    break;


                case 4:
                    count = i.getT1();
                    if (count == null) {
                        count = 1;
                    } else {
                        count += 1;
                    }
                    i.setT1(count);
                    break;


                case 5:
                    count = i.getT3();
                    if (count == null) {
                        count = 1;
                    } else {
                        count += 1;
                    }
                    i.setT3(count);
                    break;
                case 6:
                    count = i.getT2();
                    if (count == null) {
                        count = 1;
                    } else {
                        count += 1;
                    }
                    i.setT2(count);
                    break;
                case 7:
                case 8:
                    count = i.getT4();
                    if (count == null) {
                        count = 1;
                    } else {
                        count += 1;
                    }
                    i.setT4(count);
                    break;


                case 9:
                    count = i.getT5();
                    if (count == null) {
                        count = 1;
                    } else {
                        count += 1;
                    }
                    i.setT5(count);
                    break;


            }


            Factory.getInvoiceItemDAO().makePersistent(production);
            i.addInvoiceItems(production);
            Factory.getInvoiceDAO().makePersistent(i);
        }


        ActionForward actionForward = new ActionForward();
        actionForward.setPath("/invoiceAction.do?method=viewInvoice&id=" + invoiceId);
        actionForward.setRedirect(true);
        return actionForward;

    }

    //#############################################################################

    public ActionForward getPressureSensor(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response) {
        try {
            String invoiceItemId = request.getParameter("invoiceItemId");
            PressureSensor item = (PressureSensor) Factory.getInvoiceItemDAO().findById(new Long(invoiceItemId));
            ModelDescription modelDescription = Factory.getModelDescriptionDAO().findById(new Long(item.getModel()));
            response.setContentType("text/html; charset=UTF-8");

            response.getWriter().write("{\"model\":" + modelDescription.toJSONString() + ",\"item\":" + item.toJSONString() + "}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public ActionForward addPressureSensor(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response) {

     //   printProps(request);
//        System.out.println("*****************************");
        String invoiceId = request.getParameter("invoiceId").trim();
        String invoiceItemId = request.getParameter("invoiceItemId").trim();
        PressureSensor pressureSensor;
        if (invoiceItemId.equals("0")) {
            pressureSensor = new PressureSensor();

        } else {
            pressureSensor = (PressureSensor) Factory.getInvoiceItemDAO().findById(new Long(invoiceItemId));
        }


        try {


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
            pressureSensor.setStat((request.getParameter("stat") == null) ? 0 : new Integer(request.getParameter("stat")));
            pressureSensor.setOutType(new Integer(request.getParameter("out")));
            pressureSensor.setKmch((request.getParameter("kmch") == null) ? 0 : new Integer(request.getParameter("kmch")));
            pressureSensor.setDu((request.getParameter("du") == null) ? 0 : new Integer(request.getParameter("du")));
            pressureSensor.setR(request.getParameter("r") != null);
            pressureSensor.setI(request.getParameter("i") != null);
            pressureSensor.setHIM(request.getParameter("him") != null);
            pressureSensor.setPI(request.getParameter("pi") != null);
            pressureSensor.setTU(request.getParameter("tu") != null);
            pressureSensor.setAfterSpec(request.getParameter("afterSpec"));


//            if (invoiceId != null) {


            if (invoiceItemId.equals("0")) {
                Invoice i = Factory.getInvoiceDAO().findById(new Long(invoiceId));
                pressureSensor.setQuantity(1);
                pressureSensor.setDeliveryTime(45);
                pressureSensor.setInvoice(i);
                pressureSensor.setTransportationCost(new BigDecimal("0"));
                pressureSensor.setAdditionalCost(new BigDecimal("0"));

                pressureSensor.setupMoneyFields(new BigDecimal("1.3"));

                i.addInvoiceItems(pressureSensor);
                Factory.getInvoiceItemDAO().makePersistent(pressureSensor);

                Integer count = i.getT0();
                if (count == null) {
                    count = 1;
                } else {
                    count += 1;
                }
                i.setT0(count);


                //Factory.getInvoiceItemDAO().makePersistent()
                Factory.getInvoiceDAO().makePersistent(i);
                //
            } else {
                BigDecimal koef = pressureSensor.calculatePercent();

                pressureSensor.setupMoneyFields(koef);

                //  pressureSensor.setId(new Long(invoiceItemId));
                Factory.getInvoiceItemDAO().makePersistent(pressureSensor);
            }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ActionForward actionForward = new ActionForward();
        actionForward.setPath("/invoiceAction.do?method=viewInvoice&id=" + invoiceId);
        actionForward.setRedirect(true);
        return actionForward;
    }

//    public ActionForward redactAo(ActionMapping mapping, ActionForm form,
//                                  HttpServletRequest request, HttpServletResponse response)
//            throws Exception {
//        try {
//            //   printProps(request);
//            GenericHibernateDAO<ModelAoDescription> dao = Factory.getModelAoDescriptionDAO();
//            List<ModelAoDescription> list = new LinkedList<ModelAoDescription>();
//
//
//            String elements = request.getParameter("elements");
//            System.out.println(elements);
//            StringTokenizer tokenizer = new StringTokenizer(elements, "|", false);
//            for (; tokenizer.hasMoreTokens();) {
//                String model = tokenizer.nextToken();
//                // System.out.println(type);
//
//                ModelAoDescription result = dao.findById(new Long(model));
//                if (result != null) {
//                    list.add(result);
//                }
//
//            }
//
//            String param = request.getParameter("param");
//
//            if (param.equals("limits")) {
//                String loLim = request.getParameter("loLim");
//                String hiLim = request.getParameter("hiLim");
//                //   System.out.println(loLim+"--"+hiLim);
//                if (loLim != null && loLim != "" && loLim != "undefined" && hiLim != null && hiLim != "" && hiLim != "undefined") {
//                    Integer h = new Integer(hiLim);
//                    Integer l = new Integer(loLim);
//                    for (ModelAoDescription item : list) {
//                        item.setLoLimit(l);
//                        item.setHiLimit(h);
//
//                        dao.makePersistent(item);
//                    }
//                }
//
//
//            } else if (param.equals("isp")) {
//                String val = request.getParameter("val");
//                for (ModelAoDescription item : list) {
//                    item.setIsp(val);
//                    dao.makePersistent(item);
//                }
//            } else if (param.equals("mat")) {
//                String val = request.getParameter("val");
//                for (ModelAoDescription item : list) {
//                    item.setMaterials(val);
//                    dao.makePersistent(item);
//                }
//            } else if (param.equals("err")) {
//                String val = request.getParameter("val");
//                for (ModelAoDescription item : list) {
//                    item.setErrors(val);
//                    dao.makePersistent(item);
//                }
//            } else if (param.equals("stat")) {
//                String val = request.getParameter("val");
//                for (ModelAoDescription item : list) {
//                    item.setStaticPressures(val);
//                    dao.makePersistent(item);
//                }
//            } else if (param.equals("Out")) {
//                String val = request.getParameter("val");
//                for (ModelAoDescription item : list) {
//                    item.setOutputSygnals(val);
//                    dao.makePersistent(item);
//                }
//            } else if (param.equals("DU")) {
//                String val = request.getParameter("val");
//                for (ModelAoDescription item : list) {
//                    item.setDU(val);
//                    dao.makePersistent(item);
//                }
//            } else if (param.equals("KMCH")) {
//                String val = request.getParameter("val");
//                for (ModelAoDescription item : list) {
//                    item.setKMCH(val);
//                    dao.makePersistent(item);
//                }
//            }
//
//
//        } catch (Exception e) {
//        }
//        return null;
//    }

//    public ActionForward redactOp(ActionMapping mapping, ActionForm form,
//                                  HttpServletRequest request, HttpServletResponse response)
//            throws Exception {
//
//        try {
//            //   printProps(request);
//            GenericHibernateDAO<ModelOpDescription> dao = Factory.getModelOpDescriptionDAO();
//            List<ModelOpDescription> list = new LinkedList<ModelOpDescription>();
//
//
//            String elements = request.getParameter("elements");
//            StringTokenizer tokenizer = new StringTokenizer(elements, "|", false);
//            for (; tokenizer.hasMoreTokens();) {
//                String model = tokenizer.nextToken();
//                // System.out.println(type);
//
//                ModelOpDescription result = dao.findById(new Long(model));
//                if (result != null) {
//                    list.add(result);
//                }
//
//            }
//
//            String param = request.getParameter("param");
//
//            if (param.equals("limits")) {
//                String loLim = request.getParameter("loLim");
//                String hiLim = request.getParameter("hiLim");
//                //   System.out.println(loLim+"--"+hiLim);
//                if (loLim != null && loLim != "" && loLim != "undefined" && hiLim != null && hiLim != "" && hiLim != "undefined") {
//                    Integer h = new Integer(hiLim);
//                    Integer l = new Integer(loLim);
//                    for (ModelOpDescription item : list) {
//                        item.setLoLimit(l);
//                        item.setHiLimit(h);
//
//                        dao.makePersistent(item);
//                    }
//                }
//
//
//            } else if (param.equals("isp")) {
//                String val = request.getParameter("val");
//                for (ModelOpDescription item : list) {
//                    item.setIsp(val);
//                    dao.makePersistent(item);
//                }
//            } else if (param.equals("mat")) {
//                String val = request.getParameter("val");
//                for (ModelOpDescription item : list) {
//                    item.setMaterials(val);
//                    dao.makePersistent(item);
//                }
//            } else if (param.equals("err")) {
//                String val = request.getParameter("val");
//                for (ModelOpDescription item : list) {
//                    item.setErrors(val);
//                    dao.makePersistent(item);
//                }
//            } else if (param.equals("Out")) {
//                String val = request.getParameter("val");
//                for (ModelOpDescription item : list) {
//                    item.setOutputSygnals(val);
//                    dao.makePersistent(item);
//                }
//            }
//
//
//        } catch (Exception e) {
//            System.err.println(e);
//        }
//        return null;
//    }

//    public ActionForward setupAo(ActionMapping mapping, ActionForm form,
//                                 HttpServletRequest request, HttpServletResponse response)
//            throws Exception {
//
//
//        GenericHibernateDAO<ModelAoDescription> dao = Factory.getModelAoDescriptionDAO();
//        String model = request.getParameter("model");
//        ModelAoDescription md = dao.findById(new Long(model));
//        String[] mat = {"01", "02", "05", "07", "09", "11", "12"};
//        String[] err = {"0,1", "0,15", "0,25", "0,5", "1"};
//        String[] stat = {"", "0.16", "0.25", "1.6", "2.5", "4.0", "10", "16", "25", "32", "40"};
//        String[] du = {"", "50", "80"};
//        String[] limits = {"0.04", "0.063", "0.10", "0.16", "0.25"
//                , "0.40", "0.63", "1.0", "1.6", "2.5", "4.0", "6.3", "10", "16", "25", "40", "63", "100"
//                , "160", "250", "400", "630", "1.0", "1.6", "2.5", "4.0", "6.3", "10", "16", "25", "40", "63", "100"};
//        String[] out = {"42", "24", "&#8730 42", "05", "50", "&#8730 05"};
//
//        String res = "{";
//        if (md != null) {
//
//            StringTokenizer tokenizer = new StringTokenizer(md.getIsp(), "|");
//            res += " \"isp\": [";
//            for (; tokenizer.hasMoreTokens();) {
//                String token = tokenizer.nextToken();
//
//                res += "{\"type\": \"" + token + "\", \"text\": \"" + isp[new Integer(token)] + "\"},";
//            }
//            res = res.substring(0, res.length() - 1) + "], \"mat\":[";
//
//            tokenizer = new StringTokenizer(md.getIsp(), "|");
//            for (; tokenizer.hasMoreTokens();) {
//                String token = tokenizer.nextToken();
//                res += "{\"type\":\"" + token + "\", \"text\":\"" + mat[new Integer(token)] + "\"},";
//            }
//            res = res.substring(0, res.length() - 1) + "], \"err\":[";
//
//            tokenizer = new StringTokenizer(md.getErrors(), "|");
//            for (; tokenizer.hasMoreTokens();) {
//                String token = tokenizer.nextToken();
//                res += "{\"type\": \"" + token + "\", \"text\": \"" + err[new Integer(token)] + "\"},";
//            }
//            res = res.substring(0, res.length() - 1) + "],\"stat\":";
//
//            if (md.getStaticPressures().charAt(0) == '0') res += "\"\",\"out\":[";
//            else {
//                res += "[";
//                tokenizer = new StringTokenizer(md.getStaticPressures(), "|");
//                for (; tokenizer.hasMoreTokens();) {
//                    String token = tokenizer.nextToken();
//                    res += "{\"type\":\"" + token + "\", \"text\":\"" + stat[new Integer(token)] + "\"},";
//                }
//                res = res.substring(0, res.length() - 1) + "],\"out\":[";
//            }
//
//            tokenizer = new StringTokenizer(md.getOutputSygnals(), "|");
//            for (; tokenizer.hasMoreTokens();) {
//                String token = tokenizer.nextToken();
//                res += "{\"type\": \"" + token + "\", \"text\": \"" + out[new Integer(token)] + "\"},";
//            }
//            res = res.substring(0, res.length() - 1) + "],\"du\":";
//
//            if (md.getDU().charAt(0) == '0') {
//                res += "\"\",\"kmch\":[ ";
//            } else {
//                res += "[";
//                tokenizer = new StringTokenizer(md.getDU(), "|");
//                for (; tokenizer.hasMoreTokens();) {
//                    String token = tokenizer.nextToken();
//                    res += "{\"type\":\"" + token + "\", \"text\":\"" + du[new Integer(token)] + "\"},";
//                }
//                res = res.substring(0, res.length() - 1) + "],\"kmch\":[";
//            }
//
////            Integer lo = md.getLoLimit();
////            Integer hi = md.getHiLimit();
////            for (int i = lo - 1; i < hi; i++)
////                res += "{\"type\":\"" + i + "\", \"text\":\"" + limits[i] + "\"},";
////            res = res.substring(0, res.length() - 1) + "],\"kmch\":[";
//
//            tokenizer = new StringTokenizer(md.getKMCH(), "|");
//            for (; tokenizer.hasMoreTokens();) {
//                String token = tokenizer.nextToken();
//                String text = (token.equals("0")) ? "" : ("Н" + token);
//                res += "{\"type\": \"" + token + "\", \"text\": \"" + text + "\"},";
//
////                res += "{\"type\": \"" + token + "\", \"text\": \"" + token + "\"},";
//            }
//            res = res.substring(0, res.length() - 1) + "],";
//
//            res += "\"hilimit\":\"" + md.getHiLimit() + "\",";
//            res += "\"lolimit\":\"" + md.getLoLimit() + "\"";
//        }
//
//
//        response.setContentType("text/html; charset=UTF-8");
//
//        res += "}";
//
//        //    System.out.println(res);
//
//        // response.getWriter().write("<img src=\"images/delete.gif\" width=\"18\" height=\"18\" />");
//        response.getWriter().write(res);
//        //  printProps(request);
//        return null;
//    }
//
//    public ActionForward setupOp(ActionMapping mapping, ActionForm form,
//                                 HttpServletRequest request, HttpServletResponse response)
//            throws Exception {
//
//
//        GenericHibernateDAO<ModelOpDescription> dao = Factory.getModelOpDescriptionDAO();
//        String model = request.getParameter("model");
//        //      System.out.print(model);
//
//        ModelOpDescription md = dao.findById(new Long(model));
//        String[] mat = {"01", "02", "05", "07", "09", "11", "12"};
//        String[] err = {"0,1", "0,15", "0,25", "0,5", "1"};
//        String[] limits = {"0.04", "0.063", "0.10", "0.16", "0.25"
//                , "0.40", "0.63", "1.0", "1.6", "2.5", "4.0", "6.3", "10", "16", "25", "40", "63", "100"
//                , "160", "250", "400", "630", "1.0", "1.6", "2.5", "4.0", "6.3", "10", "16", "25", "40", "63", "100"};
//
//        String[] out = {"42", "24", "&#8730 42", "05", "50", "&#8730 05"};
//
//        String res = "{";
//        if (md != null) {
//
//            StringTokenizer tokenizer = new StringTokenizer(md.getIsp(), "|");
//            res += " \"isp\": [";
//            for (; tokenizer.hasMoreTokens();) {
//                String token = tokenizer.nextToken();
//
//                res += "{\"type\": \"" + token + "\", \"text\": \"" + isp[new Integer(token)] + "\"},";
//            }
//            res = res.substring(0, res.length() - 1) + "], \"mat\":[";
//
//
//            tokenizer = new StringTokenizer(md.getIsp(), "|");
//            for (; tokenizer.hasMoreTokens();) {
//                String token = tokenizer.nextToken();
//                res += "{\"type\":\"" + token + "\", \"text\":\"" + mat[new Integer(token)] + "\"},";
//            }
//
//            res = res.substring(0, res.length() - 1) + "], \"err\":[";
//
//            tokenizer = new StringTokenizer(md.getErrors(), "|");
//            for (; tokenizer.hasMoreTokens();) {
//                String token = tokenizer.nextToken();
//                res += "{\"type\": \"" + token + "\", \"text\": \"" + err[new Integer(token)] + "\"},";
//            }
//
//            res = res.substring(0, res.length() - 1) + "],\"out\":[";
//            // res = res.substring(0, res.length() - 1) + "]";
////
////
//            tokenizer = new StringTokenizer(md.getOutputSygnals(), "|");
//            for (; tokenizer.hasMoreTokens();) {
//                String token = tokenizer.nextToken();
//                res += "{\"type\": \"" + token + "\", \"text\": \"" + out[new Integer(token)] + "\"},";
//            }
//            //   res = res.substring(0, res.length() - 1) + "],\"limits\":[";
//            res = res.substring(0, res.length() - 1) + "],\"limits\":[";
////
////
//            Integer lo = md.getLoLimit();
//            Integer hi = md.getHiLimit();
//            for (int i = lo - 1; i < hi; i++)
//                res += "{\"type\":\"" + i + "\", \"text\":\"" + limits[i] + "\"},";
//            res = res.substring(0, res.length() - 1) + "],";
//            res += "\"hilimit\":\"" + md.getHiLimit() + "\",";
//            res += "\"lolimit\":\"" + md.getLoLimit() + "\"";
//
//
//        }
//
//
//        response.setContentType("text/html; charset=UTF-8");
//
//        res += "}";
//
//        // System.out.println(res);
//
//        // response.getWriter().write("<img src=\"images/delete.gif\" width=\"18\" height=\"18\" />");
//        response.getWriter().write(res);
//        //  printProps(request);
//        return null;
//    }


    public ActionForward getModelDescription(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            String model = request.getParameter("model");
            ModelDescription md = Factory.getModelDescriptionDAO().findById(new Long(model));
            response.setContentType("text/html; charset=UTF-8");
            String result = md.toJSONString();
            response.getWriter().write(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public ActionForward redactModelDescription(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request, HttpServletResponse response)
            throws Exception {
//        printProps(request);
        try {

            GenericHibernateDAO<ModelDescription> dao = Factory.getModelDescriptionDAO();
            List<ModelDescription> list = new LinkedList<ModelDescription>();


            String elements = request.getParameter("elements");
            StringTokenizer tokenizer = new StringTokenizer(elements, "|", false);
            for (; tokenizer.hasMoreTokens();) {
                String model = tokenizer.nextToken();
                // System.out.println(type);

                ModelDescription result = dao.findById(new Long(model));
                if (result != null) {
                    list.add(result);
                }

            }

            String param = request.getParameter("param");

            if (param.equals("limits")) {
                String loLim = request.getParameter("loLim");
                String hiLim = request.getParameter("hiLim");
                //   System.out.println(loLim+"--"+hiLim);
                if (loLim != null && loLim != "" && loLim != "undefined" && hiLim != null && hiLim != "" && hiLim != "undefined") {
                    Integer h = new Integer(hiLim);
                    Integer l = new Integer(loLim);
                    for (ModelDescription item : list) {
                        item.setLoLimit(l);
                        item.setHiLimit(h);

                        dao.makePersistent(item);
                    }
                }


            } else if (param.equals("isp")) {
                String val = request.getParameter("val");
                for (ModelDescription item : list) {
                    item.setIsp(val);
                    dao.makePersistent(item);
                }
            } else if (param.equals("mat")) {
                String val = request.getParameter("val");
                for (ModelDescription item : list) {
                    item.setMaterials(val);
                    dao.makePersistent(item);
                }
            } else if (param.equals("err")) {
                String val = request.getParameter("val");
                for (ModelDescription item : list) {
                    item.setErrors(val);
                    dao.makePersistent(item);
                }
            } else if (param.equals("stat")) {
                String val = request.getParameter("val");
                for (ModelDescription item : list) {
                    item.setStaticPressures(val);
                    dao.makePersistent(item);
                }
            } else if (param.equals("Out")) {
                String val = request.getParameter("val");
                for (ModelDescription item : list) {
                    item.setOutputSygnals(val);
                    dao.makePersistent(item);
                }
            } else if (param.equals("DU")) {
                String val = request.getParameter("val");
                for (ModelDescription item : list) {
                    item.setDU(val);
                    dao.makePersistent(item);
                }
            } else if (param.equals("KMCH")) {
                String val = request.getParameter("val");
                for (ModelDescription item : list) {
                    item.setKMCH(val);
                    dao.makePersistent(item);
                }
            } else if (param.equals("VM")) {
                String val = request.getParameter("val");
                Boolean b = new Boolean(val);
                for (ModelDescription item : list) {
                    item.setVM(b);
                    dao.makePersistent(item);
                }
            }


        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

//
//    public ActionForward redactPrice(ActionMapping mapping, ActionForm form,
//                                     HttpServletRequest request, HttpServletResponse response)
//            throws Exception {
//        List<Long> models = new LinkedList<Long>();
//        List<Integer> isp = new LinkedList<Integer>();
//        List<Integer> mat = new LinkedList<Integer>();
//        List<Integer> klim = new LinkedList<Integer>();
//        List<Integer> err = new LinkedList<Integer>();
//        StringTokenizer tokenizer;
//
//        tokenizer = new StringTokenizer(request.getParameter("models"), "|", false);
//        for (; tokenizer.hasMoreTokens();)
//            models.add(new Long(tokenizer.nextToken()));
//
//        tokenizer = new StringTokenizer(request.getParameter("err"), "|", false);
//        for (; tokenizer.hasMoreTokens();)
//            err.add(new Integer(tokenizer.nextToken()));
//
//        tokenizer = new StringTokenizer(request.getParameter("mat"), "|", false);
//        for (; tokenizer.hasMoreTokens();)
//            mat.add(new Integer(tokenizer.nextToken()));
//        tokenizer = new StringTokenizer(request.getParameter("klim"), "|", false);
//        for (; tokenizer.hasMoreTokens();)
//            klim.add(new Integer(tokenizer.nextToken()));
//        tokenizer = new StringTokenizer(request.getParameter("isp"), "|", false);
//        for (; tokenizer.hasMoreTokens();)
//            isp.add(new Integer(tokenizer.nextToken()));
//
//        String cost, price;
//        cost = request.getParameter("cost");
//        price = request.getParameter("price");
//        Long time = System.currentTimeMillis();
//        Factory.getPriceFirstDAO().setPrice((cost == null) ? null : new BigDecimal(cost),
//                (price == null) ? null : new BigDecimal(price), models, err, mat, klim, isp);
//        System.out.println((System.currentTimeMillis() - time) / 1000);
//
//        return null;
//    }
//
//
//        public ActionForward getItemsPrice(ActionMapping mapping, ActionForm form,
//                                     HttpServletRequest request, HttpServletResponse response)
//            throws Exception {
//        List<Long> models = new LinkedList<Long>();
//        List<Integer> isp = new LinkedList<Integer>();
//        List<Integer> mat = new LinkedList<Integer>();
//        List<Integer> klim = new LinkedList<Integer>();
//        List<Integer> err = new LinkedList<Integer>();
//        StringTokenizer tokenizer;
//
//        tokenizer = new StringTokenizer(request.getParameter("models"), "|", false);
//        for (; tokenizer.hasMoreTokens();)
//            models.add(new Long(tokenizer.nextToken()));
//
//        tokenizer = new StringTokenizer(request.getParameter("err"), "|", false);
//        for (; tokenizer.hasMoreTokens();)
//            err.add(new Integer(tokenizer.nextToken()));
//
//        tokenizer = new StringTokenizer(request.getParameter("mat"), "|", false);
//        for (; tokenizer.hasMoreTokens();)
//            mat.add(new Integer(tokenizer.nextToken()));
//        tokenizer = new StringTokenizer(request.getParameter("klim"), "|", false);
//        for (; tokenizer.hasMoreTokens();)
//            klim.add(new Integer(tokenizer.nextToken()));
//        tokenizer = new StringTokenizer(request.getParameter("isp"), "|", false);
//        for (; tokenizer.hasMoreTokens();)
//            isp.add(new Integer(tokenizer.nextToken()));
//
//     //   String cost, price;
//      //  cost = request.getParameter("cost");
//      //  price = request.getParameter("price");
//       // Long time = System.currentTimeMillis();
//
//            String res = "{ \"items\":[";
//
//        List<PriceFirstPart> resut = Factory.getPriceFirstDAO().getItems ( models, err, mat, klim, isp);
//            for (Iterator<PriceFirstPart> priceFirstPartIterator = resut.iterator(); priceFirstPartIterator.hasNext();) {
//                PriceFirstPart priceFirstPart = priceFirstPartIterator.next();
//                System.out.printf("model %d, mat %d, isp %d, err %d, klim %d \n",priceFirstPart.getId(), priceFirstPart.getMat(), priceFirstPart.getIsp(),priceFirstPart.getErr(),priceFirstPart.getKlim());
//               res+="{\"model\":\"" +priceFirstPart.getId()+"\",\"mat\":\""+priceFirstPart.getMat()+"\",\"isp\":\""+priceFirstPart.getIsp()+"\",\"err\":\""+priceFirstPart.getErr()+"\",\"klim\":\""+priceFirstPart.getKlim()+"\",\"cost\":\""+priceFirstPart.getCost()+"\",\"price\":\""+priceFirstPart.getPrice()+"\"  },";
//            }
//
//          res = res.substring(0, res.length() - 1) + "]";
//
//
//
//
//
//        response.setContentType("text/html; charset=UTF-8");
//
//        res += "}";
//            System.out.println("res = " + res);
//      //  System.out.println((System.currentTimeMillis() - time) / 1000);
//        response.getWriter().write(res);
//        return null;
//    }


    public ActionForward checkPressureSensor(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request, HttpServletResponse response)
            throws Exception {
//        printProps(request);
        String model = request.getParameter("model");
        String mat = request.getParameter("mat");
        String isp = request.getParameter("isp");
        Integer klim = new Integer(request.getParameter("klim"));
        String err = request.getParameter("err");
        PriceFirstPart example = new PriceFirstPart(new Long(model), new Integer(isp), new Integer(mat), (klim < 3) ? 0 : (klim < 6) ? 1 : 2, new Integer(err));
        PriceFirstPart result = Factory.getPriceFirstDAO().findUniqueByExample(example);
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write((result.getPrice().compareTo(new BigDecimal("0")) == 1) ? "true" : "false");
        return null;
    }


//    public ActionForward testForFL(ActionMapping mapping, ActionForm form,
//                                   HttpServletRequest request, HttpServletResponse response) {
//        printProps(request);
//        printHeaders(request);
//
//
////        System.out.println("TestAction.testForFL");
////        System.out.println("Session id = " + request.getSession().getId());
//
//
////        Cookie[] c= request.getCookies();
////
////        for(Cookie co:c)
////        {
////            System.out.print("Value = " + co.getValue());
////            System.out.print(" Name  = " + co.getName());
////            System.out.println(" Domain  = " + co.getDomain());
////        }
//
//
////        System.out.println("############################");
////        System.out.println("");
//        try {
//            response.setContentType("text/html; charset=UTF-8");
//            //String result = "{\"total_summa\":10,\"total_items\":20}";
//            String result = "\n<div> hello" +
//                    "</div>\n" +
//                    "</td>\n" +
//                    "<td width=\"240\" style=\"padding-top: 15px;\">\n" +
//                    "<div class=\"sale\">\n" +
//                    "<h3>Корзина заказов</h3>\n" +
//                    "<!--start--><div id=\"bid\"> \n" +
//                    "<table class=\"table-basket-line\" style=\"font: 8pt Tahoma;\">\n" +
//                    "\t\t\t<tr>\n" +
//                    "\t\t\t<td><a href=\"/personal/cart/\" class=\"basket-line-basket\"></a></td>\n" +
//                    "\t\t\t<td><a href=\"/personal/cart/\">В вашей корзине <b>2</b> товара</a></td>\n" +
//                    "\n" +
//                    "\t\t</tr>\n" +
//                    "\t\t\t\t<tr>\n" +
//                    "\t\t\t<td><a href=\"/personal/\" class=\"basket-line-personal\"></a></td>\n" +
//                    "\t\t\t<td><a href=\"/personal/\">Персональный раздел</a></td>\n" +
//                    "\t\t</tr>\n" +
//                    "\t\t</table></div><!--end--> \n" +
//                    "\n" +
//                    "</div>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</table>\n" +
//                    "\n" +
//                    "<h1>Игры-забавы</h1>\n" +
//                    "<p>";
//
//
//            response.getWriter().write(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    private void printProps(HttpServletRequest request) {
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String param = en.nextElement();
            System.out.print("P " + param + " -- ");
            System.out.println(request.getParameter(param));
        }
    }


//    private void printHeaders(HttpServletRequest request) {
//        Enumeration<String> en = request.getHeaderNames();
//        while (en.hasMoreElements()) {
//            String attribute = en.nextElement();
//            System.out.print("H " + attribute + " -- ");
//            System.out.println(request.getHeader(attribute));
//        }
//        System.out.println("ContentType" + request.getContentType());
//        System.out.println("ContentLength" + request.getContentLength());
//
//    }


}
