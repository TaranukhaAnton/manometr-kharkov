package br.com.javacoder.contact.controller;

import br.com.javacoder.contact.model.Contact;
import br.com.javacoder.contact.service.ContactService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping("/get")
    public String listContacts(Map<String, Object> map) {
        map.put("contact", new Contact());
        map.put("contactList", contactService.listContact());
        return "contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addContact(@ModelAttribute("contact") Contact contact,
                             BindingResult result) {
        contactService.addContact(contact);
        return "redirect:/";
    }

    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") Long contactId) {
        contactService.removeContact(contactId);
        return "redirect:/";
    }

//    @RequestMapping("/pdf")
//    public byte[] getPDF() {
//
//
//        File f = new File("D:\\projects\\SHM\\pdf\\buklet_SW_2011.pdf");
//        byte[] data = new byte[(int) f.length()];
//        InputStream inStream = null;
//        try {
//            inStream = new FileInputStream(f);
//            inStream.read(data);
//            inStream.close();
//            return data;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//         return  null;
//
//    }

    @RequestMapping("/pdf")
    public void getPDF(OutputStream out) {

        //  String contextPath = request.getContextPath();
        // System.out.println("contextPath = " + contextPath);
        // Invoice invoice = Factory.getInvoiceDAO().findById(new Long(request.getParameter("invoiceId")));
        //  ServletContext context = request.getSession().getServletContext();
        // System.out.println(context.getRealPath("/report/Report.jrxml"));
        // String type = request.getParameter("type");
        try {

            InputStream reportStream = this.getClass().getResourceAsStream("/Report.jrxml");

// Retrieve our report template
            JasperDesign jd = JRXmlLoader.load(reportStream);
            JasperReport report = JasperCompileManager.compileReport(jd);
//            System.out.println("context.getRealPath(\"/disign/invoice2.jrxml\") =" + context.getRealPath("/disign/invoice2.jrxml"));
//            JasperReport report = JasperCompileManager.compileReport(context.getRealPath("/disign/invoice2.jrxml"));
            // JasperReport report = JasperCompileManager.compileReport("D:\\projects\\SHM\\report\\Report.jrxml");
            Map parameters = new HashMap();

            parameters.put("sss", "str");


            // OutputStream out = response.getOutputStream();
            // response.setContentType("application/octet-stream");

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, (JRBeanCollectionDataSource) null);
            //  response.setHeader("Content-Disposition", "attachment;filename=invoice.pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);

            JasperExportManager.exportReportToPdfFile(jasperPrint, "C://pdf.pdf");

            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername();

        model.addAttribute("username", name);
        model.addAttribute("message", "Spring Security login + database example");
        return "hello";

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {

        return "login";

    }
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String friends(ModelMap model) {
        return "index";

    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {


        return "info/about";

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {

        return "login";
    }


}
