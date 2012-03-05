package ua.com.manometr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.manometr.model.Customer;
import ua.com.manometr.model.OrgForm;
import ua.com.manometr.model.User;
import ua.com.manometr.service.CustomerService;

import java.util.Map;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


//    @InitBinder
//    public void initBinder(WebDataBinder dataBinder){
//        dataBinder.registerCustomEditor(Profession.class, editor);
//    }

    @RequestMapping("/")
    public String populateCustomers(Map<String, Object> map) {
        //customerService.addCustomer(new Customer());
//        map.put("user", new User());
        map.put("listCustomer", customerService.listCustomer());
        return "customers";
    }

    @RequestMapping("/edit")
    public String setupForm(@RequestParam(value = "id", required = false) Long id, ModelMap model) {

        if (id == null) {
            Customer customer = new Customer();
            OrgForm orgForm = new OrgForm();
            orgForm.setId(1L);
            customer.setOrgForm(orgForm);
            User user = new User();
            user.setId(1L);
            customer.setPerson(user);
            model.put("customer", customer);
        } else {
            model.put("customer", customerService.getCustomer(id));
        }
        return "editCustomer";
    }


//    @RequestMapping("/add")
//    public String editCustomers(@ModelAttribute("user") User user, Map<String, Object> map) {
//        System.out.println(user);
//        userService.addUser(user);
//
//        map.put("userList", userService.listUser());
//        return "redirect:/users/";
//    }

    @RequestMapping("/add")
    public String processSubmit(@ModelAttribute("customer") Customer customer) {
        customer.setOldRecord(null);
        customerService.addCustomer(customer);
        return "redirect:/customers/";
    }
}
