package cn.kalyter.css.contract;

import cn.kalyter.css.model.Community;
import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;
import cn.kalyter.css.util.MessageRecyclerAdapter;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public interface BoardContract {
    interface View extends BaseView {
        void showNewMessage();

        void showCommunity(Community community);

        void showLoadMore(boolean isShow);

        void showRefreshing(boolean isRefreshing);

        void setBoardRecyclerAdapter(MessageRecyclerAdapter adapter);
    }

    interface Presenter extends BasePresenter {
        void loadCommunity();

        void loadBoardMessage();

        void refresh();

        void loadMore();
    }
}
