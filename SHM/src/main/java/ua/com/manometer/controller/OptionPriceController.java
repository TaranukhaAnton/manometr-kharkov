package ua.com.manometer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.manometer.model.price.OptionsPrice;
import ua.com.manometer.service.price.OptionsPriceService;

import java.util.*;

@Controller
@RequestMapping("/option_price")
public class OptionPriceController {
    @Autowired
    private OptionsPriceService optionsPriceService;


    @RequestMapping("/")
    public String optionPrice(ModelMap model) {
        List<OptionsPrice> optionsPriceByType = optionsPriceService.listOptionsPrice();
        Map<OptionsPrice, OptionsPrice> map = new HashMap<OptionsPrice, OptionsPrice>();
        for (OptionsPrice optionsPrice : optionsPriceByType)
            map.put(optionsPrice, optionsPrice);
        model.put("map", map);
        return "optionPrice";
    }

    @RequestMapping("/editOption")
    @ResponseBody
    public OptionsPrice edit(OptionsPrice optionsPrice) {
       // System.out.println("OptionPriceController.edit");


        optionsPriceService.updateOptionsPrice(optionsPrice);
        return optionsPrice;
    }


}
