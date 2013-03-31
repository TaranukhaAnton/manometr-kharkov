package ua.com.manometer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.manometer.model.price.IdPrice;
import ua.com.manometer.model.price.PriceFirstPart;
import ua.com.manometer.service.price.PriceFirstPartService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/matrix_price")
public class MatrixPriceController {
    @Autowired
    private PriceFirstPartService priceFirstPartService;


    @RequestMapping("/")
    public String matrixPrice() {
        return "matrixPrice";
    }

    @RequestMapping("/redact")
    @ResponseBody
    public String redactPrice(HttpServletRequest request) {
       // System.out.println("PriceController.redactPrice");
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
        priceFirstPartService.setPrice((cost == null) ? null : new BigDecimal(cost), (price == null) ? null : new BigDecimal(price), models, err, mat, klim, isp);
        return "ok";
    }


    @RequestMapping("/getItems")
    @ResponseBody
    public List<PriceFirstPart> getItemsPrice(HttpServletRequest request)
            throws Exception {
       // System.out.println("PriceController.getItemsPrice");
        List<Integer> models = new LinkedList<Integer>();
        List<Integer> isp = new LinkedList<Integer>();
        List<Integer> mat = new LinkedList<Integer>();
        List<Integer> clime = new LinkedList<Integer>();
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
            clime.add(new Integer(tokenizer.nextToken()));
        tokenizer = new StringTokenizer(request.getParameter("isp"), "|", false);
        for (; tokenizer.hasMoreTokens(); )
            isp.add(new Integer(tokenizer.nextToken()));
        List<PriceFirstPart> result = priceFirstPartService.getItems(models, err, mat, clime, isp);
        return result;
    }


    @RequestMapping("/applyTmp")
    @ResponseBody
    public String applyTmpValues() {
       // System.out.println("PriceController.applyTmpValues");
        priceFirstPartService.applyTmpValues();
        return null;
    }

    @RequestMapping("/resetTmp")
    @ResponseBody
    public String resetTmpValues() {
       // System.out.println("PriceController.resetTmpValues");
        priceFirstPartService.resetTmpValues();
        return null;
    }

    @RequestMapping("/priceToTmp")
    @ResponseBody
    public String priceValuesToTmp() {
       // System.out.println("PriceController.priceValuesToTmp");
        priceFirstPartService.priceValuesToTmp();
        return null;
    }

    @RequestMapping("/isAvailable")
    @ResponseBody
    public Map isAvailable(IdPrice item) {
        Map map = new HashMap();
        map.put("available", priceFirstPartService.modelAvailableToSell(item));
        return map;
    }


}
