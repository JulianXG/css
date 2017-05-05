package cn.kalyter.css.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import cn.kalyter.css.contract.PaymentContract;
import cn.kalyter.css.data.source.PaymentApi;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Community;
import cn.kalyter.css.model.Payment;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
import cn.kalyter.css.util.Config;
import cn.kalyter.css.util.PaymentRecyclerAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class PaymentPresenter implements PaymentContract.Presenter,
        PaymentRecyclerAdapter.OnCheckPaymentItemListener {
    private static final String TAG = "PaymentPresenter";

    private PaymentContract.View mView;
    private PaymentApi mPaymentApi;
    private PaymentRecyclerAdapter mPaymentRecyclerAdapter;
    private UserSource mUserSource;
    private Context mContext;
    private Community mCommunity;
    private User mUser;

    public PaymentPresenter(PaymentContract.View view,
                            PaymentApi paymentApi,
                            Context context,
                            UserSource userSource) {
        mView = view;
        mPaymentApi = paymentApi;
        mContext = context;
        mPaymentRecyclerAdapter = new PaymentRecyclerAdapter(mContext, true);
        mUserSource = userSource;
    }

    @Override
    public void start() {
        mView.setAdapter(mPaymentRecyclerAdapter);
        mPaymentRecyclerAdapter.setOnCheckPaymentItemListener(this);
        mCommunity = mUserSource.getCommunity();
        mUser = mUserSource.getUser();
        loadPayments();
    }

    @Override
    public void onCheckedChanged() {
        List<Payment> payments = mPaymentRecyclerAdapter.getData();
        Double amount = 0.0;
        for (Payment payment : payments) {
            amount += payment.getAmount();
        }
        mView.showAmount(amount);
    }

    @Override
    public void pay() {

    }

    @Override
    public void loadPayments() {
        mPaymentApi.getPayments(mCommunity.getId(), Config.PAGE_SIZE, 1,
                Config.STATUS_PAY_REQUIRED, null, mUser.getHouseId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<Payment>>>() {
                    @Override
                    public void onCompleted() {
                        mView.showRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showRefreshing(false);
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(Response<List<Payment>> listResponse) {
                        List<Payment> data = listResponse.getData();
                        if (data.size() == 0) {
                            mView.showNoPayment();
                        } else {
                            mPaymentRecyclerAdapter.setPropertyFeeData(data);
                        }
                    }
                });
    }

    @Override
    public void toggleAllCheckStatus(boolean allCheckStatus) {
        mPaymentRecyclerAdapter.setAllCheckStatus(allCheckStatus);
        onCheckedChanged();
    }
}
