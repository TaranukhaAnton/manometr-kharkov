package ua.com.manometer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@Controller
@RequestMapping("/price")
public class PriceController {

    @RequestMapping("/matrix_price")
    public String matrixPrice() {
        return "editMatrixPrice";
    }

    @RequestMapping("/list_price")
    public String listPrice() {
        return "editMatrixPrice";
    }

    @RequestMapping("/option_price")
    public String optionPrice() {
        return "editMatrixPrice";
    }


    @RequestMapping("/redact_matrix_price")
    @ResponseBody
    public String redactPrice(HttpServletRequest request) {
        List<Long> models = new LinkedList<Long>();
        List<Integer> isp = new LinkedList<Integer>();
        List<Integer> mat = new LinkedList<Integer>();
        List<Integer> klim = new LinkedList<Integer>();
        List<Integer> err = new LinkedList<Integer>();
        StringTokenizer tokenizer;

        tokenizer = new StringTokenizer(request.getParameter("models"), "|", false);
        for (; tokenizer.hasMoreTokens(); )
            models.add(new Long(tokenizer.nextToken()));

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
        //  Factory.getPriceFirstDAO().setPrice((cost == null) ? null : new BigDecimal(cost), (price == null) ? null : new BigDecimal(price), models, err, mat, klim, isp);
        return "ok";
    }


}
