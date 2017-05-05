package cn.kalyter.css.presenter;

import android.content.Context;

import java.util.List;

import cn.kalyter.css.contract.RepairRecordContract;
import cn.kalyter.css.data.source.RepairApi;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Repair;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
import cn.kalyter.css.util.Config;
import cn.kalyter.css.util.RepairRecyclerAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-13 0013.
 */

public class RepairRecordPresenter implements RepairRecordContract.Presenter {
    private RepairRecordContract.View mView;
    private Context mContext;
    private RepairApi mRepairApi;
    private RepairRecyclerAdapter mRepairRecyclerAdapter;
    private UserSource mUserSource;
    private User mUser;
    private String mKeyword = "";
    private int mCurrentPage = 1;
    private int mStatus = Config.STATUS_REPAIR_REQUIRED;

    public RepairRecordPresenter(RepairRecordContract.View view,
                                 Context context,
                                 RepairApi repairApi,
                                 UserSource userSource) {
        mView = view;
        mContext = context;
        mRepairApi = repairApi;
        mRepairRecyclerAdapter = new RepairRecyclerAdapter(mContext, RepairRecyclerAdapter.TYPE_VIEW);
        mUserSource = userSource;
    }

    @Override
    public void start() {
        mView.setAdapter(mRepairRecyclerAdapter);
        loadRole();
        refresh();
    }

    @Override
    public void refresh() {
        mView.clearNoItems();
        mCurrentPage = 1;
        mRepairApi.getRepairs(mUser.getCommunityId(), Config.PAGE_SIZE,
                mCurrentPage, null, mUser.getId(), mStatus, mKeyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<Repair>>>() {
                    @Override
                    public void onCompleted() {
                        mView.showRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showRefreshing(false);
                    }

                    @Override
                    public void onNext(Response<List<Repair>> listResponse) {
                        List<Repair> data = listResponse.getData();
                        if (data.size() == 0) {
                            mView.showNoItems();
                        } else {
                            mRepairRecyclerAdapter.setData(data);
                        }
                    }
                });
    }

    @Override
    public void loadMore() {
        mRepairApi.getRepairs(mUser.getCommunityId(), Config.PAGE_SIZE,
                mCurrentPage + 1, null, mUser.getId(), mStatus, mKeyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<Repair>>>() {
                    @Override
                    public void onCompleted() {
                        mView.showLoadMore(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoadMore(false);
                    }

                    @Override
                    public void onNext(Response<List<Repair>> listResponse) {
                        List<Repair> data = listResponse.getData();
                        if (data.size() == 0) {
                            mView.showNoMore();
                        } else {
                            mRepairRecyclerAdapter.addData(data);
                            mCurrentPage++;
                        }
                    }
                });
    }

    @Override
    public void search(String keyword) {
        mKeyword = keyword;
        mView.showKeyword(mKeyword);
        refresh();
    }

    @Override
    public void loadRole() {
        mUser = mUserSource.getUser();
        if (mUser.getRoleId() == Config.ROLE_PROPERTY) {
            mUser.setId(null);
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
