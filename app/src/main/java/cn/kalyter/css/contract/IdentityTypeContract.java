package cn.kalyter.css.contract;

import android.widget.ListAdapter;

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
    }

    interface Presenter extends BasePresenter {
        void loadIdentities();

        void setIdentity(int identityType);
    }
}
