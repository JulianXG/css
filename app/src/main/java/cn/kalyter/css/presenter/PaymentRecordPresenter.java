package cn.kalyter.css.presenter;

import android.content.Context;

import java.util.List;

import cn.kalyter.css.contract.PaymentRecordContract;
import cn.kalyter.css.data.source.PaymentSource;
import cn.kalyter.css.model.Payment;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.util.PaymentRecyclerAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-13 0013.
 */

public class PaymentRecordPresenter implements PaymentRecordContract.Presenter {
    private PaymentRecordContract.View mView;
    private Context mContext;
    private PaymentSource mPaymentSource;
    private PaymentRecyclerAdapter mPaymentRecyclerAdapter;

    public PaymentRecordPresenter(PaymentRecordContract.View view,
                                  Context context,
                                  PaymentSource paymentSource) {
        mView = view;
        mContext = context;
        mPaymentSource = paymentSource;
        mPaymentRecyclerAdapter = new PaymentRecyclerAdapter(mContext);
    }

    @Override
    public void start() {
        mView.setAdapter(mPaymentRecyclerAdapter);
        loadMyPaymentRecord();
    }

    @Override
    public void loadMyPaymentRecord() {
        mPaymentSource.getMyPaymentRecords()
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
}
