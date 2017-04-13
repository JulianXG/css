package cn.kalyter.css.contract;

import android.support.design.widget.BottomNavigationView;

import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public interface MainContract {
    interface View extends BaseView {
        void setBottomItemSelectListener(BottomNavigationView.OnNavigationItemSelectedListener listener);

        void showDefaultSection();

        void showBadge(int index, int count);
    }

    interface Presenter extends BasePresenter {
        void toggleFragment(int resId);

        void loadBadge();
    }
}
