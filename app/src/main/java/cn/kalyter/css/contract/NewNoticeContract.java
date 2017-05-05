package cn.kalyter.css.contract;

import cn.kalyter.css.model.Message;
import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-5-4 0004.
 */

public interface NewNoticeContract {
    interface View extends BaseView {
        void showOwner();

        void showProperty();

        void showPosting();

        void closePosting();

        void showPostSuccess();

        void showPostFail();

        void showTopTimeRange();
    }

    interface Presenter extends BasePresenter {
        void loadRole();

        void postMessage(Message message);
    }
}
