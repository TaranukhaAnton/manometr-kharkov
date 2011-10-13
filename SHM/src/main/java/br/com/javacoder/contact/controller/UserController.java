package br.com.javacoder.contact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.manometr.model.User;
import ua.com.manometr.service.UserService;

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
        return "userList";
    }
}
