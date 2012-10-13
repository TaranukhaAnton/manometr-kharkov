package application.tests.jasper;

import application.utils.amount.JAmountRU;
import application.data.invoice.Invoice;
import application.hibernate.Factory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;

import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 18.05.2010
 * Time: 22:12:52
 * To change this template use File | Settings | File Templates.
 */
public class test {


    public static void main(String[] args) {


        print();
//        Properties p = System.getProperties();
//        Enumeration keys = p.keys();
//        while (keys.hasMoreElements()) {
//            String key = (String) keys.nextElement();
//            String value = (String) p.get(key);
//            System.out.println(key + ": " + value);
//        }

/*
          String reportFileName ="D:\\Manometr\\web\\disign\\invoice2.jrxml";
Invoice invoice = Factory.getInvoiceDAO().findById(new Long(35));
        try {



            JasperReport report = JasperCompileManager.compileReport(reportFileName);
            Map parameters = new HashMap();
            String title = (invoice.isInvoice() ? "Счет № " : "Коммерческое предложение № ") + invoice.getNumber();
            title += (invoice.getNumberModifier().isEmpty()) ? "" : ("/" + invoice.getNumberModifier());
            parameters.put("Title", title);
            parameters.put("Title2", "бла бла бла");
            parameters.put("date", (new SimpleDateFormat("dd.MM.yyyy")).format(invoice.getDate()));

            parameters.put("orgForm", invoice.getEmploer().getOrgForm().getName());
            parameters.put("emploer", invoice.getEmploer().getName());
            parameters.put("name", invoice.getSupplier().getName());
            parameters.put("address", invoice.getSupplier().getAddress());
            parameters.put("phone", invoice.getSupplier().getPhone());
            parameters.put("erdpou", invoice.getSupplier().getErdpou());

            parameters.put("currency", invoice.getSupplier().getCurrency().getName());
            parameters.put("NDSPayment", invoice.getNDSPayment());
            parameters.put("total", invoice.getTotal());
            parameters.put("sum", invoice.getSum());
            parameters.put("nds", invoice.getNDS());

            parameters.put("strTotal", (new JAmountRU(invoice.getSupplier().getCurrency().getId().intValue()
                    , invoice.getTotal().divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP).toString())).toString());

            parameters.put("paymentTerms", (new JAmountRU(1, invoice.getTotal().toString())).toString());
            parameters.put("deliveryTerms", (new JAmountRU(1, invoice.getTotal().toString())).toString());
            parameters.put("delivery", (new JAmountRU(1, invoice.getTotal().toString())).toString());
            parameters.put("invoice", invoice);
            parameters.put("path", "D:\\Manometr\\web\\images\\reportImages");
            parameters.put("city",Factory.getSINGLETON().getCityDAO().findById(invoice.getEmploer().getCity()).getName());

           // invoice.getSupplier().getBank()





            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(invoice.getInvoiceItems()));
            JRXlsExporter jrXlsExporter = new JRXlsExporter();
                        jrXlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        jrXlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "c:\\out.xls");
                        jrXlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                        jrXlsExporter.exportReport();
*/

            // OutputStream out = response.getOutputStream();
           // JasperViewer.viewReport(jasperPrint, true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    }


      public static void print()
      {


         Long start = System.currentTimeMillis();

        Invoice invoice = Factory.getInvoiceDAO().findById(new Long(35));
       // ServletContext context = this.getServlet().getServletContext();
        try {
            String reportFileName ="D:\\Manometr\\web\\disign\\invoice.jrxml";
            JasperReport report = JasperCompileManager.compileReport(reportFileName);
            //  JasperReport report = JasperCompileManager.compileReport(context.getRealPath("/disign/invoice.jrxml"));
            Map parameters = new HashMap();
            String title = (invoice.isInvoice() ? "Счет-фактура № " : "Коммерческое предложение № ") + invoice.getNumber();
            title += (invoice.getNumberModifier().isEmpty()) ? "" : ("/" + invoice.getNumberModifier());
            // FontFactory.register("D:\\ARIAL.TTF");
            // FontFactory.register();

            parameters.put("Title", title);
            parameters.put("date", (new SimpleDateFormat("dd.MM.yyyy")).format(invoice.getDate()));
            parameters.put("orgForm", invoice.getEmploer().getOrgForm().getName());
            parameters.put("emploer", invoice.getEmploer().getName());
            parameters.put("name", invoice.getSupplier().getName());
            parameters.put("address", invoice.getSupplier().getAddress());
            parameters.put("phone", invoice.getSupplier().getPhone());
            parameters.put("erdpou", invoice.getSupplier().getErdpou());

            parameters.put("currency", invoice.getSupplier().getCurrency().getName());
            parameters.put("NDSPayment", invoice.getNDSPayment());
            parameters.put("total", invoice.getTotal());
            parameters.put("sum", invoice.getSum());
            parameters.put("nds", invoice.getNDS());

//            parameters.put("strTotal", (new JAmountRU(invoice.getSupplier().getCurrency().getId().intValue()
//                    , invoice.getTotal().divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP).toString())).toString());
//
//            parameters.put("paymentTerms", (new JAmountRU(1, invoice.getTotal().toString())).toString());
//            parameters.put("deliveryTerms", (new JAmountRU(1, invoice.getTotal().toString())).toString());
//            parameters.put("delivery", (new JAmountRU(1, invoice.getTotal().toString())).toString());
            parameters.put("invoice", invoice);
            parameters.put("path", "D:\\Manometr\\web\\images\\reportImages\\");
            parameters.put("city", Factory.getCityDAO().findById(invoice.getEmploer().getCity()).getName());
            parameters.put("type", ".jpg");

            System.out.println(System.currentTimeMillis()-start);

            // invoice.getPrepayment().compareTo(BigDecimal.ZERO)==0
//
            //   invoice.getDaysAfterDelivery()

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(invoice.getInvoiceItems()));

            JRXlsExporter jrXlsExporter = new JRXlsExporter();
            jrXlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            jrXlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "c:\\out.xls");
            jrXlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            jrXlsExporter.exportReport();
             System.out.println(System.currentTimeMillis()-start);

            JROdtExporter exporter = new JROdtExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "c:\\out.odt");
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporter.exportReport();
             System.out.println(System.currentTimeMillis()-start);
        } catch (Exception e) {
            e.printStackTrace();
        }


      }


}
