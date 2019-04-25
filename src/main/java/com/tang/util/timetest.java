package com.tang.util;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2018/9/29.
 */
public class timetest {
    public static void main(String[] args) {
        Timestamp time1 = new Timestamp(System.currentTimeMillis());
        long a = time1.getTime();
        Timestamp time2 = new Timestamp(a+3600000);


        System.out.println(time1);
        System.out.println(time2);
    }
}
