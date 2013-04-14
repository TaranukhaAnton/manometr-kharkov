package ua.com.manometer.util;


import org.displaytag.decorator.TableDecorator;
import ua.com.manometer.model.invoice.Invoice;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InvoiceTableDecorator extends TableDecorator {

    public String getSum() {
        Invoice invoice = (Invoice) getCurrentRowObject();
        return  invoice.getTotal().divide(new BigDecimal("1000"), 2, RoundingMode.HALF_UP).toString() ;
    }


    public String getShipment() {
        Invoice invoice = (Invoice) getCurrentRowObject();

        if(invoice.getBooking() == null){
            return "";
        }       else{
            return "<a href=\"../invoices/view_shipments?invoice_id="+invoice.getId()+"\">"+invoice.getShipmentPercent()+"</a>";
        }
    }

    public String getBooking() {
        Invoice invoice = (Invoice) getCurrentRowObject();

        if(invoice.getBooking() == null){
            return "";
        }       else{
            return "<a href=\"../bookings/view?invoice_id="+invoice.getId()+"\">"+invoice.getBooking().getNumber()+"</a>";
        }
    }
    public String getPurpose(){
        Invoice invoice = (Invoice) getCurrentRowObject();
        return   Invoice.purposeAlias[invoice.getPurpose()];
    }


    public String addRowClass()
    {
        Invoice invoice = (Invoice) getCurrentRowObject();
        Integer currency = invoice.getSupplier().getCurrency().getId();
        String color = "";


        if (currency.equals(1))
            color = "";
        if (currency.equals(2))
            color = "red";
        if (currency.equals(3))
            color = "green";
        if (currency.equals(4))
            color = "blue";
        return color;
    }

}
