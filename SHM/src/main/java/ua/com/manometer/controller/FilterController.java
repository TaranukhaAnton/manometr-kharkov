package ua.com.manometer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.manometer.model.SecuredUser;
import ua.com.manometer.model.invoice.InvoiceFilter;
import ua.com.manometer.service.FilterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/filters")
public class FilterController {

    @Autowired
    FilterService filterService;

    @RequestMapping("/get_filter")
    public
    @ResponseBody
    Object getFilter() {

        SecuredUser securedUser = (SecuredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (securedUser != null) {
            return securedUser.getFilter();
        }

        return null;
    }


    @RequestMapping("/save_filter")
    public String saveFilter(InvoiceFilter invoiceFilter) {
        SecuredUser securedUser = (SecuredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        filterService.saveFilter(invoiceFilter);
        securedUser.setFilter(invoiceFilter);

        return "redirect:/invoices/";
    }

}
