package ua.com.manometer.util;

import ua.com.manometer.model.invoice.Invoice;
import ua.com.manometer.model.invoice.InvoiceItem;
import ua.com.manometer.model.invoice.Payment;
import ua.com.manometer.model.invoice.ShipmentMediator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 22.12.12
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
public class InvoiceUtils {


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    public static BigDecimal computeTotalPayments(Invoice invoice) {
        Set<Payment> payments = invoice.getPayments();
        BigDecimal sumOfPayments = new BigDecimal("0");
        if (payments != null)
            for (Payment payment : payments) {
                sumOfPayments = sumOfPayments.add(payment.getPaymentSum());
            }
        return sumOfPayments;
    }

    public static BigDecimal computePaymentPercent( BigDecimal sumOfPayments,BigDecimal total ) {
        //BigDecimal sumOfPayments = computeTotalPayments(invoice);
       // BigDecimal total = computeTotal(invoice);
        if (total.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        } else {
            return sumOfPayments.multiply(new BigDecimal("100")).divide(total, 2, BigDecimal.ROUND_HALF_UP);
        }


    }

    public static Boolean isPaymentMade(Invoice invoice,BigDecimal total) {
        BigDecimal sumOfPayments = new BigDecimal("0");
        Set<Payment> payments = invoice.getPayments();
        if (payments != null)
            for (Payment payment : payments) {
                sumOfPayments = sumOfPayments.add(payment.getPaymentSum());
            }

        return total.compareTo(sumOfPayments) == 0;
    }

    public static Boolean isDeliveryMade(Invoice invoice) {
        Boolean result = true;
        List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
        if (invoiceItems != null) {
            for (InvoiceItem item : invoiceItems) {
                Integer count = 0;
                if (item.getShippingMediators() != null)
                    for (ShipmentMediator sm : item.getShippingMediators())
                        count += sm.getCount();
                if (!item.getQuantity().equals(count)) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public static Boolean isAnyGoodsShipped(Invoice invoice) {
        Boolean result = false;
        List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
        if (invoiceItems != null) {
            for (InvoiceItem item : invoiceItems) {
                Integer count = 0;
                if (item.getShippingMediators() != null)
                    for (ShipmentMediator sm : item.getShippingMediators())
                        count += sm.getCount();
                if (count != 0) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public static BigDecimal computeNDSPayment(Invoice invoice,BigDecimal sum ) {
        return sum.multiply(invoice.getNDS()).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal computeTotal(Invoice invoice,BigDecimal sum ) {
        return sum.add(computeNDSPayment(invoice,sum));
    }

    public static BigDecimal computeSum(Invoice invoice) {
        BigDecimal result = new BigDecimal("0");

        List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
        if (invoiceItems != null) {
            for (InvoiceItem item : invoiceItems)
                result = result.add(item.getSum());
        }
        return result;
    }

    public static BigDecimal computeAdditionToPrice(Invoice invoice) {
        BigDecimal znamenatel = new BigDecimal("0");
        BigDecimal chislitel = new BigDecimal("0");

        BigDecimal result = new BigDecimal("0");
        BigDecimal tmp1, tmp2;
        List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
        if (invoiceItems != null) {
            for (InvoiceItem item : invoiceItems) {
                tmp1 = item.getPrice().divide(invoice.getExchangeRate(), 2, RoundingMode.HALF_UP).add(item.getAdditionalCost()).multiply(new BigDecimal(item.getQuantity()));
                tmp2 = item.getSellingPrice().subtract(item.getTransportationCost()).multiply(new BigDecimal(item.getQuantity()));
                znamenatel = znamenatel.add(tmp1);
                chislitel = chislitel.add(tmp2);
            }

        }

        if (znamenatel.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ONE;
        } else {
            return chislitel.divide(znamenatel, 2, RoundingMode.HALF_UP);
        }
    }

    public static void setupInvoice(Invoice invoice){
        BigDecimal totalPayments = computeTotalPayments(invoice);
        BigDecimal sum =computeSum(invoice);
        BigDecimal total = computeTotal(invoice, sum);
        BigDecimal paymentPercent = computePaymentPercent(totalPayments, total);
        Boolean paymentMade = isPaymentMade(invoice,total);
        Boolean deliveryMade = isDeliveryMade(invoice);
        Boolean anyGoodsShipped = isAnyGoodsShipped(invoice);
        BigDecimal ndsPayment = computeNDSPayment(invoice,sum);
        BigDecimal additionToPrice = computeAdditionToPrice(invoice);

        invoice.setTotalPayments(totalPayments);
        invoice.setTotal(total);
        invoice.setSum(sum);
        invoice.setPaymentPercent(paymentPercent);
        invoice.setPaymentMade(paymentMade);
        invoice.setDeliveryMade(deliveryMade);
        invoice.setAnyGoodsShipped(anyGoodsShipped);
        invoice.setNdsPayment(ndsPayment);
        invoice.setAdditionToPrice(additionToPrice);
    }



}
