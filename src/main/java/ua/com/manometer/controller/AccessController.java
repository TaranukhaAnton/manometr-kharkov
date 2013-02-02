package ua.com.manometer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping
public class AccessController {

    @RequestMapping("/login")
    public String login(Model model, @RequestParam(required=false) Boolean hasError) {
        model.addAttribute("hasError", hasError);
        return "access/login";
    }

    @RequestMapping(value = "/denied")
    public String denied() {
        return "access/denied";
    }

    @RequestMapping(value = "/login/failure")
    public String loginFailure(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return "redirect:/login?hasError=true";

    }

    @RequestMapping(value = "/logout/success")
    public String logoutSuccess() {
        String message = "";
        return "redirect:/login?message="+message;
    }
}