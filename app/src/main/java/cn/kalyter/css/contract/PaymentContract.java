package cn.kalyter.css.contract;

import android.support.v7.widget.RecyclerView;

import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public interface PaymentContract {
    interface View extends BaseView {
        void showAmount(Double amount);

        void setAdapter(RecyclerView.Adapter adapter);

        void showLoadMore(boolean isShow);

        void showRefreshing(boolean isRefreshing);

        void showNoPayment();
    }

    interface Presenter extends BasePresenter {
        void pay();

        void loadPayments();

        void toggleAllCheckStatus(boolean allCheckStatus);
    }
}
