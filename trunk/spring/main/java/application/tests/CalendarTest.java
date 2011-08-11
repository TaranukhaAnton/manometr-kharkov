package application.tests;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 08.06.2010
 * Time: 13:33:38
 * To change this template use File | Settings | File Templates.
 */
public class CalendarTest {

    public static void main(String[] args) {
        try {
            Date date1 = (new SimpleDateFormat("dd.MM.yyyy")).parse("27.2.2010");
            Date date2 = (new SimpleDateFormat("dd.MM.yyyy")).parse("26.1.2010");

              //(new SimpleDateFormat("dd.MM.yyyy")).format()
            System.out.println("date1 = " + date1);
//            long time1 = date1.getTime();// getTimeInMillis();
//            long time2 = date2.getTime();
//            long daysBetween = (time2 - time1 + 60 * 60 * 1000) / (24 * 60 * 60 * 1000);
//            System.out.println("daysBetween = " + daysBetween);
            Date date3 = dateBeforeNDays(date1, 4);
            System.out.println(date3);
            System.out.println(daysBetween(date1, date3));


        } catch (Exception e) {
        }
    }


    private static int daysBetween(Date date1, Date date2) {
        long time1 = date1.getTime();// getTimeInMillis();
        long time2 = date2.getTime();
        Long daysBetween = (time2 - time1 + 60 * 60 * 1000) / (24 * 60 * 60 * 1000);
        return daysBetween.intValue();
    }

    private static Date dateBeforeNDays(Date date, int n) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        c.setTime(date);
        c.add(Calendar.DATE, n);
        return c.getTime();
    }


    public static int yearsDifference(Date a, Date b) {
        Calendar calendarA = Calendar.getInstance();
        Calendar calendarB = Calendar.getInstance();
        int multiplier;
        if (b.getTime() - a.getTime() > 0) {
            multiplier = -1;
            calendarA.setTime(b);
            calendarB.setTime(a);
        } else {
            multiplier = 1;
            calendarA.setTime(a);
            calendarB.setTime(b);
        }
//        calendarA.setTime(a);// <----- THIS IS NEED TO REMOVE
//        calendarB.setTime(b);// <----- THIS IS NEED TO REMOVE

        int years = calendarA.get(Calendar.YEAR) - calendarB.get(Calendar.YEAR);
        int months = calendarA.get(Calendar.MONTH) - calendarB.get(Calendar.MONTH);
        int days = calendarA.get(Calendar.DAY_OF_MONTH) - calendarB.get(Calendar.DAY_OF_MONTH);
        if (years > 0 && (months < 0 || (months == 0 && days < 0))) {
            years -= 1;
        }
        return years * multiplier;
    }


}
