package cn.kalyter.css.presenter;

import android.content.Context;

import java.util.List;

import cn.kalyter.css.contract.PaymentContract;
import cn.kalyter.css.data.source.PaymentSource;
import cn.kalyter.css.model.Payment;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.util.PaymentRecyclerAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class PaymentPresenter implements PaymentContract.Presenter,
        PaymentRecyclerAdapter.OnCheckPaymentItemListener {
    private PaymentContract.View mView;
    private PaymentSource mPaymentSource;
    private PaymentRecyclerAdapter mPaymentRecyclerAdapter;
    private Context mContext;

    public PaymentPresenter(PaymentContract.View view,
                            PaymentSource paymentSource,
                            Context context) {
        mView = view;
        mPaymentSource = paymentSource;
        mContext = context;
        mPaymentRecyclerAdapter = new PaymentRecyclerAdapter(mContext, true);
    }

    @Override
    public void start() {
        mView.setAdapter(mPaymentRecyclerAdapter);
        mPaymentRecyclerAdapter.setOnCheckPaymentItemListener(this);
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
        mPaymentSource.getPayments()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<Payment>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response<List<Payment>> listResponse) {
                        mPaymentRecyclerAdapter.setData(listResponse.getData());
                    }
                });
    }

    @Override
    public void toggleAllCheckStatus(boolean allCheckStatus) {
        mPaymentRecyclerAdapter.setAllCheckStatus(allCheckStatus);
        onCheckedChanged();
    }
}
