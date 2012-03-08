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
import ua.com.manometr.model.Customer;
import ua.com.manometr.model.Profession;
import ua.com.manometr.service.ContactService;
import ua.com.manometr.service.ProfessionService;
import ua.com.manometr.service.UserService;
import ua.com.manometr.webbeans.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/contacts")
public class ContactController {
    @Autowired
    private ContactService contactService;
    @Autowired
    private ProfessionService professionService;

    @RequestMapping("/")
    public String populateContacts(Map<String, Object> map) {
        //contactService.addContact(new Contact());
//        map.put("user", new User());
        map.put("listContact", contactService.listContact());
        return "contacts";
    }

    @RequestMapping("/edit")
    public String setupForm(@RequestParam(value = "id", required = false) Long id, ModelMap model) {

        model.put("professions", professionService.listProfession());

        if (id == null) {
            final Contact contact = new Contact();
            final Profession profession = new Profession();
            profession.setId(0L);
            contact.setProfession(profession);
            model.put("contact", contact);
        } else {
            model.put("contact", contactService.getContact(id));
        }
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
    public String processSubmit(@ModelAttribute("contact") Contact contact) {
        if (contact.getProfession().getId() == null) {
            System.out.println("profession error");
            contact.setProfession(null);
        }

        contact.setOldRecord(null);
        contactService.addContact(contact);
        return "redirect:/contacts/";
    }
}
