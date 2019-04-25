package com.tang.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateFormat {
    public static Date dateFormat(String date){
        if (date==null||date.equals("")){
            return null;
        }
        java.text.DateFormat bf = new SimpleDateFormat("yyyy-MM-dd");
        Date std_date = null;
        try {
            std_date = bf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return std_date;
    }
    public static  Date dateAdd(Date date,Integer num){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, num);
        Date date2 = c.getTime();
        return date2;
    }

    public static Integer daysBetween(Date smdate,Date bdate) throws ParseException
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static void main(String []a) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dateAdd(new Date(),-1));
        System.out.println(new Date());
        Integer days = daysBetween(dateAdd(new Date(),-1),new Date());

        System.out.println(days);

    }
}
