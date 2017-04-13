package cn.kalyter.css.presenter;

import android.content.Context;

import cn.kalyter.css.contract.WelcomeContract;
import cn.kalyter.css.data.source.SplashSource;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class WelcomePresenter implements WelcomeContract.Presenter {
    private WelcomeContract.View mView;
    private SplashSource mSplashSource;
    private Context mContext;

    public WelcomePresenter(WelcomeContract.View view,
                            SplashSource splashSource,
                            Context context) {
        mView = view;
        mSplashSource = splashSource;
        mContext = context;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadNext() {
        mSplashSource.setIsFirstRun(false);
        mView.showLogin();
    }
}
