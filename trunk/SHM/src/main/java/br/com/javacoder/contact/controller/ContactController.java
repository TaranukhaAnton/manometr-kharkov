package br.com.javacoder.contact.controller;

import java.io.*;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.javacoder.contact.model.Contact;
import br.com.javacoder.contact.service.ContactService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping("/")
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
//            System.out.println("context.getRealPath(\"/disign/invoice2.jrxml\") =" + context.getRealPath("/disign/invoice2.jrxml"));
//            JasperReport report = JasperCompileManager.compileReport(context.getRealPath("/disign/invoice2.jrxml"));
            JasperReport report = JasperCompileManager.compileReport("D:\\projects\\SHM\\report\\Report.jrxml");
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


}
