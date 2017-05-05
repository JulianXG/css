package cn.kalyter.css.contract;

import cn.kalyter.css.model.User;
import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-5-3 0003.
 */

public interface RegisterContract {
    interface View extends BaseView {
        void showValidateError();

        void showRegistering();

        void closeRegistering();

        void showRegisterSuccess();

        void showLogin();

        void showRegisterFail();

        void showUsernameConflict();
    }

    interface Presenter extends BasePresenter {
        void register(User user);
    }
}
