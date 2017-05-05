package cn.kalyter.css.presenter;

import android.content.Context;

import java.util.List;

import cn.kalyter.css.contract.RepairContract;
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
 * Created by Kalyter on 2017-4-12 0012.
 */

public class RepairPresenter implements RepairContract.Presenter,
        RepairRecyclerAdapter.OnChangeRepairStatusListener{
    private static final String TAG = "RepairPresenter";
    private RepairContract.View mView;
    private Context mContext;
    private RepairApi mRepairApi;
    private UserSource mUserSource;
    private RepairRecyclerAdapter mRepairRecyclerAdapter;
    private User mUser;
    private Integer mStatus = Config.STATUS_REPAIR_REQUIRED;        // 默认为未解决状态
    private String mKeyword = "";
    private int mCurrentPage = 1;

    public RepairPresenter(RepairContract.View view,
                           Context context,
                           RepairApi repairApi,
                           UserSource userSource) {
        mView = view;
        mContext = context;
        mRepairApi = repairApi;
        mRepairRecyclerAdapter = new RepairRecyclerAdapter(mContext,
                RepairRecyclerAdapter.TYPE_PROPERTY_MANAGEMENT);
        mUserSource = userSource;
    }

    @Override
    public void start() {
        mView.setAdapter(mRepairRecyclerAdapter);
        mRepairRecyclerAdapter.setOnChangeRepairStatusListener(this);
        mUser = mUserSource.getUser();
        refresh();
    }

    @Override
    public void toggleStatus(Integer status) {
        mStatus = status;
        refresh();
    }

    @Override
    public void refresh() {
        mView.clearNoMoreItems();
        mRepairApi.getRepairs(mUser.getCommunityId(), Config.PAGE_SIZE,
                1, null, null, mStatus, mKeyword)
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
                            mView.showNoMoreItems();
                        } else {
                            mRepairRecyclerAdapter.setData(data);
                        }
                    }
                });
    }

    @Override
    public void loadMore() {
        mRepairApi.getRepairs(mUser.getCommunityId(), Config.PAGE_SIZE,
                mCurrentPage + 1, null, null, mStatus, mKeyword)
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
    public void changeRepairStatus(int repairId, int status) {
        mView.showLoading();
        mRepairApi.changeRepairStatus(repairId, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        mView.closeLoading();

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showChangeStatusFail();
                        mView.closeLoading();
                    }

                    @Override
                    public void onNext(Response response) {
                        mView.showChangeStatusSuccess();
                        refresh();
                    }
                });
    }

    @Override
    public void onChangeRepairStatus(int repairId, int status) {
        mView.showConfirmChangeStatus(repairId, status);
    }
}
