package ua.com.manometr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.com.manometr.model.Customer;
import ua.com.manometr.model.OrgForm;
import ua.com.manometr.model.User;
import ua.com.manometr.model.address.Area;
import ua.com.manometr.model.address.City;
import ua.com.manometr.service.CustomerService;
import ua.com.manometr.service.OrgFormService;
import ua.com.manometr.service.UserService;
import ua.com.manometr.service.address.AreaService;
import ua.com.manometr.service.address.CityService;
import ua.com.manometr.service.address.CountryService;
import ua.com.manometr.service.address.RegionService;

import java.util.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrgFormService orgFormService;
    @Autowired
    private UserService userService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CityService cityService;
    @Autowired
    private AreaService areaService;


//    @InitBinder
//    public void initBinder(WebDataBinder dataBinder){
//        dataBinder.registerCustomEditor(Profession.class, editor);
//    }

    @RequestMapping("/")
    public String populateCustomers(Map<String, Object> map) {
        map.put("listCustomer", customerService.listCustomer());
        return "customers";
    }

    @RequestMapping("/edit")
    public String setupForm(@RequestParam(value = "id", required = false) Long id, ModelMap model) {
        model.put("orgForms", orgFormService.listOrgForm());
        model.put("branches", Customer.branchValues);
        model.put("users", userService.listUser());
        model.put("countries", countryService.listCountry());
        model.put("regions", regionService.listRegion());


        if (id == null) {
            model.put("areas", Collections.emptyList());
            model.put("cities", Collections.emptyList());
            Customer customer = new Customer();
            customer.setOldRecord(new Customer());
            customer.setHeadCustomer(new Customer());

            OrgForm orgForm = new OrgForm();
            orgForm.setId(1L);
            customer.setOrgForm(orgForm);
            User user = new User();
            user.setId(1L);
            customer.setPerson(user);
            customer.setDateLastCorrection(new Date());
            customer.setDateOfRecord(new Date());
            customer.setMergeData(new Date());
            model.put("customer", customer);
        } else {

            final Customer customer = customerService.getCustomer(id);
            model.put("areas", areaService.listAreaForCountry(customer.getCountry()));
            model.put("cities", cityService.listCityForArea(customer.getArea()));
            if (customer.getOldRecord()==null){
                customer.setOldRecord(new Customer());
            }
            if(customer.getHeadCustomer()==null){
                customer.setHeadCustomer(new Customer());
            }
            model.put("customer", customer);
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
        customerService.addCustomer(customer);
        return "redirect:/customers/";
    }


    @RequestMapping("/listCity")
    public
    @ResponseBody
    List<City> listCity(@RequestParam("area") Long areaId) {
        return cityService.listCityForArea(areaId);
    }

    @RequestMapping("/listCustomers")
    public
    @ResponseBody
    List<Customer> listCustomer(@RequestParam("q") String customerTemplate) {
        return customerService.findByShortNameExample(customerTemplate);
    }


    @RequestMapping("/listArea")
    public
    @ResponseBody
    List<Area> listArea(@RequestParam("country") Long countryId) {
        return areaService.listAreaForCountry(countryId);
    }
}
