package cn.kalyter.css.contract;

import cn.kalyter.css.model.LoginUser;
import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public interface LoginContract {
    interface View extends BaseView {
        void showSelectCommunity();

        void showValidError();

        void showLogining();

        void showAuthFailed();
    }

    interface Presenter extends BasePresenter {
        void login(LoginUser loginUser);
    }
}
