package cn.kalyter.css.presenter;

import java.util.Date;

import cn.kalyter.css.contract.FeedbackContract;
import cn.kalyter.css.data.source.FeedbackApi;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Feedback;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-6-1 0001.
 */

public class FeedbackPresenter implements FeedbackContract.Presenter {
    private FeedbackContract.View mView;
    private UserSource mUserSource;
    private FeedbackApi mFeedbackApi;
    private User mUser;

    public FeedbackPresenter(FeedbackContract.View view,
                             UserSource userSource,
                             FeedbackApi feedbackApi) {
        mView = view;
        mUserSource = userSource;
        mFeedbackApi = feedbackApi;
    }

    @Override
    public void start() {
        mUser = mUserSource.getUser();
    }

    @Override
    public void submitFeedback(String content) {
        mView.showSubmitting();
        Feedback feedback = new Feedback();
        feedback.setUserId(mUser.getId());
        feedback.setPostTime(new Date());
        feedback.setContent(content);
        mFeedbackApi.postFeedback(feedback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.closeSubmitting();
                        mView.showSubmitFail();
                    }

                    @Override
                    public void onNext(Response response) {
                        mView.closeSubmitting();
                        mView.showSubmitSuccess();
                    }
                });
    }
}
