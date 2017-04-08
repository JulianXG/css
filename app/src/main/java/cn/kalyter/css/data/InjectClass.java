package cn.kalyter.css.data;

import android.content.Context;

import cn.kalyter.css.data.repository.SplashRepository;
import cn.kalyter.css.data.source.SplashSource;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public final class InjectClass {
    private Context mContext;
    private SplashSource mSplashSource;

    public InjectClass(Context context) {
        mContext = context;
        mSplashSource = new SplashRepository(mContext);
    }

    public SplashSource getSplashSource() {
        return mSplashSource;
    }
}
