package cn.kalyter.css.presenter;

import cn.kalyter.css.contract.SplashContract;
import cn.kalyter.css.data.source.SplashSource;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class SplashPresenter implements SplashContract.Presenter {
    private SplashSource mSplashSource;
    private SplashContract.View mView;

    public SplashPresenter(SplashSource splashSource, SplashContract.View view) {
        mSplashSource = splashSource;
        mView = view;
    }

    @Override
    public void start() {
        if (mSplashSource.getIsFirstRun()) {
            mView.showTutorial();
        }
    }
}
