package com.changfa.frame.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtil {


    public static String getStrTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    /**
     * 返回当前日期
     *
     * @return
     * @throws ParseException
     */
    public static Date getCurDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String dateStr = sdf.format(new Date());
        return sdf.parse(dateStr);
    }

    /**
     * 格式化日期。
     *
     * @param date   日期。
     * @param format 格式字符串。
     * @return 格式化后的日期字符串。
     */
    public static String format(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }


    public static Date formatStr2Date(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }


    /**
     * /Fri Aug 21 1900 00:00:00 GMT 0800 (中国标准时间)
     */
    public static Date gmtDateFormat(String date) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss", Locale.ENGLISH);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return format.parse(format.format(sdf.parse(date)));
    }

    /**
     * 两个日期相减，例如 2015-08-08 减去 2015-08-06,返回 2015-08-06、2015-08-07、2015-08-08 三天
     */
    public static List<Date> minsDates(Date startDate, Date endDate) throws ParseException {
        List<Date> list = new ArrayList<Date>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start = dateFormat.parse(dateFormat.format(startDate));
        Date end = dateFormat.parse(dateFormat.format(endDate));

        long day = (end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000);

        if (day >= 0) {
            for (long i = 0; i <= day; i++) {
                Date date = new Date();
                date.setTime(start.getTime() + 24 * 60 * 60 * 1000 * i);
                list.add(date);
            }
        }

        return list;
    }

    /**
     * 两个日期相减，返回天数
     */
    public static int dateBetweenSize(Date startDate, Date endDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start = dateFormat.parse(dateFormat.format(startDate));
        Date end = dateFormat.parse(dateFormat.format(endDate));

        long day = (end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000);

        return (int) day + 1;
    }

}
