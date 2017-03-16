package htf.htfmms.Database;

import android.app.Activity;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Lin on 2016/5/29.
 */
public class DateUtil {
    public static String getCurTimeInMillis() {
        return String.valueOf(Calendar.getInstance().getTimeInMillis());
    }

    public static String getCurDateYYYYMMDD() {
        Date date = new Date();
        date.setTime(Calendar.getInstance().getTimeInMillis());

        String strDate = "";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        strDate = format.format(date);
        return strDate;
    }

    public static String getCurDateYYYYMMDDHHMM() {
        Locale.setDefault(Locale.getDefault());
        Date date = new Date();
        date.setTime(Calendar.getInstance().getTimeInMillis());

        String strDate = "";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        strDate = format.format(date);
        return strDate;
    }
    public static String getCurDateYYYYMMDDHHMMSS() {
        Locale.setDefault(Locale.getDefault());
        Date date = new Date();
        date.setTime(Calendar.getInstance().getTimeInMillis());

        String strDate = "";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        strDate = format.format(date);
        return strDate;
    }

    public static HashMap<String, Integer> getYearMonthDayMap2(String date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date time = null;
        try {
            time = format.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("year", calendar.get(Calendar.YEAR));
        map.put("month", calendar.get(Calendar.MONTH));
        map.put("day", calendar.get(Calendar.DAY_OF_MONTH));
        return map;
    }

    //yyyy-MM-dd
    public static String getNextMonthDate(String date){
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date time = null;
        try {
            time = format.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        return format.format(calendar.getTime());
    }

    public static String formatNumber(int number) {
        if (number < 10) {
            return "0" + String.valueOf(number);
        } else {
            return String.valueOf(number);
        }
    }

    public static boolean isDate1Earlier(String date1, String date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date time1 = null;
        Date time2 = null;
        try {
            time1 = format.parse(date1);
            time2 = format.parse(date2);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(time1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(time2);

        return calendar1.before(calendar2);
    }

    public static Calendar getClendarByDate(String date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date time = null;
        try {
            time = format.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        return calendar;
    }

    public static boolean isToday(String rawDate) {
        boolean isToday = false;
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd");
            Date date = null;
            try {
                date = format.parse(rawDate);
            } catch (java.text.ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            Calendar todayCalendar = Calendar.getInstance();
            todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
            todayCalendar.set(Calendar.MINUTE, 0);
            todayCalendar.set(Calendar.SECOND, 0);

            Calendar yestodayCalendar = Calendar.getInstance();
            yestodayCalendar.set(Calendar.HOUR_OF_DAY, -1);
            yestodayCalendar.set(Calendar.MINUTE, 0);
            yestodayCalendar.set(Calendar.SECOND, 0);

            isToday = calendar.before(todayCalendar)
                    && calendar.after(yestodayCalendar);

        } catch (Exception e) {
            // TODO: handle exception
        }
        return isToday;
    }
    /**
     * 几分钟前，几天前等提示
     **/

    public static boolean isUpDate(String date1,String date2){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean ret=false;
        try
        {
            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);
            long diff = d1.getTime() - d2.getTime();
            if(diff>0){
                ret =true;
            }
        }
        catch (Exception e)
        {

        }
        return ret;
    }
}
