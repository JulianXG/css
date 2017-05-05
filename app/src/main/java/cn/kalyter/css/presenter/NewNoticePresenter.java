package cn.kalyter.css.presenter;

import java.util.Date;

import cn.kalyter.css.contract.NewNoticeContract;
import cn.kalyter.css.data.source.MessageApi;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Message;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.Role;
import cn.kalyter.css.model.User;
import cn.kalyter.css.util.Config;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-5-4 0004.
 */

public class NewNoticePresenter implements NewNoticeContract.Presenter {
    private NewNoticeContract.View mView;
    private MessageApi mMessageApi;
    private UserSource mUserSource;
    private User mUser;
    private Role mRole;

    public NewNoticePresenter(NewNoticeContract.View view,
                              MessageApi messageApi,
                              UserSource userSource) {
        mView = view;
        mMessageApi = messageApi;
        mUserSource = userSource;
    }

    @Override
    public void start() {
        loadRole();
    }

    @Override
    public void loadRole() {
        mUser = mUserSource.getUser();
        mRole = mUserSource.getRole();
        if (mRole.getId() == Config.ROLE_OWNER) {
            mView.showOwner();
        } else if (mRole.getId() == Config.ROLE_PROPERTY){
            mView.showProperty();
        }
    }

    @Override
    public void postMessage(Message message) {
        mView.showPosting();
        message.setUserId(mUser.getId());
        message.setCommunityId(mUser.getCommunityId());
        message.setPostTime(new Date());
        message.setSource(Config.LOCAL_DEVICE);
        mMessageApi.postMessage(message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        mView.closePosting();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.closePosting();
                        mView.showPostFail();
                    }

                    @Override
                    public void onNext(Response response) {
                        if (response.getCode() == Config.RESPONSE_SUCCESS_CODE) {
                            mView.showPostSuccess();
                        } else {
                            mView.showPostFail();
                        }
                    }
                });
    }
}
