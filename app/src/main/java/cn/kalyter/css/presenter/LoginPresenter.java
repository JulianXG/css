package cn.kalyter.css.presenter;

import cn.kalyter.css.contract.LoginContract;
import cn.kalyter.css.data.source.SplashSource;
import cn.kalyter.css.data.source.UserApi;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
import cn.kalyter.css.util.Config;
import cn.kalyter.css.util.Util;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;
    private UserSource mUserSource;
    private UserApi mUserApi;
    private SplashSource mSplashSource;

    public LoginPresenter(LoginContract.View view,
                          UserSource userSource,
                          UserApi userApi,
                          SplashSource splashSource) {
        mView = view;
        mUserSource = userSource;
        mUserApi = userApi;
        mSplashSource = splashSource;
    }

    @Override
    public void start() {

    }

    @Override
    public void login(final User user) {
        mView.showLogining();
        user.setPassword(Util.md5(user.getPassword()));
        mUserApi.login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // 原来delay只是支持成功情况下，error情况delay好像被忽略
                        mView.showAuthFailed();
                    }

                    @Override
                    public void onNext(Response<User> userResponse) {
                        if (userResponse.getCode() == Config.RESPONSE_USERNAME_OR_PASSWORD_ERROR) {
                            mView.showAuthFailed();
                        } else if (userResponse.getCode() == Config.RESPONSE_SUCCESS_CODE){
                            mUserSource.saveUser(userResponse.getData());
                            mView.showMain();
                            mSplashSource.setIsLogin(true);
                        }
                    }
                });
    }
}
