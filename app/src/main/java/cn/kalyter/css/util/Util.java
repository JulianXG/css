package cn.kalyter.css.util;

import java.util.Date;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public final class Util {
    /**
     * 以一种漂亮的字符串格式，返回和当前时间点相差的时间
     * @param date 需要比较的时间
     * @return 相差时间字符串
     */
    public static String getPrettyDiffTime(Date date) {
        int ONE_HOUR = 60 * 60;
        int ONE_DAY = ONE_HOUR * 24;
        int ONE_MONTH = ONE_DAY * 30;
        int ONE_YEAR = ONE_MONTH * 12;
        long timestamp = date.getTime();
        if (timestamp != 0) {
            long nowTimestamp = new Date().getTime();
            int diffSecond = (int) ((nowTimestamp - timestamp) / 1000);
            if (diffSecond < 60) {
                if (diffSecond < 1) {
                    return "刚刚";
                } else {
                    return diffSecond + "秒前";
                }
            } else if (diffSecond >= 60 && diffSecond < ONE_HOUR) {
                int minute = diffSecond / 60;
                return minute + "分钟前";
            } else if (diffSecond > ONE_HOUR && diffSecond < ONE_DAY) {
                return diffSecond / ONE_HOUR + "个小时前";
            } else if (diffSecond > ONE_DAY && diffSecond < ONE_MONTH) {
                return diffSecond / ONE_DAY + "天前";
            } else if (diffSecond > ONE_MONTH && diffSecond < ONE_YEAR) {
                return diffSecond / ONE_MONTH + "个月前";
            } else {
                return new Date(timestamp).toString();
            }
        }
        return "";
    }


    /**
     * 漂亮的显示微博的来源信息
     * @param device 发送微博的设备信息
     * @return 返回微博来源信息
     */
    public static String getPrettySource(String device) {
        if (device != null) {
            device = device.substring(0, 1).toUpperCase() + device.substring(1);
        } else {
            device = "";
        }
        return "来自 " + device;
    }

}
