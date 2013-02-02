package ua.com.manometer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import ua.com.manometer.model.User;
import ua.com.manometer.service.UserService;

import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String populateUsers(Map<String, Object> map) {
        map.put("userList", userService.listUser());
        return "users";
    }

    @RequestMapping("/edit")
    public String setupForm(@RequestParam(value = "id", required = false) Integer id, ModelMap model) {
        if (id != null) {
            User user = userService.getUser(id);
            model.put("user", user);
        } else {
            model.put("user", new User());
        }
        return "editUser";
    }


    @RequestMapping("/add")
    public String processSubmit(@ModelAttribute("user") User user,  SessionStatus status) {
        boolean hasError = false;

        if (hasError) {
            return "editUser";
        } else {
            userService.addUser(user);
            status.setComplete();
            return "redirect:/users/";
        }
    }

    @RequestMapping("/delete")
    public String deleteInvoice(@RequestParam("user_id") Integer id) {
        userService.removeUser(id);
        return "redirect:/users/";
    }


}
