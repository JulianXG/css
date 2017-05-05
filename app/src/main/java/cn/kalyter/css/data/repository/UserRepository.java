package cn.kalyter.css.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;

import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Community;
import cn.kalyter.css.model.Role;
import cn.kalyter.css.model.User;
import cn.kalyter.css.util.Config;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public class UserRepository implements UserSource {
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    private static final String KEY_USER = "USER";
    private static final String KEY_USERNAME = "USERNAME";
    private static final String KEY_PASSWORD = "PASSWORD";
    private static final String KEY_NICKNAME = "NICKNAME";
    private static final String KEY_COMMUNITY_ID = "COMMUNITY_ID";
    private static final String KEY_COMMUNITY_NAME = "COMMUNITY_NAME";
    private static final String KEY_AVATAR = "AVATAR";
    private static final String KEY_BACKGROUND = "BACKGROUND";
    private static final String KEY_BIRTHDAY = "BIRTHDAY";
    private static final String KEY_GENDER = "GENDER";
    private static final String KEY_ADDRESS = "ADDRESS";
    private static final String KEY_BIND_TEL = "BIND_TEL";

    public UserRepository(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(Config.SP, Context.MODE_PRIVATE);
    }

    @Override
    public void saveUser(User user) {
        // 默认情况下大概只有登录后会调用
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_USER, JSON.toJSONString(user));
        editor.apply();
    }

    @Override
    public User getUser() {
        String userJSON = mSharedPreferences.getString(KEY_USER, "");
        return JSON.parseObject(userJSON, User.class);
    }

    @Override
    public Community getCommunity() {
        User user = getUser();
        return user.getCommunity();
    }

    @Override
    public Role getRole() {
        return getUser().getRole();
    }
}
