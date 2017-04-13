package cn.kalyter.css.presenter;

import cn.kalyter.css.contract.ProfileContract;
import cn.kalyter.css.data.source.UserApi;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.User;

/**
 * Created by Kalyter on 2017-4-11 0011.
 */

public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View mView;
    private UserSource mUserSource;
    private UserApi mUserApi;


    public ProfilePresenter(ProfileContract.View view,
                            UserSource userSource,
                            UserApi userApi) {
        mView = view;
        mUserSource = userSource;
        mUserApi = userApi;
    }

    @Override
    public void start() {
        loadUser();
    }

    @Override
    public void loadUser() {
        User user = mUserSource.getUser();
        mView.showUser(user);
    }
}
