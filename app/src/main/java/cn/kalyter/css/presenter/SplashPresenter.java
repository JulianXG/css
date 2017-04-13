package cn.kalyter.css.presenter;

import java.util.concurrent.TimeUnit;

import cn.kalyter.css.contract.SplashContract;
import cn.kalyter.css.data.source.SplashSource;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseView;
import rx.Observable;
import rx.functions.Action1;

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
            // 第一次运行
            mView.showTutorial();
        } else if (!mSplashSource.getIsLogin()) {
            // 没有登录
            mView.showLogin();
        } else if (!mSplashSource.getIsLocate()) {
            // 没有设置小区位置
            mView.showLocate();
        } else {
            // 普通的splash界面，延时过后显示主界面
            Observable.just(0)
                    .delay(mSplashSource.getSplashDelay(), TimeUnit.SECONDS)
                    .subscribe(new Action1<Integer>() {
                        @Override
                        public void call(Integer integer) {
                            mView.showMain();
                        }
                    });
        }
    }
}
