package ua.com.manometr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String listContacts(Map<String, Object> map) {
        map.put("user", new User());
        map.put("userList", userService.listUser());
        return "users";
    }

    @RequestMapping("/edit")
    public String editContactsMaping(Map<String, Object> map) {
        User user = userService.listUser().get(0);

        map.put("user", user);
        return "editUser";
    }

    @RequestMapping("/add")
    public String editContacts(@ModelAttribute("user") User user, Map<String, Object> map) {
        System.out.println(user);
        userService.addUser(user);

        map.put("userList", userService.listUser());
        return "redirect:/users/";
    }


}
