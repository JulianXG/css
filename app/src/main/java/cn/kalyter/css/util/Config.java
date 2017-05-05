package cn.kalyter.css.util;

import android.os.Build;

import java.text.SimpleDateFormat;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public final class Config {
//    public static final String API_ROOT_URL = "http://192.168.253.1:5000";
//    public static final String API_ROOT_URL = "http://192.168.1.111:5000";
    public static final String API_ROOT_URL = "http://kalyter.cn:2000";

    // 服务端返回码
    public static final int RESPONSE_SUCCESS_CODE = 200;
    public static final int RESPONSE_USERNAME_OR_PASSWORD_ERROR = 400;
    public static final int RESPONSE_USERNAME_CONFLICT = 401;

    // 角色配置
    public static final int IDENTITY_OWNER_CODE = 0;
    public static final int IDENTITY_FAMILY_CODE = 1;
    public static final int IDENTITY_RENTER_CODE = 2;

    // 用户角色
    public static final int ROLE_PROPERTY = 1;
    public static final int ROLE_OWNER = 2;

    public static final String SP = "SP";

    public static final String LOCAL_DEVICE = Build.BRAND + " " + Build.MODEL;


    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy年MM月dd号");
    public static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy年MM月dd号 HH:mm:ss");

    public static final int REQUEST_CODE_PERMISSION = 1000;


    // Bundle Keys
    public static final String BUNDLE_COMMUNITY_ID = "COMMUNITY_ID";
    public static final String BUNDLE_USER = "USER";

    // 百度地图相关
    public static final int NETWORK_LOCATE_SUCCESS = 161;
    public static final int LOCATE_DENIED = 167;

    // 刷新列表相关配置
    public static final int ALREADY_LATEST = 100;
    public static final int REFRESH_SUCCESS = 101;

    // 缴费状态
    public static final int STATUS_PAY_REQUIRED = 0;
    public static final int STATUS_PAY_FINISHED = 1;
    public static final String PAY_REQUIRED = "未缴费";
    public static final String PAY_FINISHED = "已缴费";

    // 物业费发布状态
    public static final int PROPERTY_FEE_REQUIRED = 0;
    public static final int PROPERTY_FEE_FINISHED = 1;

    // 报修状态
    public static final String REPAIR_REQUIRED = "待解决";
    public static final String REPAIR_REPAIRING = "解决中";
    public static final String REPAIR_FINISHED = "已解决";

    public static final int STATUS_REPAIR_REQUIRED = 0;
    public static final int STATUS_REPAIR_REPAIRING = 1;
    public static final int STATUS_REPAIR_FINISHED = 2;

    public static final int PAGE_SIZE = 10;
}
