package cn.kalyter.css.contract;

import cn.kalyter.css.model.User;
import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public interface ProfileContract {
    interface View extends BaseView {
        void showUser(User user);

        void showLoading();

        void showChangeSuccess();

        void showActionFail();

        void closeLoading();
    }

    interface Presenter extends BasePresenter {
        void loadUser();

        void changeProfile(User user);

        void refreshUser();
    }
}
