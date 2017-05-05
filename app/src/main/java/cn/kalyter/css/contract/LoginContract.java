package cn.kalyter.css.contract;

import cn.kalyter.css.model.User;
import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public interface LoginContract {
    interface View extends BaseView {
        void showLoginSuccess();

        void showMain();

        void showSelectCommunity();

        void showValidError();

        void showLogining();

        void showAuthFailed();
    }

    interface Presenter extends BasePresenter {
        void login(User user);
    }
}
