package ua.com.manometr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import ua.com.manometr.model.User;
import ua.com.manometr.service.UserService;

import javax.xml.ws.RequestWrapper;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String populateUsers(Map<String, Object> map) {
        map.put("user", new User());
        map.put("userList", userService.listUser());
        return "users";
    }

    @RequestMapping("/edit")
    public String setupForm(@RequestParam("id") Long id, ModelMap model) {
        System.out.println("id = " + id);
        if (id != null) {
            User user = userService.listUser().get(0);
            model.put("user", user);
        } else {
            model.put("user", new User());
        }
        return "editUser";
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
    public String processSubmit(@ModelAttribute("user") User user, BindingResult result, SessionStatus status) {
        boolean hasError = false;
        //  new PetValidator().validate(pet, result);
        if (hasError) {
            return "editUser";
        } else {
            userService.addUser(user);
            status.setComplete();
            return "redirect:/users/";
        }
    }


}
