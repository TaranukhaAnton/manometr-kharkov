package application.actions;

import application.data.ModelDescription.ModelDescription;
import application.data.price.OptionsPrice;
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
import java.net.URLEncoder;
import java.text.StringCharacterIterator;
import java.util.*;


public class PriceAction extends DispatchAction {
    public ActionForward redactMatrixPriceForward(ActionMapping mapping, ActionForm form,
                                                  HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // }
        return mapping.findForward("redactMatrixPrice");
    }

    public ActionForward getInputWindow(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // }
        return mapping.findForward("inputWindow");
    }

    public ActionForward redactOptionPriceForward(ActionMapping mapping, ActionForm form,
                                                  HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // }
        return mapping.findForward("redactOptionPrice");
    }

    public ActionForward redactOption(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {


     //   printProps(request);
        GenericHibernateDAO<OptionsPrice> dao = Factory.getOptionsPriceDAO();

        try {
            //  Long id = new Long(request.getParameter("id"));
            Map<String, Object> param = new HashMap();
            param.put("type", new Integer(request.getParameter("type")));
            param.put("isp", new Integer(request.getParameter("isp")));
            param.put("param", request.getParameter("param"));


            List<OptionsPrice> res = dao.findByExample(param);
            String result = "";
            if (!res.isEmpty()) {
                OptionsPrice p = res.get(0); //new OptionsPrice();
                p.setCost(new BigDecimal(request.getParameter("cost")));
                p.setPrice(new BigDecimal(request.getParameter("price")));
//            p.setType(new Integer(request.getParameter("type")));
//            p.setIsp(new Integer(request.getParameter("isp")));
//            p.setParam(request.getParameter("param"));
                dao.makePersistent(p);

                result = "{\"isp\":\"" + p.getIsp() + "\",";
                result += "\"type\":\"" + p.getType() + "\",";
                result += "\"param\":\"" + p.getParam() + "\",";
                result += "\"cost\":\"" + p.getCost() + "\",";
                result += "\"price\":\"" + p.getPrice() + "\"}";
            }


//            System.out.println("PriceAction.addListItem");
            response.setContentType("text/html; charset=UTF-8");


            response.getWriter().write(result);
            // printProps(request);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // }
        return null;
    }


    public ActionForward redactListPriceForward(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //  System.out.println("PriceAction.redactListPriceForward");
        //  GenericHibernateDAO<ProductionPrice> dao = Factory.getProductionDAO();

//        request.setAttribute("list0", dao.findListByParameter("type", 0));
//        request.setAttribute("list1", dao.findListByParameter("type", 1));
        // }
        return mapping.findForward("redactListPrice");
    }


    public ActionForward deleteListItem(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        GenericHibernateDAO<ProductionPrice> dao = Factory.getProductionDAO();
        try {
            String s = request.getParameter("id");
            if (s != null) {
                Long id = new Long(s);
                ProductionPrice p = dao.findById(id);
                if (p != null) dao.delete(p);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

//        method=
        //    request.addParameter("method","redactListPriceForward");
        //    request.
        return mapping.findForward("redactListPrice");
    }


    public ActionForward getListItem(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        GenericHibernateDAO<ProductionPrice> dao = Factory.getProductionDAO();
        try {
            Long id = new Long(request.getParameter("id"));
            ProductionPrice item = dao.findById(id);
            String res = "{\"id\":\"" + item.getId() + "\",";
            res += "\"type\":\"" + item.getType() + "\",";
            res += "\"name\":\"" +forJSON(item.getName()) + "\",";
            res += "\"cost\":\"" + item.getCost() + "\",";
            res += "\"price\":\"" + item.getPrice() + "\"}";

//            System.out.println("PriceAction.addListItem");
            response.setContentType("text/html; charset=UTF-8");


            response.getWriter().write(res);
            // printProps(request);
        } catch (Exception e) {
        }


        return null;
    }

    public ActionForward addListItem(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        GenericHibernateDAO<ProductionPrice> dao = Factory.getProductionDAO();
        //  System.out.println("PriceAction.addListItem");
        response.setContentType("text/html; charset=UTF-8");
        ProductionPrice p;
        if ((request.getParameter("id") != null) && (!request.getParameter("id").isEmpty())) {
            p = dao.findById(new Long(request.getParameter("id")));
        } else {
            p = new ProductionPrice();
        }

        //  System.out.println("res = " + res);
        //  System.out.println((System.currentTimeMillis() - time) / 1000);
        //response.getWriter().write("response");
        // printProps(request);

        p.setCost(new BigDecimal(request.getParameter("cost")));
        p.setPrice(new BigDecimal(request.getParameter("price")));
        p.setType(new Integer(request.getParameter("type")));
        p.setName(request.getParameter("name"));

        dao.makePersistent(p);
//        request.setAttribute("list0", dao.findListByParameter("type", 0));
//        request.setAttribute("list1", dao.findListByParameter("type", 1));
        // }
        return mapping.findForward("redactListPrice");
    }


    public ActionForward redactPrice(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<Long> models = new LinkedList<Long>();
        List<Integer> isp = new LinkedList<Integer>();
        List<Integer> mat = new LinkedList<Integer>();
        List<Integer> klim = new LinkedList<Integer>();
        List<Integer> err = new LinkedList<Integer>();
        StringTokenizer tokenizer;

        tokenizer = new StringTokenizer(request.getParameter("models"), "|", false);
        for (; tokenizer.hasMoreTokens();)
            models.add(new Long(tokenizer.nextToken()));

        tokenizer = new StringTokenizer(request.getParameter("err"), "|", false);
        for (; tokenizer.hasMoreTokens();)
            err.add(new Integer(tokenizer.nextToken()));

        tokenizer = new StringTokenizer(request.getParameter("mat"), "|", false);
        for (; tokenizer.hasMoreTokens();)
            mat.add(new Integer(tokenizer.nextToken()));
        tokenizer = new StringTokenizer(request.getParameter("klim"), "|", false);
        for (; tokenizer.hasMoreTokens();)
            klim.add(new Integer(tokenizer.nextToken()));
        tokenizer = new StringTokenizer(request.getParameter("isp"), "|", false);
        for (; tokenizer.hasMoreTokens();)
            isp.add(new Integer(tokenizer.nextToken()));

        String cost, price;
        cost = request.getParameter("cost");
        price = request.getParameter("price");
        //  Long time = System.currentTimeMillis();
        Factory.getPriceFirstDAO().setPrice((cost == null) ? null : new BigDecimal(cost),
                (price == null) ? null : new BigDecimal(price), models, err, mat, klim, isp);
        // System.out.println((System.currentTimeMillis() - time) / 1000);

        return null;
    }


    public ActionForward getItemsPrice(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<Long> models = new LinkedList<Long>();
        List<Integer> isp = new LinkedList<Integer>();
        List<Integer> mat = new LinkedList<Integer>();
        List<Integer> klim = new LinkedList<Integer>();
        List<Integer> err = new LinkedList<Integer>();
        StringTokenizer tokenizer;

        tokenizer = new StringTokenizer(request.getParameter("models"), "|", false);
        for (; tokenizer.hasMoreTokens();)
            models.add(new Long(tokenizer.nextToken()));

        tokenizer = new StringTokenizer(request.getParameter("err"), "|", false);
        for (; tokenizer.hasMoreTokens();)
            err.add(new Integer(tokenizer.nextToken()));

        tokenizer = new StringTokenizer(request.getParameter("mat"), "|", false);
        for (; tokenizer.hasMoreTokens();)
            mat.add(new Integer(tokenizer.nextToken()));
        tokenizer = new StringTokenizer(request.getParameter("klim"), "|", false);
        for (; tokenizer.hasMoreTokens();)
            klim.add(new Integer(tokenizer.nextToken()));
        tokenizer = new StringTokenizer(request.getParameter("isp"), "|", false);
        for (; tokenizer.hasMoreTokens();)
            isp.add(new Integer(tokenizer.nextToken()));


        String[] matAlias = ModelDescription.MAT;
        String[] ispAlias = ModelDescription.ISP;
        String[] errAlias = ModelDescription.ERR;
        String[] klimAlias = ModelDescription.KLIM3;


        String res = "{ \"items\":[";

        List<PriceFirstPart> resut = Factory.getPriceFirstDAO().getItems(models, err, mat, klim, isp);
        for (Iterator<PriceFirstPart> priceFirstPartIterator = resut.iterator(); priceFirstPartIterator.hasNext();) {
            PriceFirstPart priceFirstPart = priceFirstPartIterator.next();
            //    System.out.printf("model %d, mat %d, isp %d, err %d, klim %d \n", priceFirstPart.getId(), priceFirstPart.getMat(), priceFirstPart.getIsp(), priceFirstPart.getErr(), priceFirstPart.getKlim());
            res += "{\"model\":\"" + priceFirstPart.getId() + "\",\"mat\":\"" + matAlias[priceFirstPart.getMat()] + "\",\"isp\":\"" + ispAlias[priceFirstPart.getIsp()] + "\",\"err\":\"" + errAlias[priceFirstPart.getErr()] + "\",\"klim\":\"" + klimAlias[priceFirstPart.getKlim()] + "\",\"costTmp\":\"" + priceFirstPart.getCostTmp() + "\",\"priceTmp\":\"" + priceFirstPart.getPriceTmp() + "\",\"cost\":\"" + priceFirstPart.getCost() + "\",\"price\":\"" + priceFirstPart.getPrice() + "\" " + " },";
        }

        res = res.substring(0, res.length() - 1) + "]";


        response.setContentType("text/html; charset=UTF-8");

        res += "}";
        //  System.out.println("res = " + res);
        //  System.out.println((System.currentTimeMillis() - time) / 1000);
        response.getWriter().write(res);
        return null;
    }

    public ActionForward applayTmpValues(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response) {
        //  System.out.println("PriceAction.applayTmpValues");
        Factory.getPriceFirstDAO().applayTmpValues();

        return null;
    }

    public ActionForward resetTmpValues(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("PriceAction.resetTmpValues");
        Factory.getPriceFirstDAO().resetTmpValues();

        return null;
    }

    private void printProps(HttpServletRequest request) {
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String param = en.nextElement();
            System.out.print(param + " -- ");
            System.out.println(request.getParameter(param));
        }
    }


    public static String forJSON(String aText) {
        final StringBuilder result = new StringBuilder();
        StringCharacterIterator iterator = new StringCharacterIterator(aText);
        char character = iterator.current();
        while (character != StringCharacterIterator.DONE) {
            if (character == '\"') {
                result.append("\\\"");
            } else if (character == '\\') {
                result.append("\\\\");
            } else if (character == '/') {
                result.append("\\/");
            } else if (character == '\b') {
                result.append("\\b");
            } else if (character == '\f') {
                result.append("\\f");
            } else if (character == '\n') {
                result.append("\\n");
            } else if (character == '\r') {
                result.append("\\r");
            } else if (character == '\t') {
                result.append("\\t");
            } else {
                //the char is not a special one
                //add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }
}
