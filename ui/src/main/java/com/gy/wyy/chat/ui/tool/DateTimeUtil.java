package com.gy.wyy.chat.ui.tool;

import java.util.Calendar;
import java.util.Date;


/**
 *
 */
public class DateTimeUtil {

    private final static long minute = 60 * 1000;// 1分钟
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
}
