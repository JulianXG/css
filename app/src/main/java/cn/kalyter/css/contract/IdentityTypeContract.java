package cn.kalyter.css.contract;

import android.widget.ListAdapter;

import cn.kalyter.css.model.CommunityAllHouse;
import cn.kalyter.css.model.User;
import cn.kalyter.css.util.BasePresenter;
import cn.kalyter.css.util.BaseView;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */
public interface IdentityTypeContract {
    interface View extends BaseView {
        void setAdapter(ListAdapter adapter);

        void showMain();

        void showIdentitySuccess();

        void showConfirmCommunityCode();

        void showChooseHouse();

        void showRegister(User user);

        void showValidateCodeSuccess();

        void showValidateCodeFail();

        void setHouses(CommunityAllHouse communityAllHouse);

        void showLoading();

        void closeLoading();

        void requestError();
    }

    interface Presenter extends BasePresenter {
        void loadIdentities();

        void setIdentity(int communityId, int identityType);

        void validateCommunityCode(int communityId, String code);

        void loadCommunityAllHouses(int communityId);

        void selectHouse(int communityId,
                         CommunityAllHouse communityAllHouse,
                         int buildingPosition,
                         int unitPosition,
                         int roomPosition);
    }
}
