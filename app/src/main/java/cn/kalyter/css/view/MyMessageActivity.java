package cn.kalyter.css.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.MyMessageContract;
import cn.kalyter.css.presenter.MyMessagePresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseActivity;
import cn.kalyter.css.util.RecycleViewDivider;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class MyMessageActivity extends BaseActivity implements MyMessageContract.View,
        BGARefreshLayout.BGARefreshLayoutDelegate{
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    BGARefreshLayout mRefresh;

    private MyMessageContract.Presenter mPresenter;
    private String mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.inflateMenu(R.menu.filter);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new MaterialDialog.Builder(MyMessageActivity.this)
                        .title(R.string.keyword_title)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input(getString(R.string.hint_keyword_search), null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                mPresenter.search(input.toString());
                            }
                        }).show();
                return true;
            }
        });
        mPresenter.start();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new MyMessagePresenter(this, this,
                App.getInjectClass().getMessageApi(),
                App.getInjectClass().getUserSource());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_message;
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
        Toast.makeText(this, "没有更多了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoSearchResult() {
        Toast.makeText(this, "抱歉，没有查询到相关信息", Toast.LENGTH_SHORT).show();
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
        mTitleText = getString(R.string.my_message);
        mTitle.setText(mTitleText);
    }

    @Override
    public void showProperty() {
        mTitleText = getString(R.string.message_center);
        mTitle.setText(mTitleText);
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
