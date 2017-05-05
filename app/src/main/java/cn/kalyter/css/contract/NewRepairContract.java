package cn.kalyter.css.contract;

import java.util.Date;

import cn.kalyter.css.model.Repair;
import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-5-5 0005.
 */

public interface NewRepairContract {
    interface View extends BaseView {
        void showPosting();

        void closePosting();

        void showPostSuccess();

        void showPostFail();

        void showExpectTime(Date date);

        void clearUIStatusAndData();
    }

    interface Presenter extends BasePresenter {
        void post(Repair repair);
    }
}
