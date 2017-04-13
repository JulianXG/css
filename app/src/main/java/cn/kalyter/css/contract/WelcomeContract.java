package cn.kalyter.css.contract;

import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public interface WelcomeContract {
    interface View extends BaseView {
        void setupBanner();

        void showLogin();
    }

    interface Presenter extends BasePresenter {
        void loadNext();
    }
}
