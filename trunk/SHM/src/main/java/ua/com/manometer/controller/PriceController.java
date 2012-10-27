package ua.com.manometer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.manometer.model.price.PriceFirstPart;
import ua.com.manometer.service.price.PriceFirstPartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

@Controller
@RequestMapping("/price")
public class PriceController {
    @Autowired
    private PriceFirstPartService priceFirstPartService;


    @RequestMapping("/matrix_price")
    public String matrixPrice() {
        return "editMatrixPrice";
    }

    @RequestMapping("/redact_matrix_price")
    @ResponseBody
    public String redactPrice(HttpServletRequest request) {
        System.out.println("PriceController.redactPrice");
        List<Integer> models = new LinkedList<Integer>();
        List<Integer> isp = new LinkedList<Integer>();
        List<Integer> mat = new LinkedList<Integer>();
        List<Integer> klim = new LinkedList<Integer>();
        List<Integer> err = new LinkedList<Integer>();
        StringTokenizer tokenizer;

        tokenizer = new StringTokenizer(request.getParameter("models"), "|", false);
        for (; tokenizer.hasMoreTokens(); )
            models.add(new Integer(tokenizer.nextToken()));

        tokenizer = new StringTokenizer(request.getParameter("err"), "|", false);
        for (; tokenizer.hasMoreTokens(); )
            err.add(new Integer(tokenizer.nextToken()));

        tokenizer = new StringTokenizer(request.getParameter("mat"), "|", false);
        for (; tokenizer.hasMoreTokens(); )
            mat.add(new Integer(tokenizer.nextToken()));
        tokenizer = new StringTokenizer(request.getParameter("klim"), "|", false);
        for (; tokenizer.hasMoreTokens(); )
            klim.add(new Integer(tokenizer.nextToken()));
        tokenizer = new StringTokenizer(request.getParameter("isp"), "|", false);
        for (; tokenizer.hasMoreTokens(); )
            isp.add(new Integer(tokenizer.nextToken()));

        String cost, price;
        cost = request.getParameter("cost");
        price = request.getParameter("price");
        //  Long time = System.currentTimeMillis();
        priceFirstPartService.setPrice((cost == null) ? null : new BigDecimal(cost), (price == null) ? null : new BigDecimal(price), models, err, mat, klim, isp);
        return "ok";
    }


    @RequestMapping("/getItems")
    @ResponseBody
    public  List<PriceFirstPart> getItemsPrice( HttpServletRequest request)
            throws Exception {
        System.out.println("PriceController.getItemsPrice");
        List<Integer> models = new LinkedList<Integer>();
        List<Integer> isp = new LinkedList<Integer>();
        List<Integer> mat = new LinkedList<Integer>();
        List<Integer> clime = new LinkedList<Integer>();
        List<Integer> err = new LinkedList<Integer>();
        StringTokenizer tokenizer;

        tokenizer = new StringTokenizer(request.getParameter("models"), "|", false);
        for (; tokenizer.hasMoreTokens();)
            models.add(new Integer(tokenizer.nextToken()));

        tokenizer = new StringTokenizer(request.getParameter("err"), "|", false);
        for (; tokenizer.hasMoreTokens();)
            err.add(new Integer(tokenizer.nextToken()));

        tokenizer = new StringTokenizer(request.getParameter("mat"), "|", false);
        for (; tokenizer.hasMoreTokens();)
            mat.add(new Integer(tokenizer.nextToken()));
        tokenizer = new StringTokenizer(request.getParameter("klim"), "|", false);
        for (; tokenizer.hasMoreTokens();)
            clime.add(new Integer(tokenizer.nextToken()));
        tokenizer = new StringTokenizer(request.getParameter("isp"), "|", false);
        for (; tokenizer.hasMoreTokens();)
            isp.add(new Integer(tokenizer.nextToken()));


//        String[] matAlias = ModelDescription.MAT;
//        String[] ispAlias = ModelDescription.ISP;
//        String[] errAlias = ModelDescription.ERR;
//        String[] klimAlias = ModelDescription.KLIM3;


//        String res = "{ \"items\":[";

        List<PriceFirstPart> result = priceFirstPartService.getItems(models, err, mat, clime, isp);
//
//
//        for (Iterator<PriceFirstPart> priceFirstPartIterator = resut.iterator(); priceFirstPartIterator.hasNext();) {
//            PriceFirstPart priceFirstPart = priceFirstPartIterator.next();
//            //    System.out.printf("model %d, mat %d, isp %d, err %d, klim %d \n", priceFirstPart.getId(), priceFirstPart.getMat(), priceFirstPart.getIsp(), priceFirstPart.getErr(), priceFirstPart.getKlim());
//            res += "{\"model\":\"" + priceFirstPart.getId() + "\",\"mat\":\"" + matAlias[priceFirstPart.getMat()] + "\",\"isp\":\"" + ispAlias[priceFirstPart.getIsp()] + "\",\"err\":\"" + errAlias[priceFirstPart.getErr()] + "\",\"klim\":\"" + klimAlias[priceFirstPart.getKlim()] + "\",\"costTmp\":\"" + priceFirstPart.getCostTmp() + "\",\"priceTmp\":\"" + priceFirstPart.getPriceTmp() + "\",\"cost\":\"" + priceFirstPart.getCost() + "\",\"price\":\"" + priceFirstPart.getPrice() + "\" " + " },";
//        }
//
//        res = res.substring(0, res.length() - 1) + "]";
//
//
//        response.setContentType("text/html; charset=UTF-8");
//
//        res += "}";
//        //  System.out.println("res = " + res);
//        //  System.out.println((System.currentTimeMillis() - time) / 1000);
//        response.getWriter().write(res);
        return result;
    }



    @RequestMapping("/applyTmp")
    @ResponseBody
    public String applyTmpValues() {
        System.out.println("PriceController.applyTmpValues");
        priceFirstPartService.applyTmpValues();
        return null;
    }

    @RequestMapping("/resetTmp")
    @ResponseBody
    public String resetTmpValues() {
        System.out.println("PriceController.resetTmpValues");
        priceFirstPartService.resetTmpValues();
        return null;
    }

    @RequestMapping("/priceToTmp")
    @ResponseBody
    public String priceValuesToTmp() {
        System.out.println("PriceController.priceValuesToTmp");
        priceFirstPartService.priceValuesToTmp();
        return null;
    }


}
