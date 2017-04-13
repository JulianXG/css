package cn.kalyter.css.contract;

import cn.kalyter.css.model.Community;
import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;
import cn.kalyter.css.util.LocateRecyclerAdapter;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public interface LocateCommunityContract {
    interface View extends BaseView {
        void showConfirmIdentify();

        void showChooseCity();

        void showMain();

        void showLocating();

        void showLocateSuccess();

        void showLocateFail();

        void setAdapter(LocateRecyclerAdapter adapter);
    }

    interface Presenter extends BasePresenter {
        void locateCommunities();
    }
}
