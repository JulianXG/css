package cn.kalyter.css.contract;

import android.support.v7.widget.RecyclerView;

import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public interface RepairContract {
    interface View extends BaseView {
        void setAdapter(RecyclerView.Adapter adapter);

        void showLoadMore(boolean isShow);

        void showRefreshing(boolean isRefreshing);

        void showNoMore();

        void showKeyword(String keyword);

        void showNoMoreItems();

        void clearNoMoreItems();

        void showLoading();

        void closeLoading();

        void showChangeStatusSuccess();

        void showChangeStatusFail();

        void showConfirmChangeStatus(int repairId, int status);
    }

    interface Presenter extends BasePresenter {
        void toggleStatus(Integer status);

        void refresh();

        void loadMore();

        void search(String keyword);

        void changeRepairStatus(int repairId, int status);
    }
}
