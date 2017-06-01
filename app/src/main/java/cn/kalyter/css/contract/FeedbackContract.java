package cn.kalyter.css.contract;

import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public interface FeedbackContract {
    interface View extends BaseView {
        void showSubmitting();

        void showSubmitSuccess();

        void closeSubmitting();

        void showSubmitFail();
    }

    interface Presenter extends BasePresenter {
        void submitFeedback(String content);
    }
}
