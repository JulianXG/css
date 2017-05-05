package cn.kalyter.css.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.RepairContract;
import cn.kalyter.css.presenter.RepairPresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseFragment;
import cn.kalyter.css.util.Config;
import cn.kalyter.css.util.RecycleViewDivider;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class RepairFragment extends BaseFragment implements RepairContract.View,
        BGARefreshLayout.BGARefreshLayoutDelegate{
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.no_items)
    TextView mNoItems;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    BGARefreshLayout mRefresh;
    @BindView(R.id.tab)
    TabLayout mTabLayout;

    private RepairContract.Presenter mPresenter;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressDialog mProgressDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitle.setText(R.string.repair_management_center);

        mToolbar.inflateMenu(R.menu.filter);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new MaterialDialog.Builder(getContext())
                        .title(R.string.keyword_title)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input(getString(R.string.hint_keyword_search), null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                mPresenter.search(input.toString());
                            }
                        }).build().show();
                return true;
            }
        });

        mTabLayout.addTab(mTabLayout.newTab().setText(Config.REPAIR_REQUIRED));
        mTabLayout.addTab(mTabLayout.newTab().setText(Config.REPAIR_REPAIRING));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals(Config.REPAIR_REQUIRED)) {
                    mPresenter.toggleStatus(Config.STATUS_REPAIR_REQUIRED);
                } else if (tab.getText().equals(Config.REPAIR_REPAIRING)) {
                    mPresenter.toggleStatus(Config.STATUS_REPAIR_REPAIRING);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mProgressDialog = new ProgressDialog(getContext());
        mPresenter.start();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new RepairPresenter(this, getContext(),
                App.getInjectClass().getRepairApi(),
                App.getInjectClass().getUserSource());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_repair;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(mLinearLayoutManager);
        mRecycler.addItemDecoration(new RecycleViewDivider(getContext(),
                LinearLayoutManager.VERTICAL, 64, R.color.divider));
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        refreshViewHolder.setLoadingMoreText(getString(R.string.pull_load_more));
        mRefresh.setRefreshViewHolder(refreshViewHolder);
        mRefresh.setDelegate(this);
        mRecycler.setAdapter(adapter);
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
    public void showNoMore() {
        Toast.makeText(getContext(), "没有更多了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showKeyword(String keyword) {
        String title = getString(R.string.repair_management_center);
        if (keyword != null && !keyword.equals("")) {
            title += "(" + keyword + ")";
        }
        mTitle.setText(title);
    }

    @Override
    public void showNoMoreItems() {
        mRefresh.setVisibility(View.GONE);
        mNoItems.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearNoMoreItems() {
        mRefresh.setVisibility(View.VISIBLE);
        mNoItems.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void closeLoading() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showChangeStatusSuccess() {
        Toast.makeText(getContext(), "更改报修状态成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showChangeStatusFail() {
        Toast.makeText(getContext(), "更改报修状态失败，请重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showConfirmChangeStatus(final int repairId, final int status) {
        new MaterialDialog.Builder(getContext())
                .title(R.string.confirm)
                .content(R.string.confirm_change_repair_status)
                .positiveText(R.string.confirm)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mPresenter.changeRepairStatus(repairId, status);
                    }
                }).build().show();
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
