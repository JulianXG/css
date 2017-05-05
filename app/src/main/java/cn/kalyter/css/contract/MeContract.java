package cn.kalyter.css.contract;

import cn.kalyter.css.model.User;
import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public interface MeContract {
    interface View extends BaseView {
        void showLogin();

        void showUser(User user);

        void showOwner();

        void showProperty();
    }

    interface Presenter extends BasePresenter {
        void logout();

        void loadUser();
    }
}
