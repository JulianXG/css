package cn.kalyter.css.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Community;
import cn.kalyter.css.model.LoginUser;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
import cn.kalyter.css.util.Config;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public class UserRepository implements UserSource {
    private Context mContext;
    private SharedPreferences mSharedPreferences;

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
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_NICKNAME, user.getNickname());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_AVATAR, user.getAvatar());
        editor.putString(KEY_BACKGROUND, user.getBackground());
        editor.putString(KEY_BIRTHDAY, user.getBirthday());
        editor.putString(KEY_GENDER, user.getGender());
        editor.putString(KEY_BIND_TEL, user.getBindTel());
        editor.putString(KEY_ADDRESS, user.getAddress());
        editor.apply();
    }

    @Override
    public User getUser() {
        User user = new User();
        user.setUsername(mSharedPreferences.getString(KEY_USERNAME, ""));
        user.setNickname(mSharedPreferences.getString(KEY_NICKNAME, ""));
        user.setPassword(mSharedPreferences.getString(KEY_PASSWORD, ""));
        user.setCommunityId(mSharedPreferences.getLong(KEY_COMMUNITY_ID, 0));
        user.setCommunityName(mSharedPreferences.getString(KEY_COMMUNITY_NAME, ""));
        user.setAvatar(mSharedPreferences.getString(KEY_AVATAR, ""));
        user.setBackground(mSharedPreferences.getString(KEY_BACKGROUND, ""));
        user.setBirthday(mSharedPreferences.getString(KEY_BIRTHDAY, ""));
        user.setGender(mSharedPreferences.getString(KEY_GENDER, ""));
        user.setBindTel(mSharedPreferences.getString(KEY_BIND_TEL, ""));
        user.setAddress(mSharedPreferences.getString(KEY_ADDRESS, ""));
        return user;
    }

    @Override
    public Community getCommunity() {
        Community community = new Community();
        community.setId(mSharedPreferences.getLong(KEY_COMMUNITY_ID, 0));
        community.setName(mSharedPreferences.getString(KEY_COMMUNITY_NAME, ""));
        return community;
    }

    @Override
    public void setCommunity(Community community) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(KEY_COMMUNITY_ID, community.getId());
        editor.putString(KEY_COMMUNITY_NAME, community.getName());
        editor.apply();
    }
}
