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
