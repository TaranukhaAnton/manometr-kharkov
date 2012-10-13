package application.utils;

import application.data.invoice.Booking;
import application.data.invoice.Invoice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static application.utils.CommonUtils.getCurrentDate;

public class InvoiceUtils {

    public static List<Invoice> filterInvoicesWithDebts(List<Invoice> input, Date currentDate) {
        List<Invoice> result = new LinkedList<Invoice>();

        for (Invoice invoice : input) {
            BigDecimal paymentPercent = invoice.getPaymentPercent();
            BigDecimal total = invoice.getTotal();
            System.out.println("total = " + total.toString());
            BigDecimal totalPayments = invoice.getTotalPayments();
            System.out.println("totalPayments = " + totalPayments.toString());

            BigDecimal awaitingPayment;

            Booking booking = invoice.getBooking();
            if (booking == null) {
                continue;
            }
            Date C1 = booking.getDate();
            Date C2 = booking.getDateOfNoticeOpening();
            Date C3 = booking.getDateOfDeviveryMade();
            if ((C3 != null)&&(currentDate.after(C3))) {
                System.out.println("(C3 != null)&&(currentDate.after(C3))");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(C3);
                Integer daysAfterDelivery = (invoice.getDaysAfterDelivery() == null) ? 0 : invoice.getDaysAfterDelivery();
                calendar.add(Calendar.DAY_OF_YEAR, daysAfterDelivery);
                C3 = calendar.getTime();
                awaitingPayment = total;
                System.out.println(" awaitingPayment = " + awaitingPayment.toString());

                if  (paymentPercent.compareTo(awaitingPayment) == -1) {
                    BigDecimal debt = total.subtract(totalPayments);
                    invoice.setDebt(debt);
                    result.add(invoice);
                    continue;
                }


            }
            if ((C2 != null)&&currentDate.after(C2)) {
                System.out.println("(C2 != null)&&currentDate.after(C2)");
                BigDecimal prepayment = invoice.getPrepayment();
                BigDecimal paymentOnTheNitice = invoice.getPaymentOnTheNitice();
                //BigDecimal total = invoice.getPaymentOnTheNitice();

                BigDecimal awaitingPercent = prepayment.add(paymentOnTheNitice);
                awaitingPayment = awaitingPercent.multiply(total).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                System.out.println(" awaitingPayment = " + awaitingPayment.toString());
                if (totalPayments.compareTo(awaitingPayment) == -1) {
                    BigDecimal debt = awaitingPayment.subtract(totalPayments);
                    invoice.setDebt(debt);
                    result.add(invoice);
                    continue;
                }

            }

            if ((C1 != null)&&currentDate.after(C1)) {
                System.out.println("(C1 != null)&&currentDate.after(C1)");
                BigDecimal awaitingPercent = invoice.getPrepayment();
                System.out.println("awaitingPercent = " + awaitingPercent.toString());

                awaitingPayment = awaitingPercent.multiply(total).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                System.out.println(" awaitingPayment = " + awaitingPayment.toString());
                if (totalPayments.compareTo(awaitingPayment) == -1) {
                    BigDecimal debt = awaitingPayment.subtract(totalPayments);
                    invoice.setDebt(debt);
                    result.add(invoice);
                    continue;
                }

            }




        }

        return result;

    }
}
