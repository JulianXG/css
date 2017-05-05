package cn.kalyter.css.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import cn.kalyter.css.data.source.SplashSource;
import cn.kalyter.css.util.Config;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class SplashRepository implements SplashSource {
    private static final String KEY_IS_FIRST_RUN = "IS_FIRST_RUN";
    private static final String KEY_IS_LOGIN = "IS_LOGIN";
    private static final String KEY_IS_LOCATE = "IS_LOCATE";
    private static final String KEY_SPLASH_DELAY = "SPLASH_DELAY";
    private static final String KEY_IDENTITY = "IDENTITY";

    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public SplashRepository(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(Config.SP, Context.MODE_PRIVATE);
    }

    @Override
    public void setIsFirstRun(boolean isFirstRun) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(KEY_IS_FIRST_RUN, isFirstRun);
        editor.apply();
    }

    @Override
    public boolean getIsFirstRun() {
        return mSharedPreferences.getBoolean(KEY_IS_FIRST_RUN, true);
    }

    @Override
    public boolean getIsLogin() {
        return mSharedPreferences.getBoolean(KEY_IS_LOGIN, false);
    }

    @Override
    public void setIsLogin(boolean isLogin) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGIN, isLogin);
        editor.apply();
    }

    @Override
    public boolean getIsLocate() {
        return mSharedPreferences.getBoolean(KEY_IS_LOCATE, false);
    }

    @Override
    public void setIsLocate(boolean isLocate) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOCATE, isLocate);
        editor.apply();
    }

    @Override
    public int getSplashDelay() {
        return mSharedPreferences.getInt(KEY_SPLASH_DELAY, 3);
    }

    @Override
    public void setSplashDelay(int splashDelay) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(KEY_SPLASH_DELAY, splashDelay);
        editor.apply();
    }

    @Override
    public void setIdentityType(int identityType) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(KEY_IDENTITY, identityType);
        editor.apply();
    }

    @Override
    public int getIdentityType() {
        return mSharedPreferences.getInt(KEY_IDENTITY, -1);
    }
}
