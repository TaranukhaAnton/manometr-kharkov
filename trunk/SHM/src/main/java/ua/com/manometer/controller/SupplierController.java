package ua.com.manometer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import ua.com.manometer.model.Supplier;
import ua.com.manometer.model.User;
import ua.com.manometer.service.CurrencyService;
import ua.com.manometer.service.SupplierService;
import ua.com.manometer.service.UserService;

import java.util.Map;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @Autowired
    CurrencyService currencyService;

    @RequestMapping("/")
    public String populateSuppliers(Map<String, Object> map) {
        map.put("suppliers", supplierService.listSupplier());
        return "suppliers";
    }

    @RequestMapping("/edit")
    public String setupForm(@RequestParam(value = "id", required = false) Integer id, ModelMap model) {

        model.put("currencies", currencyService.listCurrency());

        if (id != null) {
            Supplier supplier = supplierService.getSupplier(id);
            model.put("supplier", supplier);
        } else {
            model.put("supplier", new Supplier());
        }
        return "editSupplier";
    }


    @RequestMapping("/add")
    public String processSubmit(Supplier supplier) {
        supplierService.addSupplier(supplier);
        return "redirect:/suppliers/";
    }

    @RequestMapping("/delete")
    public String deleteInvoice(@RequestParam("supplier_id") Integer id) {
        supplierService.removeSupplier(id);
        return "redirect:/suppliers/";
    }
}


