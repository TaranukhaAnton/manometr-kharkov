package com.sp.util;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class TimePeriod {

    public TimePeriod(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TimePeriod() {
    }

    public static enum TimePeriodName {
        TODAY,
        YESTERDAY,
        CURRENT_WEEK,
        LAST_WEEK,
        PREVIOUS_WEEK,
        CURRENT_MONTH,
        LAST_MONTH,
        PREVIOUS_MONTH
    }
    private Date startDate;
    private Date endDate;
    private static TimePeriod EMPTY_PERIOD = new TimePeriod();

    private final static Logger LOGGER = Logger.getLogger(TimePeriod.class);

    public static TimePeriod getByWeekNumber(int weekNum) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.set(Calendar.WEEK_OF_YEAR, weekNum);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        TimePeriod result = new TimePeriod();
        result.setStartDate(calendar.getTime());

        calendar.set(Calendar.WEEK_OF_YEAR, weekNum + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        result.setEndDate(calendar.getTime());

        return result;
    }


    public boolean isEmpty() {
        return (endDate == null && startDate == null) ;
    }
    
    public static TimePeriod getByName(TimePeriodName name) {
        try {
            switch (name) {
                case TODAY: return getTodayPeriod();
                case YESTERDAY: return getYesterdayPeriod();
                case CURRENT_WEEK: return getCurrentWeekPeriod();
                case LAST_WEEK: return getLastWeekPeriod();
                case PREVIOUS_WEEK: return getPrevWeekPeriod();
                case CURRENT_MONTH: return getCurrentMonthPeriod();
                case LAST_MONTH: return getPrevMonthPeriod();//August 2009, Ross: Last Month is all days in July
                case PREVIOUS_MONTH: return getPrevMonthPeriod();
                default: return EMPTY_PERIOD;
            }
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return EMPTY_PERIOD ;
    }

    public static TimePeriod getLast2HoursPeriod() throws ParseException {
        TimePeriod res = new TimePeriod();
        Calendar calendar = Calendar.getInstance();
        res.endDate = calendar.getTime();
        calendar.add(Calendar.HOUR, -2);
        res.setStartDate(calendar.getTime());
        return res;
    }
    
    public static TimePeriod getTodayPeriod() throws ParseException {
        TimePeriod res = new TimePeriod();
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        res.startDate = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        res.endDate = calendar.getTime();

        return res;
    }

    public static TimePeriod getYesterdayPeriod() throws ParseException {
        TimePeriod res = new TimePeriod();
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        res.startDate = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);

        res.endDate = calendar.getTime();

        return res;
    }

    /**
     * [17:36:09] Ross Farrell: I have just spoken to a colleague and he thinks we should do Monday to Sunday.
     * @return
     * @throws java.text.ParseException
     */
    public static TimePeriod getLastWeekPeriod() throws ParseException {
        TimePeriod res = new TimePeriod();
        LocalDate previousSunday = new LocalDate().minusDays(new LocalDate().getDayOfWeek());
        LocalDate startOfPreviousWeek = previousSunday.minusDays(6);
        res.setStartDate(startOfPreviousWeek.toDateTimeAtStartOfDay().toDate());
        res.setEndDate(previousSunday.toDateTimeAtStartOfDay().withTime(23,59,59,999).toDate());
        return res;
    }

    public static TimePeriod getCurrentMonthPeriod() throws ParseException {
        TimePeriod res = new TimePeriod();
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        res.endDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        res.startDate = calendar.getTime();

        return res;
    }

    /**
     * [17:36:09] Ross Farrell: I have just spoken to a colleague and he thinks we should do Monday to Sunday.
     * @return
     * @throws java.text.ParseException
     */
    public static TimePeriod getCurrentWeekPeriod() throws ParseException {
        TimePeriod res = new TimePeriod();
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        res.endDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        res.startDate = calendar.getTime();

        return res;
    }

    /**
     * [17:36:09] Ross Farrell: I have just spoken to a colleague and he thinks we should do Monday to Sunday.
     * @return
     * @throws java.text.ParseException
     */
    public static TimePeriod getPrevWeekPeriod() throws ParseException {
        TimePeriod res = new TimePeriod();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND,59);
        calendar.add(Calendar.DATE, -7);
        res.endDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        res.startDate = calendar.getTime();

        return res;
    }

    public static TimePeriod getPrevMonthPeriod() throws ParseException {
        TimePeriod res = new TimePeriod();
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.add(Calendar.DATE, -1);
        res.endDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        res.startDate = calendar.getTime();

        return res;
    }

    public static TimePeriod getCustomPeriod(String customPrefixedDateStr) throws ParseException {
        customPrefixedDateStr = customPrefixedDateStr.replace("custom:","");
        String[] dates = customPrefixedDateStr.split(" - ");
        TimePeriod res = new TimePeriod();
        res.setStartDate(Constants.DATE_ONLY_FORMAT_YEAR_STARTED.parse(dates[0]));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Constants.DATE_ONLY_FORMAT_YEAR_STARTED.parse(dates[1]));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND,59);
        res.setEndDate(calendar.getTime());
        return res;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public static TimePeriod getOneDayPeriodFrom(Date day) {
        TimePeriod res = new TimePeriod();
        res.startDate = day;
        res.endDate = new Date(day.getTime() + 1000 * 60 * 60 * 24);

        return res;
    }

    public static TimePeriod getByCurDateRangeStr(String dateRangeStr) {
        LOGGER.info("getTimePeriodByCurDateRange() started");
        TimePeriod period;
        try {
            int weekNum = Integer.parseInt(dateRangeStr);
            period = TimePeriod.getByWeekNumber(weekNum);
        } catch (NumberFormatException e) {
            period = TimePeriod.getByName(TimePeriod.TimePeriodName.valueOf(dateRangeStr));
        }
        LOGGER.info("getTimePeriodByCurDateRange() finished");
        return period;
    }

    public static TimePeriod getMonthByNumber(int year, int month) {
        Calendar cal1 = GregorianCalendar.getInstance();
        cal1.set(year, month, 1, 0, 0, 0);
        cal1.set(Calendar.MILLISECOND, 0);

        Calendar cal2 = GregorianCalendar.getInstance();
        cal2.setTime(cal1.getTime());
        cal2.add(Calendar.MONTH, 1);
        cal2.add(Calendar.MILLISECOND, -1);
        TimePeriod timePeriod = new TimePeriod();
        timePeriod.setStartDate(cal1.getTime());
        timePeriod.setEndDate(cal2.getTime());
        return timePeriod;
    }

    @Override
    public String toString() {
        return "TimePeriod{" +
                "startDate=" + startDate +
                ",endDate=" + endDate +
                '}';
    }
}
