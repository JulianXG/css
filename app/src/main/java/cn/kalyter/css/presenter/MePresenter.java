package cn.kalyter.css.presenter;

import cn.kalyter.css.contract.MeContract;
import cn.kalyter.css.data.source.SplashSource;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.User;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class MePresenter implements MeContract.Presenter {
    private MeContract.View mView;
    private SplashSource mSplashSource;
    private UserSource mUserSource;

    public MePresenter(MeContract.View view,
                       SplashSource splashSource,
                       UserSource userSource) {
        mView = view;
        mSplashSource = splashSource;
        mUserSource = userSource;
    }

    @Override
    public void start() {
        loadUser();
    }

    @Override
    public void logout() {
        mSplashSource.setIsLogin(false);
        mView.showLogin();
    }

    @Override
    public void loadUser() {
        User user = mUserSource.getUser();
        mView.showUser(user);
    }
}
