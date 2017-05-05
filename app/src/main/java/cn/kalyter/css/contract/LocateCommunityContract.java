package cn.kalyter.css.contract;

import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;
import cn.kalyter.css.util.LocateRecyclerAdapter;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public interface LocateCommunityContract {
    interface View extends BaseView {
        void showConfirmIdentify(int communityId);

        void showChooseCity();

        void showMain();

        void showLocating();

        void showLocatingCommunity();

        void showLocateSuccess();

        void showLocateFail();

        void showTitle(String title);

        void setAdapter(LocateRecyclerAdapter adapter);

        void requestPermissions(String[] permissions);

        void showLocateDenied();

        void showNotCooperate();

        void showSearch();
    }

    interface Presenter extends BasePresenter {
        void locateCommunities();

        void locateCurrentPlace();

        void searchCommunity(String city, String keyword);
    }
}
