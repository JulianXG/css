package cn.kalyter.css.presenter;

import java.util.Calendar;
import java.util.Date;

import cn.kalyter.css.contract.PaymentPropertyContract;
import cn.kalyter.css.data.source.PaymentApi;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Community;
import cn.kalyter.css.model.Payment;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.util.Config;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-5-4 0004.
 */

public class PaymentPropertyPresenter implements PaymentPropertyContract.Presenter {
    private PaymentApi mPaymentApi;
    private PaymentPropertyContract.View mView;
    private UserSource mUserSource;
    private Community mCommunity;
    private int mYear;

    public PaymentPropertyPresenter(PaymentApi paymentApi,
                                    PaymentPropertyContract.View view,
                                    UserSource userSource) {
        mPaymentApi = paymentApi;
        mView = view;
        mUserSource = userSource;
        mYear = Calendar.getInstance().get(Calendar.YEAR);
    }

    @Override
    public void start() {
        mCommunity = mUserSource.getCommunity();
        mView.showCommunity(mCommunity);
        loadPublishStatus();
    }

    @Override
    public void loadPublishStatus() {
        mView.showLoading();
        mPaymentApi.getPublishStatus(mCommunity.getId(), mYear)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        mView.closeLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.closeLoading();
                    }

                    @Override
                    public void onNext(Response response) {
                        if (response.getCode() == Config.RESPONSE_SUCCESS_CODE) {
                            mView.showPublishStatus(Config.PROPERTY_FEE_FINISHED);
                        } else {
                            mView.showPublishStatus(Config.PROPERTY_FEE_REQUIRED);
                        }
                    }
                });
    }

    @Override
    public void publishToyearFee(Date deadline) {
        mView.showLoading();
        Payment payment = new Payment();
        payment.setCommunityId(mCommunity.getId());
        payment.setDeadline(deadline);
        payment.setYear(mYear);
        mPaymentApi.publishPropertyFee(payment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        mView.closeLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showPublishFail();
                        mView.closeLoading();
                    }

                    @Override
                    public void onNext(Response response) {
                        if (response.getCode() == Config.RESPONSE_SUCCESS_CODE) {
                            mView.showPublishSuccess();
                            mView.showPublishStatus(Config.PROPERTY_FEE_FINISHED);
                        } else {
                            mView.showPublishFail();
                        }
                    }
                });
    }
}
