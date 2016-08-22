package cn.renrg.util;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/21.
 */
public class DateFct {

    public static String getDateStr(Date d) {
        return DateFormat.format("yyyy-MM-dd", d).toString();
    }

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return DateFormat.format("yyyy-MM-dd kk:mm:ss", calendar.getTime()).toString();
    }

    public static String getCurrentTimes() {
        return DateFormat.format("yyyy-MM-dd kk:mm", Calendar.getInstance().getTime()).toString();
    }

    public static Date getDateAfterDays(Date d, int days) {
        long times = d.getTime();
        return new Date(times + days * 24 * 3600 * 1000l);
    }

    public static String getTimeStr(int hour, int minute) {
        StringBuffer sb = new StringBuffer();
        if (hour < 10) {
            sb.append("0");
        }
        sb.append(hour);
        sb.append(":");
        if (minute < 10) {
            sb.append("0");
        }
        sb.append(minute);
        return sb.toString();
    }

    public static String getDateStr(int year, int month, int day) {
        StringBuffer sb = new StringBuffer();
        sb.append(year);
        sb.append("-");
        if (month < 10) {
            sb.append("0");
        }
        sb.append(month);
        sb.append("-");
        if (day < 10) {
            sb.append("0");
        }
        sb.append(day);
        return sb.toString();
    }
}
