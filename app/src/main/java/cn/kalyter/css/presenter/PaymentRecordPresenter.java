package cn.kalyter.css.presenter;

import android.content.Context;

import java.util.List;

import cn.kalyter.css.contract.PaymentRecordContract;
import cn.kalyter.css.data.source.PaymentApi;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Payment;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
import cn.kalyter.css.util.Config;
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
    private PaymentApi mPaymentApi;
    private UserSource mUserSource;
    private PaymentRecyclerAdapter mPaymentRecyclerAdapter;
    private User mUser;
    private String mKeyword = "";
    private int mCurrentPage = 1;
    private int mStatus = Config.STATUS_PAY_REQUIRED;

    public PaymentRecordPresenter(PaymentRecordContract.View view,
                                  Context context,
                                  PaymentApi paymentApi,
                                  UserSource userSource) {
        mView = view;
        mContext = context;
        mPaymentApi = paymentApi;
        mPaymentRecyclerAdapter = new PaymentRecyclerAdapter(mContext);
        mUserSource = userSource;
    }

    @Override
    public void start() {
        mView.setAdapter(mPaymentRecyclerAdapter);
        loadRole();
        refresh();
    }

    @Override
    public void refresh() {
        mView.clearNoItems();
        mCurrentPage = 1;
        mPaymentApi.getPayments(mUser.getCommunityId(), Config.PAGE_SIZE,
                1, mStatus, null, mUser.getHouseId())
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
                    }

                    @Override
                    public void onNext(Response<List<Payment>> listResponse) {
                        List<Payment> data = listResponse.getData();
                        if (data.size() == 0) {
                            mView.showNoItems();
                        } else {
                            mPaymentRecyclerAdapter.setPropertyFeeData(data);
                        }
                    }
                });
    }

    @Override
    public void loadMore() {
        mPaymentApi.getPayments(mUser.getCommunityId(), Config.PAGE_SIZE,
                mCurrentPage + 1, mStatus, null, mUser.getHouseId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<Payment>>>() {
                    @Override
                    public void onCompleted() {
                        mView.showLoadMore(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoadMore(false);
                    }

                    @Override
                    public void onNext(Response<List<Payment>> listResponse) {
                        List<Payment> data = listResponse.getData();
                        if (data.size() == 0) {
                            mView.showNoMore();
                        } else {
                            mPaymentRecyclerAdapter.addPropertyFeeData(data);
                            mCurrentPage++;
                        }
                    }
                });
    }

    @Override
    public void search(String keyword) {
        mKeyword = keyword;
    }

    @Override
    public void loadRole() {
        mUser = mUserSource.getUser();
        if (mUser.getRoleId() == Config.ROLE_PROPERTY) {
            mUser.setHouseId(null);
            mView.showProperty();
        } else if (mUser.getRoleId() == Config.ROLE_OWNER) {
            mView.showOwner();
        }
    }

    @Override
    public void toggleStatus(int status) {
        mStatus = status;
        refresh();
    }
}
