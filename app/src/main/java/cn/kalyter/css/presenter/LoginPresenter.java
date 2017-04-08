package cn.kalyter.css.presenter;

import cn.kalyter.css.contract.LoginContract;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;


    public LoginPresenter(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }
}
