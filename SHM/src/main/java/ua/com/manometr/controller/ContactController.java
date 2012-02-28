package ua.com.manometr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import ua.com.manometr.model.Contact;
import ua.com.manometr.service.ContactService;
import ua.com.manometr.service.UserService;
import ua.com.manometr.webbeans.User;

import java.util.Map;

@Controller
@RequestMapping("/contacts")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @RequestMapping("/")
    public String populateContacts(Map<String, Object> map) {
        //contactService.addContact(new Contact());
//        map.put("user", new User());
        map.put("listContact", contactService.listContact());
        return "contacts";
    }

    @RequestMapping("/edit")
    public String setupForm(@RequestParam(value = "id", required = false) Long id, ModelMap model) {




        if (id != null) {
            Contact contact = contactService.getContact(id);

            model.put("contact", contact);
        }
        //else {
        //    model.put("contact", new Contact());
       // }
        return "editContact";
    }


//    @RequestMapping("/add")
//    public String editContacts(@ModelAttribute("user") User user, Map<String, Object> map) {
//        System.out.println(user);
//        userService.addUser(user);
//
//        map.put("userList", userService.listUser());
//        return "redirect:/users/";
//    }

    @RequestMapping("/add")
    public String processSubmit(ModelMap model) {


            return "redirect:/contacts/";
    }
}
