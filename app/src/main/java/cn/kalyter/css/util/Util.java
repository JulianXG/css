package cn.kalyter.css.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        if (device == null)  {
            device = "手机APP";
        }
        return "来自 " + device;
    }


    /**
     * MD5单向加密，生成32位大写字母和数字的组合
     * @param originData 原始字符串
     * @return MD5加密后的数据
     */
    public static String md5(String originData) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(originData.getBytes());
            return new BigInteger(1, digest.digest()).toString(16).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    /**
     * 如果系统版本大于等于6，则动态请求权限，如果权限已经拥有则跳过
     * @param activity 当前Activity
     * @param permissions 需要启用的permission
     */
    public static void requestPermission(Activity activity, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> needPermissions = new ArrayList<>();
            for (String permission : permissions) {
                int result = ContextCompat.checkSelfPermission(activity, permission);
                if (result == PackageManager.PERMISSION_DENIED) {
                    needPermissions.add(permission);
                }
            }
            if (needPermissions.size() > 0) {
                ActivityCompat.requestPermissions(activity,
                        needPermissions.toArray(new String[]{}),
                        Config.REQUEST_CODE_PERMISSION);
            }
        }
    }
}
