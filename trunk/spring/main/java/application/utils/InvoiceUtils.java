package application.utils;

import ua.com.manometer.model.invoice.Booking;
import ua.com.manometer.model.invoice.Invoice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static application.utils.CommonUtils.calculateDayDif;

public class InvoiceUtils {

    public static List<Invoice> filterInvoicesWithDebts(List<Invoice> input, Date currentDate) {
        List<Invoice> result = new LinkedList<Invoice>();
        System.out.println("input.size() = " + input.size());

        for (Invoice invoice : input) {
            BigDecimal paymentPercent = invoice.getPaymentPercent();
            BigDecimal total = invoice.getTotal();
            BigDecimal totalPayments = invoice.getTotalPayments();

            System.out.println("total = " + total.toString());
            System.out.println("totalPayments = " + totalPayments.toString());
            System.out.println("paymentPercent = " + paymentPercent.toString());
            BigDecimal awaitingPayment;
            BigDecimal awaitingPercent;
            Booking booking = invoice.getBooking();
            if (booking == null) {
                continue;
            }
            Date C1 = booking.getDate();
            Date C2 = booking.getDateOfNoticeOpening();
            Date C3 = booking.getDateOfDeviveryMade();
            System.out.println("currentDate = " + currentDate);
            System.out.println("C1 = " + C1);
            System.out.println("C2 = " + C2);
            System.out.println("C3 = " + C3);

            if (C3 != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(C3);
                Integer daysAfterDelivery = (invoice.getDaysAfterDelivery() == null) ? 0 : invoice.getDaysAfterDelivery();
                calendar.add(Calendar.DAY_OF_YEAR, daysAfterDelivery);
                C3 = calendar.getTime();
                if (currentDate.after(C3)) {
                    System.out.println("(C3 != null)&&(currentDate.after(C3))");
                    awaitingPayment = total;
                    awaitingPercent = new BigDecimal("100");
                    System.out.println("awaitingPayment = " + awaitingPayment.toString());
                    System.out.println("awaitingPercent = " + awaitingPercent.toString());
                    if (paymentPercent.compareTo(awaitingPayment) == -1) {
                        processDebt(result, invoice, paymentPercent, totalPayments, awaitingPayment, awaitingPercent, currentDate, C3);
                        continue;
                    }
                }
            }
            if ((C2 != null) && currentDate.after(C2)) {
                System.out.println("(C2 != null)&&currentDate.after(C2)");
                BigDecimal prepayment = (invoice.getPrepayment() == null) ? BigDecimal.ZERO : invoice.getPrepayment();
                BigDecimal paymentOnTheNitice = (invoice.getPaymentOnTheNitice() == null) ? BigDecimal.ZERO : invoice.getPaymentOnTheNitice();
                awaitingPercent = prepayment.add(paymentOnTheNitice);
                awaitingPayment = awaitingPercent.multiply(total).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                System.out.println("awaitingPayment = " + awaitingPayment.toString());
                System.out.println("awaitingPercent = " + awaitingPercent.toString());
                if (totalPayments.compareTo(awaitingPayment) == -1) {
                    processDebt(result, invoice, paymentPercent, totalPayments, awaitingPayment, awaitingPercent, currentDate, C2);
                    continue;
                }

            }

            if ((C1 != null) && currentDate.after(C1)) {
                System.out.println("(C1 != null)&&currentDate.after(C1)");
                awaitingPercent = invoice.getPrepayment();
                if (awaitingPercent == null) continue;
                awaitingPayment = awaitingPercent.multiply(total).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                System.out.println("awaitingPercent = " + awaitingPercent.toString());
                System.out.println("awaitingPayment = " + awaitingPayment.toString());
                if (totalPayments.compareTo(awaitingPayment) == -1) {
                    processDebt(result, invoice, paymentPercent, totalPayments, awaitingPayment, awaitingPercent, currentDate, C1);
                    continue;
                }

            }


        }

        return result;

    }

    private static void processDebt(List<Invoice> result,
                                    Invoice invoice,
                                    BigDecimal paymentPercent,
                                    BigDecimal totalPayments,
                                    BigDecimal awaitingPayment,
                                    BigDecimal awaitingPercent,
                                    Date cur,
                                    Date c
    ) {
        BigDecimal debt = awaitingPayment.subtract(totalPayments);
        BigDecimal debtPercent = awaitingPercent.subtract(paymentPercent);
        System.out.println("debtPercent = " + debtPercent.toString());
        invoice.setDebtPercent(debtPercent);
        System.out.println("debt = " + debt.toString());
        invoice.setDebt(debt);
        Integer d = calculateDayDif(c, cur);
        System.out.println("day debt = " + d);
        invoice.setDebtDayCount(d);
        result.add(invoice);
    }
}
