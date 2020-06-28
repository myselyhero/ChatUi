package com.gy.wyy.chat.ui.tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;


/**
 *
 */
public class DateTimeUtil {

    private final static long second = 1000;//秒
    private final static long minute = 60 * second;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 返回文字描述的日期
     *
     * @param date
     * @return
     */
    public static String getTimeFormatText(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        int currentDayIndex = calendar.get(Calendar.DAY_OF_YEAR);
        int currentYear = calendar.get(Calendar.YEAR);
        calendar.setTime(date);
        int msgYear = calendar.get(Calendar.YEAR);
        int msgDayIndex = calendar.get(Calendar.DAY_OF_YEAR);
        int msgMinute = calendar.get(Calendar.MINUTE);
        String msgTimeStr = calendar.get(Calendar.HOUR_OF_DAY) + ":";
        if (msgMinute < 10) {
            msgTimeStr = msgTimeStr + "0" + msgMinute;
        } else {
            msgTimeStr = msgTimeStr + msgMinute;
        }
        if (currentDayIndex == msgDayIndex) {
            return msgTimeStr;
        } else {
            if (currentDayIndex - msgDayIndex == 1 && currentYear == msgYear) {
                msgTimeStr = "昨天 " + msgTimeStr;
            } else if (currentDayIndex - msgDayIndex > 1 && currentYear == msgYear) {
                msgTimeStr = (Integer.valueOf(calendar.get(Calendar.MONTH) + 1)) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日 " + msgTimeStr + " ";
            } else {
                msgTimeStr = msgYear + "年" + (Integer.valueOf(calendar.get(Calendar.MONTH) + 1)) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日" + msgTimeStr + " ";
            }
        }
        return msgTimeStr;
    }

    /**
     *
     * @param date
     * @return
     */
    public static String getTime(long date){

        Long day1 = date / day;
        Long hour1 = (date - day1 * second) / hour;
        Long minute1 = (date - day1 * day - hour1 * hour) / minute;
        Long second1 = (date - day1 * day - hour1 * hour - minute1 * minute) / second;
        //Long milliSecond1 = date - day1 * day - hour1 * hour - minute1 * minute - second1 * second;

        if(day1 > 0) {
            //sb.append(day1+"天");
        }
        if(hour1 > 0) {
            //sb.append(hour1+"小时");
        }
        String minu = "00";
        if(minute1 > 0) {
            if (minute1 < 10){
                minu = "0" + minute1;
            }else {
                minu = minute1+"";
            }
            //sb.append(minute1+"分");
        }
        String sec = "00";
        if(second1 > 0) {
            if (second1 < 10){
                sec = "0"+second1;
            }else {
                sec = second1+"";
            }
        }
        /*if(milliSecond1 > 0) {
            sb.append(milliSecond1+"毫秒");
        }*/
        return minu + ":" + sec;
    }
}
