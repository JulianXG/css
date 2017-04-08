package cn.kalyter.css.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import cn.kalyter.css.data.source.SplashSource;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class SplashRepository implements SplashSource {
    private final String SP = "Splash";
    private static final String KEY_IS_FIRST_RUN = "IS_FIRST_RUN";
    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public SplashRepository(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(SP, Context.MODE_PRIVATE);
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
}
