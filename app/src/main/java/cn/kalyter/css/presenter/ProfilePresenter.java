package cn.kalyter.css.presenter;

import cn.kalyter.css.contract.ProfileContract;
import cn.kalyter.css.data.source.UserApi;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-11 0011.
 */

public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View mView;
    private UserSource mUserSource;
    private UserApi mUserApi;
    private User mUser;

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
        mUser = mUserSource.getUser();
        mView.showUser(mUser);
    }

    @Override
    public void changeProfile(User user) {
        mView.showLoading();
        mUserApi.changeUserProfile(mUser.getId(), user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        mView.closeLoading();
                        mView.showChangeSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.closeLoading();
                        mView.showActionFail();
                    }

                    @Override
                    public void onNext(Response response) {
                        refreshUser();
                    }
                });
    }

    @Override
    public void refreshUser() {
        mView.showLoading();
        mUserApi.getUserById(mUser.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<User>>() {
                    @Override
                    public void onCompleted() {
                        mView.closeLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.closeLoading();
                        mView.showActionFail();
                    }

                    @Override
                    public void onNext(Response<User> userResponse) {
                        mUser = userResponse.getData();
                        mUserSource.saveUser(mUser);
                        mView.showUser(mUser);
                    }
                });
    }
}
