package cn.kalyter.css.presenter;

import java.util.concurrent.TimeUnit;

import cn.kalyter.css.contract.LoginContract;
import cn.kalyter.css.data.source.SplashSource;
import cn.kalyter.css.data.source.UserApi;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.LoginUser;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
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
    public void login(LoginUser loginUser) {
        mView.showLogining();
        mUserApi.login(loginUser)
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<User>>() {
                    @Override
                    public void onCompleted() {
                        mView.showSelectCommunity();
                        mSplashSource.setIsLogin(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 原来delay只是支持成功情况下，error情况delay好像被忽略
                        mView.showAuthFailed();
                    }

                    @Override
                    public void onNext(Response<User> userResponse) {
                        User data = userResponse.getData();
                        if (data == null) {
                            mView.showAuthFailed();
                        } else {
                            mUserSource.saveUser(data);
                        }
                    }
                });
    }
}
