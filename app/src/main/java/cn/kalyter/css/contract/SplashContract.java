package cn.kalyter.css.contract;

import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public interface SplashContract {
    interface View extends BaseView {
        void showTutorial();

        void showLogin();

        void showMain();

        void showLocate();
    }

    interface Presenter extends BasePresenter {

    }
}
