package cn.kalyter.css.contract;

import android.support.v7.widget.RecyclerView;

import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public interface PaymentRecordContract {
    interface View extends BaseView {
        void setAdapter(RecyclerView.Adapter adapter);

        void showLoadMore(boolean isShow);

        void showRefreshing(boolean isRefreshing);

        void showNoMore();

        void showNoSearchResult();

        void showKeyword(String keyword);

        void showOwner();

        void showProperty();

        void showNoItems();

        void clearNoItems();
    }

    interface Presenter extends BasePresenter {
        void refresh();

        void loadMore();

        void search(String keyword);

        void loadRole();

        void toggleStatus(int status);
    }
}
