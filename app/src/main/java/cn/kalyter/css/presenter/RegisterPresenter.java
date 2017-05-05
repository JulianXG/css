package cn.kalyter.css.presenter;

import cn.kalyter.css.contract.RegisterContract;
import cn.kalyter.css.data.source.UserApi;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
import cn.kalyter.css.util.Config;
import cn.kalyter.css.util.Util;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-5-3 0003.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View mView;
    private UserApi mUserApi;

    public RegisterPresenter(RegisterContract.View view, UserApi userApi) {
        mView = view;
        mUserApi = userApi;
    }

    @Override
    public void start() {

    }

    @Override
    public void register(User user) {
        mView.showRegistering();
        user.setPassword(Util.md5(user.getPassword()));
        mUserApi.register(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        mView.closeRegistering();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.closeRegistering();
                    }

                    @Override
                    public void onNext(Response response) {
                        if (response.getCode() == Config.RESPONSE_SUCCESS_CODE) {
                            mView.showRegisterSuccess();
                            mView.showLogin();
                        } else if (response.getCode() == Config.RESPONSE_USERNAME_CONFLICT) {
                            mView.showUsernameConflict();
                        }
                    }
                });
    }
}
