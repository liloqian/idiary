package com.example.leo.diarynew.util;

import java.util.Calendar;

/**
 * Created by leo on 2017/8/19.
 */

public class GetTimeUtil {

    private static Calendar mCalendar = Calendar.getInstance();

    public static String getData(){
        return mCalendar.get(Calendar.YEAR) + "年" +
                (mCalendar.get(Calendar.MONTH)+1) + "月" +
                mCalendar.get(Calendar.DAY_OF_MONTH) + "日";
    }
}
