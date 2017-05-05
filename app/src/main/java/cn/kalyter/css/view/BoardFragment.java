package cn.kalyter.css.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.BoardContract;
import cn.kalyter.css.model.Community;
import cn.kalyter.css.model.User;
import cn.kalyter.css.presenter.BoardPresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseFragment;
import cn.kalyter.css.util.GlideCircleTransform;
import cn.kalyter.css.util.MessageRecyclerAdapter;
import cn.kalyter.css.util.RecycleViewDivider;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public class BoardFragment extends BaseFragment implements BoardContract.View,
        BGARefreshLayout.BGARefreshLayoutDelegate{
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    BGARefreshLayout mRefresh;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.avatar)
    ImageView mAvatar;

    private BoardContract.Presenter mPresenter;

    @OnClick(R.id.btn_floating)
    void clickFloating() {
        showNewMessage();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mToolbar.inflateMenu(R.menu.search);
        mPresenter.start();
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
                        }).show();

                return true;
            }
        });
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new BoardPresenter(this,
                App.getInjectClass().getUserSource(),
                App.getInjectClass().getMessageApi(),
                getContext());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_board;
    }

    @Override
    public void showNewMessage() {
        startActivity(new Intent(getContext(), NewNoticeActivity.class));
    }

    @Override
    public void showCommunity(Community community) {
        mTitle.setText(community.getName());
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
    public void showLoadMore(boolean isShow) {
        if (isShow) {
            mRefresh.beginLoadingMore();
        } else {
            mRefresh.endLoadingMore();
        }
    }

    @Override
    public void setBoardRecyclerAdapter(MessageRecyclerAdapter adapter) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(manager);
        mRecycler.addItemDecoration(new RecycleViewDivider(getContext(),
                LinearLayoutManager.VERTICAL, 40, R.color.divider));
        mRecycler.setAdapter(adapter);
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        refreshViewHolder.setLoadingMoreText(getString(R.string.pull_load_more));
        mRefresh.setRefreshViewHolder(refreshViewHolder);
        mRefresh.setDelegate(this);
    }

    @Override
    public void showNoMore() {
        Toast.makeText(getContext(), "没有更多了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUser(User user) {
        Glide.with(this)
                .load(user.getAvatar())
                .centerCrop()
                .transform(new GlideCircleTransform(getContext()))
                .placeholder(R.drawable.ic_person_black_24dp)
                .into(mAvatar);
    }

    @Override
    public void showNoSearchResult() {
        Toast.makeText(getContext(), "抱歉，没有查询到相关信息", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showKeyword(Community community, String keyword) {
        if (keyword.equals("")) {
            showCommunity(community);
        } else {
            mTitle.setText(String.format("%s(%s)", community.getName(), keyword));
        }
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
