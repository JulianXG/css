package cn.kalyter.css.util;

import java.text.SimpleDateFormat;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public final class Config {
    public static final int IDENTITY_OWNER_CODE = 0;
    public static final int IDENTITY_FAMILY_CODE = 1;
    public static final int IDENTITY_RENTER_CODE = 2;

    public static final String IDENTITY_OWNER = "我是业主本人";
    public static final String IDENTITY_FAMILY = "我是业主家人";
    public static final String IDENTITY_RENTER = "我是租户";

    public static final String SP = "SP";

    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy年MM月dd号");
    public static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy年MM月dd号 HH:mm:ss");
}
