package cn.kalyter.css.presenter;

import cn.kalyter.css.contract.MeContract;
import cn.kalyter.css.data.source.SplashSource;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.User;
import cn.kalyter.css.util.Config;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class MePresenter implements MeContract.Presenter {
    private MeContract.View mView;
    private SplashSource mSplashSource;
    private UserSource mUserSource;
    private User mUser;

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
        mUser = mUserSource.getUser();
        mView.showUser(mUser);
        if (mUser.getRoleId() == Config.ROLE_OWNER) {
            mView.showOwner();
        } else if (mUser.getRoleId() == Config.ROLE_PROPERTY) {
            mView.showProperty();
        }
    }
}
