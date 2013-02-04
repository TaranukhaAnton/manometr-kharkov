package ua.com.manometer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.manometer.model.price.ProductionPrice;
import ua.com.manometer.service.price.ProductionPriceService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
@RequestMapping("/list_price")
public class ListPriceController {
    @Autowired
    private ProductionPriceService productionPriceService;


    @RequestMapping("/")
    public String listPrice() {
        return "listPrice";
    }

    @RequestMapping("/page")
    public String onePage(Integer type, ModelMap model) {

        model.put("type", type);
        model.put("list", productionPriceService.listProductionPriceByType(type));
        return "/price/oneListPage";
    }

     //todo
    @RequestMapping("/add")
    public String add(ProductionPrice productionPrice, ModelMap model) {
        System.out.println("ListPriceController.add");
        productionPriceService.addProductionPrice(productionPrice);
        return  "redirect:/list_price/page?type="+ productionPrice.getType();
    }


    @RequestMapping("/delete")
    public String delete(Integer id, Integer type, ModelMap model)
            throws Exception {
        System.out.println("PriceController.delete");
        productionPriceService.removeProductionPrice(id);
        model.put("type", type);
        model.put("list", productionPriceService.listProductionPriceByType(type));
        return "/price/oneListPage";
    }


}
