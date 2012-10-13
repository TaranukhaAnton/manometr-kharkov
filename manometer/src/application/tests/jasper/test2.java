package application.tests.jasper;

import application.data.invoice.Booking;
import application.data.invoice.Invoice;
import application.hibernate.Factory;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 08.06.2010
 * Time: 12:05:22
 * To change this template use File | Settings | File Templates.
 */
public class test2 {
    public static void main(String[] args) {
//        Properties p = System.getProperties();
//        Enumeration keys = p.keys();
//        while (keys.hasMoreElements()) {
//            String key = (String) keys.nextElement();
//            String value = (String) p.get(key);
//            System.out.println(key + ": " + value);
//        }

        String reportFileName = "D:\\Manometr\\web\\disign\\booking.jrxml";
        Invoice invoice = Factory.getInvoiceDAO().findById(new Long(1));
        Booking booking = invoice.getBooking();
        try {


            JasperReport report = JasperCompileManager.compileReport(reportFileName);
            Map parameters = new HashMap();

            parameters.put("invoice", invoice);
            parameters.put("booking", booking);


            //   invoice.getSupplier().getCurrency().getName()
              (new SimpleDateFormat("dd.MM.yyyy")).format(invoice.getDate());

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(invoice.getInvoiceItems()));
            // OutputStream out = response.getOutputStream();
            JasperViewer.viewReport(jasperPrint, true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
