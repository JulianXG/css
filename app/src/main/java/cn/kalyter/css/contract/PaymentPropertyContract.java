package cn.kalyter.css.contract;

import java.util.Date;

import cn.kalyter.css.model.Community;
import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-5-4 0004.
 */

public interface PaymentPropertyContract {
    interface View extends BaseView {
        void showPublishStatus(int status);

        void showLoading();

        void closeLoading();

        void showPublishSuccess();

        void showPublishFail();

        void showCommunity(Community community);
    }

    interface Presenter extends BasePresenter {
        void loadPublishStatus();

        void publishToyearFee(Date deadline);
    }
}
