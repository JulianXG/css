package cn.kalyter.css.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.PaymentRecordContract;
import cn.kalyter.css.presenter.PaymentRecordPresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseActivity;
import cn.kalyter.css.util.Config;
import cn.kalyter.css.util.RecycleViewDivider;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class PaymentRecordActivity extends BaseActivity implements PaymentRecordContract.View,
        BGARefreshLayout.BGARefreshLayoutDelegate{
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    BGARefreshLayout mRefresh;
    @BindView(R.id.tab)
    TabLayout mTabLayout;
    @BindView(R.id.no_items)
    TextView mNoItems;

    private PaymentRecordContract.Presenter mPresenter;
    private String mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle.setText(R.string.my_payment);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTabLayout.addTab(mTabLayout.newTab().setText(Config.PAY_REQUIRED));
        mTabLayout.addTab(mTabLayout.newTab().setText(Config.PAY_FINISHED));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals(Config.PAY_REQUIRED)) {
                    mPresenter.toggleStatus(Config.STATUS_PAY_REQUIRED);
                } else if (tab.getText().equals(Config.PAY_FINISHED)) {
                    mPresenter.toggleStatus(Config.STATUS_PAY_FINISHED);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mPresenter.start();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new PaymentRecordPresenter(this, this,
                App.getInjectClass().getPaymentApi(),
                App.getInjectClass().getUserSource());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment_record;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(manager);
        mRecycler.addItemDecoration(new RecycleViewDivider(this,
                LinearLayoutManager.VERTICAL, 40, R.color.divider));
        mRecycler.setAdapter(adapter);
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        refreshViewHolder.setLoadingMoreText(getString(R.string.pull_load_more));
        mRefresh.setRefreshViewHolder(refreshViewHolder);
        mRefresh.setDelegate(this);
    }

    @Override
    public void showNoMore() {
        Toast.makeText(this, "没有更多了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoSearchResult() {
        Toast.makeText(this, "抱歉，没有查询到相关信息", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadMore(boolean isShow) {
        if (isShow) {
            mRefresh.beginLoadingMore();
        } else {
            mRefresh.endLoadingMore();
        }
    }

    @Override
    public void showRefreshing(boolean isRefreshing) {
        if (isRefreshing) {
            mRefresh.beginRefreshing();
        } else {
            mRefresh.endRefreshing();
        }
    }

    @Override
    public void showKeyword(String keyword) {
        String content = mTitleText;
        if (!keyword.equals("")) {
            content = String.format("%s(%s)", mTitleText, keyword);
        }
        mTitle.setText(content);
    }

    @Override
    public void showOwner() {
        mTitleText = getString(R.string.my_payment);
        mTitle.setText(mTitleText);
    }

    @Override
    public void showProperty() {
        mTitleText = getString(R.string.payment_center);
        mTitle.setText(mTitleText);
    }

    @Override
    public void showNoItems() {
        mRefresh.setVisibility(View.GONE);
        mNoItems.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearNoItems() {
        mRefresh.setVisibility(View.VISIBLE);
        mNoItems.setVisibility(View.GONE);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mPresenter.refresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mPresenter.loadMore();
        return true;
    }
}
