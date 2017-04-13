package cn.kalyter.css.contract;

import android.support.v7.widget.RecyclerView;

import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public interface MyMessageContract {
    interface View extends BaseView {
        void setAdapter(RecyclerView.Adapter adapter);
    }

    interface Presenter extends BasePresenter {
        void loadMyMessage();
    }
}