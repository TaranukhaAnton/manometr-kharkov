package application.utils;


import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

public class CommonUtils {
    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();

    }

    public static void printProps(HttpServletRequest request) {
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String param = en.nextElement();
            // System.out.print(param + " -- ");
            // System.out.println(request.getParameter(param));
            for (String s : request.getParameterValues(param))
                System.out.println(param + " = " + s);
        }
    }

    public static int calculateDayDif(Date firstDate, Date secondDate) {
        long millesecDifference = secondDate.getTime() - firstDate.getTime();
        long secondsDifference = millesecDifference / 1000;
        long minutesDifference = secondsDifference / 60;
        long hoursDifference = minutesDifference / 60;
        long daysDifference = hoursDifference / 24;
        return (int) daysDifference;
    }

}
