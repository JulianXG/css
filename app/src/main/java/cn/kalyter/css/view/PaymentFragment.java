package cn.kalyter.css.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.PaymentContract;
import cn.kalyter.css.presenter.PaymentPresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseFragment;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class PaymentFragment extends BaseFragment implements PaymentContract.View,
        BGARefreshLayout.BGARefreshLayoutDelegate{
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.no_items)
    TextView mNoItems;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.all_check)
    CheckBox mAllCheck;
    @BindView(R.id.amount)
    TextView mAmount;
    @BindView(R.id.pay)
    Button mPay;
    @BindView(R.id.pay_container)
    LinearLayout mPayContainer;
    @BindView(R.id.refresh)
    BGARefreshLayout mRefresh;

    @OnClick(R.id.pay)
    void pay() {
        mPresenter.pay();
    }

    @OnCheckedChanged(R.id.all_check)
    void allCheck(boolean checkStatus) {
        mPresenter.toggleAllCheckStatus(checkStatus);
    }

    private PaymentContract.Presenter mPresenter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPresenter.start();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new PaymentPresenter(this,
                App.getInjectClass().getPaymentSource(),
                getContext());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_payment;
    }

    @Override
    public void showAmount(Double amount) {
        mPayContainer.setVisibility(View.VISIBLE);
        mAmount.setText(String.format("￥%.2f", amount));
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(mLinearLayoutManager);
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        refreshViewHolder.setLoadingMoreText(getString(R.string.pull_load_more));
        mRefresh.setRefreshViewHolder(refreshViewHolder);
        mRefresh.setDelegate(this);
        mRecycler.setAdapter(adapter);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        Observable.just(0)
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mRefresh.endRefreshing();
                    }
                });
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return true;
    }
}
